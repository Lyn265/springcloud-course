package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.CourseCategory;
import com.lyn.server.domain.CourseCategoryExample;
import com.lyn.server.dto.CategoryDto;
import com.lyn.server.dto.CourseCategoryDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.CourseCategoryMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class CourseCategoryService {
    @Resource
    CourseCategoryMapper courseCategoryMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseCategoryExample example = new CourseCategoryExample();
        List<CourseCategory> courseCategorys = courseCategoryMapper.selectByExample(example);
        PageInfo<CourseCategory> pageInfo = new PageInfo<>(courseCategorys);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseCategoryDto> courseCategoryDtoList = CopyUtil.copyList(courseCategorys,CourseCategoryDto.class);
//        for (int i = 0; i <courseCategorys.size(); i++) {
//            CourseCategoryDto courseCategoryDto = new CourseCategoryDto();
//            CourseCategory courseCategory = courseCategorys.get(i);
//            BeanUtils.copyProperties(courseCategory,courseCategoryDto);
//            courseCategoryDtoList.add(courseCategoryDto);
//        }
        pageDto.setList(courseCategoryDtoList);
    }

    public void save(CourseCategoryDto courseCategoryDto) {
        CourseCategory courseCategory = CopyUtil.copy(courseCategoryDto,CourseCategory.class);
        if(StringUtils.isEmpty(courseCategoryDto.getId())){
            insert(courseCategory);
        }else{
            update(courseCategory);
        }
    }
    private void insert(CourseCategory courseCategory) {
        courseCategory.setId(UuidUtil.getShortUuid());
        courseCategoryMapper.insert(courseCategory);
    }
    private void update(CourseCategory courseCategory) {
        courseCategoryMapper.updateByPrimaryKey(courseCategory);
    }

    public void delete(String id) {
        courseCategoryMapper.deleteByPrimaryKey(id);
    }

    //批量保存course_category
    public void saveBatch(String courseId, List<CategoryDto>dtoList){
        long start = System.currentTimeMillis();
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        courseCategoryMapper.deleteByExample(example);
        List<CourseCategory>list = new ArrayList<>();
        for (CategoryDto dto:dtoList) {
            CourseCategory courseCategory = new CourseCategory();
            courseCategory.setId(UuidUtil.getShortUuid());
            courseCategory.setCourseId(courseId);
            courseCategory.setCategoryId(dto.getId());
            list.add(courseCategory);
        }
        courseCategoryMapper.saveBatch(list);
        long end = System.currentTimeMillis();
        System.out.println("所用时间:"+ (end - start));
    }

    public List<CourseCategory> getCategoryList(String courseId) {
        CourseCategoryExample example = new CourseCategoryExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        return courseCategoryMapper.selectByExample(example);
    }
}
