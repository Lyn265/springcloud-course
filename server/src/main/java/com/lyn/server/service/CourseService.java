package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Course;
import com.lyn.server.domain.CourseContent;
import com.lyn.server.domain.CourseExample;
import com.lyn.server.dto.CourseContentDto;
import com.lyn.server.dto.CourseDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.SortDto;
import com.lyn.server.mapper.CourseContentMapper;
import com.lyn.server.mapper.CourseMapper;
import com.lyn.server.mapper.my.MyCourseMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class CourseService {
    @Resource
    CourseMapper courseMapper;

    @Resource
    MyCourseMapper myCourseMapper;

    @Resource
    CourseCategoryService courseCategoryService;

    @Resource
    CourseContentMapper courseContentMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        CourseExample example = new CourseExample();
            example.setOrderByClause("sort asc");
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
    @Transactional
    public void save(CourseDto courseDto) {
        Course course = CopyUtil.copy(courseDto,Course.class);
        if(StringUtils.isEmpty(courseDto.getId())){
            insert(course);
        }else{
            update(course);
        }
        courseCategoryService.saveBatch(course.getId(),courseDto.getCategorys());
    }
    private void insert(Course course) {
        course.setId(UuidUtil.getShortUuid());
            Date now = new Date();
            course.setCreatedAt(now);
            course.setUpdatedAt(now);
        courseMapper.insert(course);
    }
    private void update(Course course) {
            Date now = new Date();
            course.setUpdatedAt(now);
        courseMapper.updateByPrimaryKey(course);
    }

    public void delete(String id) {
        courseMapper.deleteByPrimaryKey(id);
    }

    public void updateTime(String id){
        myCourseMapper.updateTime(id);
    }

    public int saveContent(CourseContentDto courseContentDto) {
        CourseContent courseContent = CopyUtil.copy(courseContentDto, CourseContent.class);
        int i = courseContentMapper.updateByPrimaryKeyWithBLOBs(courseContent);
        if(i == 0){
            //不存在，说明要新插入
             i = courseContentMapper.insert(courseContent);
        }
        return i ;
    }

    public CourseContentDto findContent(String id) {
        CourseContent courseContent = courseContentMapper.selectByPrimaryKey(id);
        if(courseContent == null){
            return null;
        }
        return CopyUtil.copy(courseContent,CourseContentDto.class);
    }

    public void sort(SortDto sortDto) {
        //更新排序
        myCourseMapper.updateSort(sortDto);
        //重置排序
         //如果排序值变大 向后
        if(sortDto.getNewSort() > sortDto.getOldSort()){
            myCourseMapper.moveSortForward(sortDto);
        }
         //如果排序值变小 向前
        if(sortDto.getNewSort() < sortDto.getOldSort()){
            myCourseMapper.moveSortBackward(sortDto);
        }
    }
}
