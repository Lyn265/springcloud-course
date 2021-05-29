package com.lyn.file.controller.admin;

import com.lyn.server.dto.ResponseDto;
import com.lyn.server.util.UuidUtil;
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
    @PostMapping("/upload")
    public ResponseDto upload(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseDto responseDto = new ResponseDto();
        //保存文件到本地
        String filename = file.getOriginalFilename();
        String key = UuidUtil.getShortUuid();
        String fullPath = "/Users/chenglliu/imgs/" + key + filename.substring(filename.lastIndexOf("."));
        File dest = new File(fullPath);
        file.transferTo(dest);
        return responseDto;
    }
}
