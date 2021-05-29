package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Teacher;
import com.lyn.server.domain.TeacherExample;
import com.lyn.server.dto.TeacherDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.TeacherMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class TeacherService {
    @Resource
    TeacherMapper teacherMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        TeacherExample example = new TeacherExample();
        List<Teacher> teachers = teacherMapper.selectByExample(example);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        pageDto.setTotal(pageInfo.getTotal());
        List<TeacherDto> teacherDtoList = CopyUtil.copyList(teachers,TeacherDto.class);
//        for (int i = 0; i <teachers.size(); i++) {
//            TeacherDto teacherDto = new TeacherDto();
//            Teacher teacher = teachers.get(i);
//            BeanUtils.copyProperties(teacher,teacherDto);
//            teacherDtoList.add(teacherDto);
//        }
        pageDto.setList(teacherDtoList);
    }

    public void save(TeacherDto teacherDto) {
        Teacher teacher = CopyUtil.copy(teacherDto,Teacher.class);
        if(StringUtils.isEmpty(teacherDto.getId())){
            insert(teacher);
        }else{
            update(teacher);
        }
    }
    private void insert(Teacher teacher) {
        teacher.setId(UuidUtil.getShortUuid());
        teacherMapper.insert(teacher);
    }
    private void update(Teacher teacher) {
        teacherMapper.updateByPrimaryKey(teacher);
    }

    public void delete(String id) {
        teacherMapper.deleteByPrimaryKey(id);
    }

    public List<TeacherDto> all() {
        TeacherExample example = new TeacherExample();
        List<Teacher>teacherList =  teacherMapper.selectByExample(example);
        return CopyUtil.copyList(teacherList,TeacherDto.class);

    }
}
