package com.lyn.${module}.controller.admin;

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

    public static final String BUSINESS_NAME = "${tableNameCn}";
    @Resource
    ${Domain}Service ${domain}Service;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        ${domain}Service.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody ${Domain}Dto ${domain}Dto){
        // 保存校验
        <#list fieldList as field>
        <#if field.name!="id" && field.nameHump!="createdAt" && field.nameHump!="updatedAt" && field.nameHump!="sort">
            <#if !field.nullAble>
        ValidatorUtil.require(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}");
            </#if>
            <#if (field.length > 0)>
        ValidatorUtil.length(${domain}Dto.get${field.nameBigHump}(), "${field.nameCn}", 1, ${field.length?c});
            </#if>
        </#if>
        </#list>
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
