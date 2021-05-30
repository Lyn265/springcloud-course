package com.lyn.file.controller.admin;

import com.lyn.server.dto.PageDto;
import com.lyn.server.service.FileService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/file")
public class FileController {

    public static final String BUSINESS_NAME = "文件";
    @Resource
    FileService fileService;

    @PostMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
        fileService.list(pageDto);
        return  pageDto;
    }
}
