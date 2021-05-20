package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Course;
import com.lyn.server.domain.CourseExample;
import com.lyn.server.dto.CourseDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.CourseMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample example = new CourseExample();
        List<Course> courses = courseMapper.selectByExample(example);
        PageInfo<Course> pageInfo = new PageInfo<>(courses);
        pageDto.setTotal(pageInfo.getTotal());
        List<CourseDto> courseDtoList = CopyUtil.copyList(courses,CourseDto.class);
//        for (int i = 0; i <courses.size(); i++) {
//            CourseDto courseDto = new CourseDto();
//            Course course = courses.get(i);
//            BeanUtils.copyProperties(course,courseDto);
//            courseDtoList.add(courseDto);
//        }
        pageDto.setList(courseDtoList);
    }

    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto,Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            insert(course);
        }else{
            update(course);
        }
    }
    private void insert(Course course) {
        course.setId(UuidUtil.getShortUuid());
        courseMapper.insert(course);
    }
    private void update(Course course) {
        courseMapper.updateByPrimaryKey(course);
    }

    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }
}
