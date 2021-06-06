package com.lyn.file.controller.admin;

import com.lyn.server.dto.FileDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.enums.FileUseEnum;
import com.lyn.server.service.FileService;
import com.lyn.server.util.Base64ToMultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@RestController
@RequestMapping("/admin")
public class UploadController {

    private static final Logger LOG = LoggerFactory.getLogger(UploadController.class);

    public static final String BUSINESS_NAME = "视频上传";

    @Autowired
    FileService fileService;

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @PostMapping("/upload")
    public ResponseDto upload(@RequestBody FileDto fileDto) throws Exception {
        ResponseDto responseDto = new ResponseDto();
        String base64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFile.base64ToMultipart(base64);
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(fileDto.getUse());
        String dirName = fileUseEnum.name().toLowerCase();
        File dirFile = new File(FILE_PATH + dirName);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        //保存文件到本地
//        String key = UuidUtil.getShortUuid();
//        String path = dirName +File.separator + fileDto.getKey() +"." + fileDto.getSuffix();
        String path = new StringBuffer()
                .append(dirName).append(File.separator)
                .append(fileDto.getKey()).append(".").append(fileDto.getSuffix())
                .toString(); //xxxx.mp4
        String localPath = new StringBuffer(path)
                .append(".")
                .append(fileDto.getShardIndex()).toString();//xxxx.mp4.1
        String fullPath = FILE_PATH + localPath;
        File dest = new File(fullPath);
        shard.transferTo(dest);

        fileDto.setPath(path);
        fileService.save(fileDto);
        fileDto.setPath(FILE_DOMAIN + path);
        responseDto.setContent(fileDto);
        //合并分片
        if (fileDto.getShardIndex() == fileDto.getShardTotal()) {
            this.merge(fileDto);
        }
        return responseDto;
    }

    /**
     * 分片合并
     * 读取各个分片，每次追加最后写出一个合并后的文件
     */
    private void merge(FileDto fileDto) throws Exception{
        String path = fileDto.getPath();//http://localhost:9000/file/f/xxxx.mp4
        path = path.replace(FILE_DOMAIN, "");
        Integer shardTotal = fileDto.getShardTotal();
        File newFile = new File(FILE_PATH + path);

        FileOutputStream outputStream = new FileOutputStream(newFile, true);//文件追加写入
        FileInputStream inputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;

        try {
            for (int i = 0; i < shardTotal; i++) {
                inputStream = new FileInputStream(new File(FILE_PATH + path + "." + (i + 1)));
                while ((len = inputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (Exception e) {
            LOG.error("分片合并异常", e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                outputStream.close();
                LOG.info("IO流关闭");
            } catch (Exception e) {
                LOG.error("IO流关闭", e);
            }
        }
        LOG.info("合并分片结束");

        System.gc();
        //留一毫秒虚拟机其它工作都做完后再执行删除
        Thread.sleep(100);
        // 删除分片
        LOG.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = FILE_PATH + path + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            LOG.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        LOG.info("删除分片结束");
    }
    @GetMapping("/check/{key}")
    public ResponseDto findByKey(@PathVariable String key){
        ResponseDto responseDto = new ResponseDto();
        FileDto fileDto = fileService.findByKey(key);
        if(fileDto != null){
            fileDto.setPath(FILE_DOMAIN + fileDto.getPath());
        }
        responseDto.setContent(fileDto);
        return responseDto;
    }
}
