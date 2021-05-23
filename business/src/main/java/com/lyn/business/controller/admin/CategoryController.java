package com.lyn.business.controller.admin;

import com.lyn.server.dto.CategoryDto;
import com.lyn.server.dto.ResponseDto;
import com.lyn.server.service.CategoryService;
import com.lyn.server.util.ValidatorUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {

    public static final String BUSINESS_NAME = "分类";
    @Resource
    CategoryService categoryService;

    @PostMapping("/all")
    public ResponseDto all(){
        List<CategoryDto> list = categoryService.all();
        ResponseDto responseDto = new ResponseDto();
        responseDto.setContent(list);
        return  responseDto;
    }

    @PostMapping("/save")
    public ResponseDto save(@RequestBody CategoryDto categoryDto){
        // 保存校验
        ValidatorUtil.require(categoryDto.getParent(), "父id");
        ValidatorUtil.require(categoryDto.getName(), "名称");
        ValidatorUtil.length(categoryDto.getName(), "名称", 1, 50);
        ResponseDto responseDto = new ResponseDto();
         categoryService.save(categoryDto);
         responseDto.setContent(categoryDto);
         return responseDto;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable String id){
        ResponseDto responseDto = new ResponseDto();
        categoryService.delete(id);
        return responseDto;
    }
}
