package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.User;
import com.lyn.server.domain.UserExample;
import com.lyn.server.dto.UserDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.enums.BusinessExceptionCode;
import com.lyn.server.exception.BusinessException;
import com.lyn.server.mapper.UserMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class UserService {
    @Resource
    UserMapper userMapper;

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
        userMapper.updateByPrimaryKey(user);
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
}
