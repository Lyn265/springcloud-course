package com.lyn.business.controller.admin;

import com.lyn.server.dto.${Domain}Dto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.${Domain}Service;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/${domain}")
public class ${Domain}Controller {

    public static final String BUSINESS_NAME = "大章";
    @Resource
    ${Domain}Service ${domain}Service;

    @PostMapping("/list")
    public PageDto list(@RequestBody PageDto pageDto){
        ${domain}Service.list(pageDto);
        return  pageDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto){
        ValidatorUtil.require(${domain}Dto.getName(),"名称");
        ValidatorUtil.require(${domain}Dto.getCourseId(),"课程ID");
        ValidatorUtil.length(${domain}Dto.getCourseId(),"课程ID",1,8);
        ResponseDto responseDto = new ResponseDto();
         ${domain}Service.save(${domain}Dto);
         responseDto.setContent(${domain}Dto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.delete(id);
        return responseDto;
    }
}
