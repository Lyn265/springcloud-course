package com.lyn.business.controller.admin;

import com.lyn.server.dto.ChapterDto;
import com.lyn.server.service.ChapterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class ChapterController {
    @Resource
    ChapterService chapterService;

    @GetMapping("/chapter")
    public List<ChapterDto> list(){
        return chapterService.list();
    }
}
