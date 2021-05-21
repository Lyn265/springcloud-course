package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.${Domain};
import com.lyn.server.domain.${Domain}Example;
import com.lyn.server.dto.${Domain}Dto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.${Domain}Mapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Date;

@Service
public class ${Domain}Service {
    @Resource
    ${Domain}Mapper ${domain}Mapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ${Domain}Example example = new ${Domain}Example();
        <#list fieldList as field>
        <#if field.nameHump='sort'>
            example.setOrderByClause("sort asc");
        </#if>
        </#list>
        List<${Domain}> ${domain}s = ${domain}Mapper.selectByExample(example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        pageDto.setTotal(pageInfo.getTotal());
        List<${Domain}Dto> ${domain}DtoList = CopyUtil.copyList(${domain}s,${Domain}Dto.class);
//        for (int i = 0; i <${domain}s.size(); i++) {
//            ${Domain}Dto ${domain}Dto = new ${Domain}Dto();
//            ${Domain} ${domain} = ${domain}s.get(i);
//            BeanUtils.copyProperties(${domain},${domain}Dto);
//            ${domain}DtoList.add(${domain}Dto);
//        }
        pageDto.setList(${domain}DtoList);
    }

    public void save(${Domain}Dto ${domain}Dto) {
        ${Domain} ${domain} = CopyUtil.copy(${domain}Dto,${Domain}.class);
        if(StringUtils.isEmpty(${domain}Dto.getId())){
            insert(${domain});
        }else{
            update(${domain});
        }
    }
    private void insert(${Domain} ${domain}) {
        ${domain}.setId(UuidUtil.getShortUuid());
            Date now = new Date();
    <#list fieldList as field>
        <#if field.nameHump='createdAt'>
            ${domain}.setCreatedAt(now);
        </#if>
        <#if field.nameHump='updatedAt'>
            ${domain}.setUpdatedAt(now);
        </#if>
    </#list>
        ${domain}Mapper.insert(${domain});
    }
    private void update(${Domain} ${domain}) {
            Date now = new Date();
    <#list fieldList as field>
        <#if field.nameHump='updatedAt'>
            ${domain}.setUpdatedAt(now);
        </#if>
    </#list>
        ${domain}Mapper.updateByPrimaryKey(${domain});
    }

    public void delete(String id) {
        ${domain}Mapper.deleteByPrimaryKey(id);
    }
}
