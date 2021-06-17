package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.RoleUser;
import com.lyn.server.domain.RoleUserExample;
import com.lyn.server.dto.RoleUserDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.RoleUserMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class RoleUserService {
    @Resource
    RoleUserMapper roleUserMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        RoleUserExample example = new RoleUserExample();
        List<RoleUser> roleUsers = roleUserMapper.selectByExample(example);
        PageInfo<RoleUser> pageInfo = new PageInfo<>(roleUsers);
        pageDto.setTotal(pageInfo.getTotal());
        List<RoleUserDto> roleUserDtoList = CopyUtil.copyList(roleUsers,RoleUserDto.class);
//        for (int i = 0; i <roleUsers.size(); i++) {
//            RoleUserDto roleUserDto = new RoleUserDto();
//            RoleUser roleUser = roleUsers.get(i);
//            BeanUtils.copyProperties(roleUser,roleUserDto);
//            roleUserDtoList.add(roleUserDto);
//        }
        pageDto.setList(roleUserDtoList);
    }

    public void save(RoleUserDto roleUserDto) {
        RoleUser roleUser = CopyUtil.copy(roleUserDto,RoleUser.class);
        if(StringUtils.isEmpty(roleUserDto.getId())){
            insert(roleUser);
        }else{
            update(roleUser);
        }
    }
    private void insert(RoleUser roleUser) {
        roleUser.setId(UuidUtil.getShortUuid());
        roleUserMapper.insert(roleUser);
    }
    private void update(RoleUser roleUser) {
        roleUserMapper.updateByPrimaryKey(roleUser);
    }

    public void delete(String id) {
        roleUserMapper.deleteByPrimaryKey(id);
    }
}
