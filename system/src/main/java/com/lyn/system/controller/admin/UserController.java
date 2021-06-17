package com.lyn.system.controller.admin;

import com.alibaba.fastjson.JSON;
import com.lyn.server.dto.LoginDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.dto.UserDto;
import com.lyn.server.service.UserService;
import com.lyn.server.util.UuidUtil;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    public static final String BUSINESS_NAME = "用户";
    @Resource
    UserService userService;

    @Resource
    RedisTemplate redisTemplate;

    @PostMapping("/login")
    public ResponseDto login(@RequestBody UserDto userDto, HttpServletRequest request){
        ResponseDto responseDto = new ResponseDto();
        String code = (String) redisTemplate.opsForValue().get(userDto.getImageCodeToken());
        if(StringUtils.isEmpty(code)){
            responseDto.setSuccess(false);
            responseDto.setMsg("验证码已过期");
            return responseDto;
        }
        if (!code.toLowerCase().equals(userDto.getImageCode().toLowerCase())){
            responseDto.setSuccess(false);
            responseDto.setMsg("验证码不正确");
            return responseDto;
        }else{
            redisTemplate.delete(userDto.getImageCodeToken());
        }
        String pwd = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        userDto.setPassword(pwd);
        LoginDto loginDto = userService.login(userDto);
//        request.getSession().setAttribute("loginUser",loginDto);
        String token = UuidUtil.getShortUuid();
        loginDto.setToken(token);
        redisTemplate.opsForValue().set(token, JSON.toJSONString(loginDto), 3600, TimeUnit.SECONDS);
        responseDto.setContent(loginDto);
         return responseDto;
    }

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
        String pwd = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        userDto.setPassword(pwd);
        userService.save(userDto);
        responseDto.setContent(userDto);
         return responseDto;
    }
    @PostMapping("/save-password")
    public ResponseDto savePassword(@RequestBody UserDto userDto){
        ResponseDto responseDto = new ResponseDto();
        String pwd = DigestUtils.md5DigestAsHex(userDto.getPassword().getBytes());
        userDto.setPassword(pwd);
        userService.savePassword(userDto);
        responseDto.setContent(userDto);
        return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        userService.delete(id);
        return responseDto;
    }

    @GetMapping("/logout/{token}")
    public ResponseDto logout(@PathVariable String token){
        ResponseDto responseDto = new ResponseDto();
//        request.getSession().removeAttribute("loginUser");
        redisTemplate.delete(token);
        return responseDto;
    }
}
