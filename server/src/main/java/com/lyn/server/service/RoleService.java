package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.*;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.RoleDto;
import com.lyn.server.mapper.RoleMapper;
import com.lyn.server.mapper.RoleResourceMapper;
import com.lyn.server.mapper.RoleUserMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class RoleService {
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleResourceMapper roleResourceMapper;
    @Resource
    RoleUserMapper roleUserMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleExample example = new RoleExample();
        List<Role> roles = roleMapper.selectByExample(example);
        PageInfo<Role> pageInfo = new PageInfo<>(roles);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleDto> roleDtoList = CopyUtil.copyList(roles,RoleDto.class);
//        for (int i = 0; i <roles.size(); i++) {
//            RoleDto roleDto = new RoleDto();
//            Role role = roles.get(i);
//            BeanUtils.copyProperties(role,roleDto);
//            roleDtoList.add(roleDto);
//        }
        pageDto.setList(roleDtoList);
    }

    public void save(RoleDto roleDto) {
        Role role = CopyUtil.copy(roleDto,Role.class);
        if(StringUtils.isEmpty(roleDto.getId())){
            insert(role);
        }else{
            update(role);
        }
    }
    private void insert(Role role) {
        role.setId(UuidUtil.getShortUuid());
        roleMapper.insert(role);
    }
    private void update(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    public void delete(String id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void saveResource(RoleDto roleDto) {
            String roleId = roleDto.getId();
            List<String> resourceIds = roleDto.getResourceIds();
            RoleResourceExample example = new RoleResourceExample();
            example.createCriteria().andRoleIdEqualTo(roleId);
            roleResourceMapper.deleteByExample(example);

            for (int i = 0; i <resourceIds.size() ; i++) {
                String resourceId = resourceIds.get(i);
                RoleResource rr = new RoleResource();
                rr.setId(UuidUtil.getShortUuid());
                rr.setRoleId(roleId);
                rr.setResourceId(resourceId);
                roleResourceMapper.insert(rr);
            }
        }

    public List<String> listResource(String roleId) {
        RoleResourceExample example = new RoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleResource> roleResources = roleResourceMapper.selectByExample(example);
        List<String>stringList = new ArrayList<>();
        roleResources.stream().forEach(roleResource -> {
            stringList.add(roleResource.getResourceId());
        });
        return stringList;
    }

    public List<String> listUsers(String roleId) {
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(example);
        List<String>list = new ArrayList<>();
        roleUsers.stream().forEach(roleUser ->{
             list.add(roleUser.getUserId());
        });
        return list;
    }

    public void saveUser(RoleDto roleDto) {
        String roleId = roleDto.getId();
        List<String> userIds = roleDto.getUserIds();
        RoleUserExample example = new RoleUserExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        roleUserMapper.deleteByExample(example);

        for (int i = 0; i <userIds.size() ; i++) {
            String userId = userIds.get(i);
            RoleUser ru = new RoleUser();
            ru.setId(UuidUtil.getShortUuid());
            ru.setRoleId(roleId);
            ru.setUserId(userId);
            roleUserMapper.insert(ru);
        }

    }
}
