package com.lyn.server.service;

import com.lyn.server.domain.CourseContentFile;
import com.lyn.server.domain.CourseContentFileExample;
import com.lyn.server.dto.CourseContentFileDto;
import com.lyn.server.mapper.CourseContentFileMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;


@Service
public class CourseContentFileService {
    @Resource
    CourseContentFileMapper courseContentFileMapper;

    public List<CourseContentFileDto> list(String courseId) {
        CourseContentFileExample example = new CourseContentFileExample();
        example.createCriteria().andCourseIdEqualTo(courseId);
        List<CourseContentFile> courseContentFiles = courseContentFileMapper.selectByExample(example);
        return CopyUtil.copyList(courseContentFiles,CourseContentFileDto.class);
    }

    public void save(CourseContentFileDto courseContentFileDto) {
        CourseContentFile courseContentFile = CopyUtil.copy(courseContentFileDto,CourseContentFile.class);
        if(StringUtils.isEmpty(courseContentFileDto.getId())){
            insert(courseContentFile);
        }else{
            update(courseContentFile);
        }
    }
    private void insert(CourseContentFile courseContentFile) {
        courseContentFile.setId(UuidUtil.getShortUuid());
        courseContentFileMapper.insert(courseContentFile);
    }
    private void update(CourseContentFile courseContentFile) {
        courseContentFileMapper.updateByPrimaryKey(courseContentFile);
    }

    public void delete(String id) {
        courseContentFileMapper.deleteByPrimaryKey(id);
    }
}
