package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Section;
import com.lyn.server.domain.SectionExample;
import com.lyn.server.dto.SectionDto;
import com.lyn.server.dto.SectionPageDto;
import com.lyn.server.mapper.SectionMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SectionService {

    @Resource
    SectionMapper sectionMapper;

    @Resource
    CourseService courseService;

    public void list(SectionPageDto sectionPageDto) {
        PageHelper.startPage(sectionPageDto.getPage(),sectionPageDto.getSize());
        SectionExample example = new SectionExample();
        SectionExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(sectionPageDto.getCourseId())){
        criteria.andCourseIdEqualTo(sectionPageDto.getCourseId());
        }
        if(!StringUtils.isEmpty(sectionPageDto.getChapterId())){
        criteria.andChapterIdEqualTo(sectionPageDto.getChapterId());
        }
        example.setOrderByClause("sort asc");
        List<Section> sections = sectionMapper.selectByExample(example);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        sectionPageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sections,SectionDto.class);
//        for (int i = 0; i <sections.size(); i++) {
//            SectionDto sectionDto = new SectionDto();
//            Section section = sections.get(i);
//            BeanUtils.copyProperties(section,sectionDto);
//            sectionDtoList.add(sectionDto);
//        }
        sectionPageDto.setList(sectionDtoList);
    }
    @Transactional
    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto,Section.class);
        if(StringUtils.isEmpty(sectionDto.getId())){
            insert(section);
        }else{
            update(section);
        }
        courseService.updateTime(section.getCourseId());
    }
    private void insert(Section section) {
        section.setId(UuidUtil.getShortUuid());
            Date now = new Date();
            section.setCreatedAt(now);
            section.setUpdatedAt(now);
        sectionMapper.insert(section);
    }
    private void update(Section section) {
            Date now = new Date();
            section.setUpdatedAt(now);
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
