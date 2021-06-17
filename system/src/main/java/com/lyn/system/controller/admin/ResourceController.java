package com.lyn.system.controller.admin;

import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResourceDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.ResourceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/resource")
public class ResourceController {

    public static final String BUSINESS_NAME = "资源";
    @Resource
    ResourceService resourceService;

    @PostMapping("/list")
    public ResponseDto list(@RequestBody PageDto pageDto){
        ResponseDto responseDto = new ResponseDto();
        resourceService.list(pageDto);
        responseDto.setContent(pageDto);
        return responseDto;
    }
    @GetMapping("/load-tree")
    public ResponseDto loadTree(){
        ResponseDto responseDto = new ResponseDto();
        List<ResourceDto> list = resourceService.loadTree();
        responseDto.setContent(list);
        return responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody String resourceStr){
        // 保存校验
        ResponseDto responseDto = new ResponseDto();
         resourceService.saveJson(resourceStr);
         responseDto.setContent(resourceStr);
         return responseDto;
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        resourceService.delete(id);
        return responseDto;
    }
}
