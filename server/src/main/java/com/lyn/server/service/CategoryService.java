package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Category;
import com.lyn.server.domain.CategoryExample;
import com.lyn.server.dto.CategoryDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.CategoryMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CategoryService {
    @Resource
    CategoryMapper categoryMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CategoryExample example = new CategoryExample();
            example.setOrderByClause("sort asc");
        List<Category> categorys = categoryMapper.selectByExample(example);
        PageInfo<Category> pageInfo = new PageInfo<>(categorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CategoryDto> categoryDtoList = CopyUtil.copyList(categorys,CategoryDto.class);
//        for (int i = 0; i <categorys.size(); i++) {
//            CategoryDto categoryDto = new CategoryDto();
//            Category category = categorys.get(i);
//            BeanUtils.copyProperties(category,categoryDto);
//            categoryDtoList.add(categoryDto);
//        }
        pageDto.setList(categoryDtoList);
    }

    public void save(CategoryDto categoryDto) {
        Category category = CopyUtil.copy(categoryDto,Category.class);
        if(StringUtils.isEmpty(categoryDto.getId())){
            insert(category);
        }else{
            update(category);
        }
    }
    private void insert(Category category) {
        category.setId(UuidUtil.getShortUuid());
        categoryMapper.insert(category);
    }
    private void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }

    @Transactional
    public void delete(String id) {
        deleteChildren(id);
        categoryMapper.deleteByPrimaryKey(id);
    }

    public List<CategoryDto> all() {
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("sort asc");
        List<Category> categories = categoryMapper.selectByExample(example);
        return CopyUtil.copyList(categories,CategoryDto.class);
    }
    //删除父分类下的所有子分类
    private void deleteChildren(String id){
        CategoryExample example = new CategoryExample();
        Category category = categoryMapper.selectByPrimaryKey(id);
        if("00000000".equals(category.getParent())){
            example.createCriteria().andParentEqualTo(category.getParent());
            categoryMapper.deleteByExample(example);
        }

    }
}
