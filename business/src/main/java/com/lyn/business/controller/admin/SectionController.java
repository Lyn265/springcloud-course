package com.lyn.business.controller.admin;

import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.dto.SectionDto;
import com.lyn.server.dto.SectionPageDto;
import com.lyn.server.service.SectionService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    public static final String BUSINESS_NAME = "小节";
    @Resource
    SectionService sectionService;

    @PostMapping("/list")
    public PageDto list(@RequestBody SectionPageDto sectionPageDto){
        ValidatorUtil.require(sectionPageDto.getCourseId(),"课程ID");
        ValidatorUtil.require(sectionPageDto.getChapterId(),"大章ID");
        sectionService.list(sectionPageDto);
        return  sectionPageDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto){
        // 保存校验
        ValidatorUtil.require(sectionDto.getTitle(), "标题");
        ValidatorUtil.length(sectionDto.getTitle(), "标题", 1, 50);
        ValidatorUtil.length(sectionDto.getVideo(), "视频", 1, 200);
        ResponseDto responseDto = new ResponseDto();
         sectionService.save(sectionDto);
         responseDto.setContent(sectionDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        sectionService.delete(id);
        return responseDto;
    }
}
