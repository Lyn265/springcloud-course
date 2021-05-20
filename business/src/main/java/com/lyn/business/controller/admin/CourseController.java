package com.lyn.business.controller.admin;

import com.lyn.server.dto.CourseDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.CourseService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course")
public class CourseController {

    public static final String BUSINESS_NAME = "课程";
    @Resource
    CourseService courseService;

    @PostMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
        courseService.list(pageDto);
        return  pageDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseDto courseDto){
//        ValidatorUtil.require(courseDto.getName(),"名称");
//        ValidatorUtil.require(courseDto.getCourseId(),"课程ID");
//        ValidatorUtil.length(courseDto.getCourseId(),"课程ID",1,8);
        ResponseDto responseDto = new ResponseDto();
         courseService.save(courseDto);
         responseDto.setContent(courseDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        courseService.delete(id);
        return responseDto;
    }
}
