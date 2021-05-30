package com.lyn.file.controller.admin;

import com.lyn.server.dto.FileDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.enums.FileUseEnum;
import com.lyn.server.service.FileService;
import com.lyn.server.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/admin")
public class UploadController {

    @Autowired
    FileService fileService;

    @Value("${file.path}")
    private String FILE_PATH;

    @Value("${file.domain}")
    private String FILE_DOMAIN;

    @PostMapping("/upload")
    public ResponseDto upload(@RequestParam("file") MultipartFile file,String use) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        FileUseEnum fileUseEnum = FileUseEnum.getByCode(use);
        String dirName = fileUseEnum.name().toLowerCase();
        File dirFile = new File(FILE_PATH + dirName);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        //保存文件到本地
        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String suffix = filename.substring(filename.lastIndexOf(".")+1).toLowerCase();
        String path = dirName +File.separator + key +"." + suffix;
        String fullPath = FILE_PATH + path;
        File dest = new File(fullPath);
        file.transferTo(dest);

        FileDto fileDto = new FileDto();
        fileDto.setName(filename);
        fileDto.setSize(Math.toIntExact(file.getSize()));
        fileDto.setPath(path);
        fileDto.setSuffix(suffix);
        fileDto.setUse(use);
        fileService.save(fileDto);
        responseDto.setContent(FILE_DOMAIN +path);
        return responseDto;
    }
}
