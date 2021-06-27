package com.lyn.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.User;
import com.lyn.server.domain.UserExample;
import com.lyn.server.dto.LoginUserDto;
import com.lyn.server.dto.ResourceDto;
import com.lyn.server.dto.UserDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.enums.BusinessExceptionCode;
import com.lyn.server.exception.BusinessException;
import com.lyn.server.mapper.UserMapper;
import com.lyn.server.mapper.my.MyUserMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;


@Service
public class UserService {
    @Resource
    UserMapper userMapper;

    @Resource
    MyUserMapper myUserMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        UserExample example = new UserExample();
        List<User> users = userMapper.selectByExample(example);
        PageInfo<User> pageInfo = new PageInfo<>(users);
        pageDto.setTotal(pageInfo.getTotal());
        List<UserDto> userDtoList = CopyUtil.copyList(users,UserDto.class);
//        for (int i = 0; i <users.size(); i++) {
//            UserDto userDto = new UserDto();
//            User user = users.get(i);
//            BeanUtils.copyProperties(user,userDto);
//            userDtoList.add(userDto);
//        }
        pageDto.setList(userDtoList);
    }

    public void save(UserDto userDto) {
        User user = CopyUtil.copy(userDto,User.class);
        if(StringUtils.isEmpty(userDto.getId())){
            insert(user);
        }else{
            update(user);
        }
    }
    private void insert(User user) {
        User userDb = this.selectByLoginName(user.getLoginName());
        if(userDb!=null){
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        user.setId(UuidUtil.getShortUuid());
        userMapper.insert(user);
    }
    private void update(User user) {
        user.setPassword(null);
        userMapper.updateByPrimaryKeySelective(user);
    }

    public void delete(String id) {
        userMapper.deleteByPrimaryKey(id);
    }

    private User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(users)){
            return null;
        }else{
            return users.get(0);
        }
    }

    public void savePassword(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setPassword(userDto.getPassword());
        userMapper.updateByPrimaryKeySelective(user);
    }
    public LoginUserDto login(UserDto userDto){
        User userDb = selectByLoginName(userDto.getLoginName());
        if(userDb == null){
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_NOT_EXIST);
        }else {
            if(userDb.getPassword().equals(userDto.getPassword())){
                LoginUserDto loginUserDto = CopyUtil.copy(userDb, LoginUserDto.class);
                setAuth(loginUserDto);
                return loginUserDto;
            }else{
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_NOT_EXIST);
            }
        }
    }
    private void setAuth(LoginUserDto loginUserDto){
        List<ResourceDto> resourceList = myUserMapper.findResources(loginUserDto.getId());
        loginUserDto.setResources(resourceList);
        HashSet<String>requestSet = new HashSet<>();
        if(!CollectionUtils.isEmpty(resourceList)){
            for (int i = 0; i < resourceList.size(); i++) {
                ResourceDto resourceDto = resourceList.get(i);
                List<String> requestStr = JSON.parseArray(resourceDto.getRequest(), String.class);
                if(!CollectionUtils.isEmpty(requestStr)){
                    requestSet.addAll(requestStr);
                }
            }
        }
        loginUserDto.setRequests(requestSet);

    }
}
