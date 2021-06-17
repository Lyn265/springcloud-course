package com.lyn.system.controller.admin;

import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.dto.RoleDto;
import com.lyn.server.service.RoleService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/role")
public class RoleController {

    public static final String BUSINESS_NAME = "角色";
    @Resource
    RoleService roleService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        roleService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }

    @GetMapping("/list-user/{roleId}")
    public ResponseDto listUsers(@PathVariable String roleId){
        ResponseDto responseDto = new ResponseDto();
        List<String>IdList = roleService.listUsers(roleId);
        responseDto.setContent(IdList);
        return responseDto;
    }

    @PostMapping("/save-resource")
    public ResponseDto saveResource(@RequestBody RoleDto roleDto){
        // 保存校验
        ResponseDto responseDto = new ResponseDto();
        roleService.saveResource(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }
    @PostMapping("/save-user")
    public ResponseDto saveUser(@RequestBody RoleDto roleDto){
        // 保存校验
        ResponseDto responseDto = new ResponseDto();
        roleService.saveUser(roleDto);
        responseDto.setContent(roleDto);
        return responseDto;
    }
    @PostMapping("/list-resource/{roleId}")
    public ResponseDto listResource(@PathVariable String roleId){
        // 保存校验
        ResponseDto responseDto = new ResponseDto();
        List<String> resources = roleService.listResource(roleId);
        responseDto.setContent(resources);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody RoleDto roleDto){
        // 保存校验
        ValidatorUtil.require(roleDto.getName(), "角色");
        ValidatorUtil.length(roleDto.getName(), "角色", 1, 50);
        ValidatorUtil.require(roleDto.getDesc(), "描述");
        ValidatorUtil.length(roleDto.getDesc(), "描述", 1, 100);
        ResponseDto responseDto = new ResponseDto();
         roleService.save(roleDto);
         responseDto.setContent(roleDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        roleService.delete(id);
        return responseDto;
    }
}
