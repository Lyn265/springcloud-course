package com.lyn.business.controller.admin;

import com.lyn.server.dto.SectionDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.SectionService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/section")
public class SectionController {

    public static final String BUSINESS_NAME = "大章";
    @Resource
    SectionService sectionService;

    @PostMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
        sectionService.list(pageDto);
        return  pageDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody SectionDto sectionDto){
//        ValidatorUtil.require(sectionDto.getName(),"名称");
//        ValidatorUtil.require(sectionDto.getCourseId(),"课程ID");
//        ValidatorUtil.length(sectionDto.getCourseId(),"课程ID",1,8);
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
