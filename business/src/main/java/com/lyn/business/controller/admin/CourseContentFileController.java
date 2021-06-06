package com.lyn.business.controller.admin;

import com.lyn.server.dto.CourseContentFileDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.CourseContentFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/course-content-file")
public class CourseContentFileController {

    public static final String BUSINESS_NAME = "课程内容文件";
    @Resource
    CourseContentFileService courseContentFileService;

    @GetMapping("/list/{courseId}")
    public ResponseDto list(@PathVariable String courseId){
        ResponseDto responseDto = new ResponseDto();
        List<CourseContentFileDto> list = courseContentFileService.list(courseId);
        responseDto.setContent(list);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CourseContentFileDto courseContentFileDto){
        // 保存校验
//        ValidatorUtil.require(courseContentFileDto.getCourseId(), "课程id");
//        ValidatorUtil.length(courseContentFileDto.getUrl(), "地址", 1, 100);
//        ValidatorUtil.length(courseContentFileDto.getName(), "文件名", 1, 100);
        ResponseDto responseDto = new ResponseDto();
         courseContentFileService.save(courseContentFileDto);
         responseDto.setContent(courseContentFileDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        courseContentFileService.delete(id);
        return responseDto;
    }
}
