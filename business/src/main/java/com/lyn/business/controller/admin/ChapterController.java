package com.lyn.business.controller.admin;

import com.lyn.server.dto.ChapterDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.service.ChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChapterController {
    @Resource
    ChapterService chapterService;

    @PostMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
        chapterService.list(pageDto);
        return  pageDto;
    }
}
