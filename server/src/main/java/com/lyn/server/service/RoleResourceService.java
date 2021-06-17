package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.RoleResource;
import com.lyn.server.domain.RoleResourceExample;
import com.lyn.server.dto.RoleResourceDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.RoleResourceMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleResourceService {
    @Resource
    RoleResourceMapper roleResourceMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleResourceExample example = new RoleResourceExample();
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(example);
        PageInfo<RoleResource> pageInfo = new PageInfo<>(roleResources);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleResourceDto> roleResourceDtoList = CopyUtil.copyList(roleResources,RoleResourceDto.class);
//        for (int i = 0; i <roleResources.size(); i++) {
//            RoleResourceDto roleResourceDto = new RoleResourceDto();
//            RoleResource roleResource = roleResources.get(i);
//            BeanUtils.copyProperties(roleResource,roleResourceDto);
//            roleResourceDtoList.add(roleResourceDto);
//        }
        pageDto.setList(roleResourceDtoList);
    }

    public void save(RoleResourceDto roleResourceDto) {
        RoleResource roleResource = CopyUtil.copy(roleResourceDto,RoleResource.class);
        if(StringUtils.isEmpty(roleResourceDto.getId())){
            insert(roleResource);
        }else{
            update(roleResource);
        }
    }
    private void insert(RoleResource roleResource) {
        roleResource.setId(UuidUtil.getShortUuid());
        roleResourceMapper.insert(roleResource);
    }
    private void update(RoleResource roleResource) {
        roleResourceMapper.updateByPrimaryKey(roleResource);
    }

    public void delete(String id) {
        roleResourceMapper.deleteByPrimaryKey(id);
    }
}
