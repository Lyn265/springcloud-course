package com.lyn.system.controller.admin;

import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.dto.UserDto;
import com.lyn.server.service.UserService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    public static final String BUSINESS_NAME = "用户";
    @Resource
    UserService userService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        userService.list(pageDto);
        responseDto.setContent(pageDto);
        return  responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody UserDto userDto){
        // 保存校验
        ValidatorUtil.require(userDto.getLoginName(), "登陆名");
        ValidatorUtil.length(userDto.getLoginName(), "登陆名", 1, 50);
        ValidatorUtil.length(userDto.getName(), "昵称", 1, 50);
        ValidatorUtil.require(userDto.getPassword(), "密码");
        ResponseDto responseDto = new ResponseDto();
        userService.save(userDto);
         responseDto.setContent(userDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }
}
