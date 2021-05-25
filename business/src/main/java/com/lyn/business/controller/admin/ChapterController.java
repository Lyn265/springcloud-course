package com.lyn.business.controller.admin;

import com.lyn.server.dto.ChapterDto;
import com.lyn.server.dto.ChapterPageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.ChapterService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/chapter")
public class ChapterController {

    public static final String BUSINESS_NAME = "大章";
    @Resource
    ChapterService chapterService;

    @PostMapping("/list")
    public ChapterPageDto list(@RequestBody ChapterPageDto chapterPageDto){
        ValidatorUtil.require(chapterPageDto.getCourseId(),"课程ID");
        chapterService.list(chapterPageDto);
        return  chapterPageDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody ChapterDto chapterDto){
        ValidatorUtil.require(chapterDto.getName(),"名称");
        ValidatorUtil.require(chapterDto.getCourseId(),"课程ID");
        ValidatorUtil.length(chapterDto.getCourseId(),"课程ID",1,8);
        ResponseDto responseDto = new ResponseDto();
         chapterService.save(chapterDto);
         responseDto.setContent(chapterDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        chapterService.delete(id);
        return responseDto;
    }
}
