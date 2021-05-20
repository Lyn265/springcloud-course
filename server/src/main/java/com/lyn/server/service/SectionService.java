package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Section;
import com.lyn.server.domain.SectionExample;
import com.lyn.server.dto.SectionDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.SectionMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SectionService {
    @Resource
    SectionMapper sectionMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        SectionExample example = new SectionExample();
        List<Section> sections = sectionMapper.selectByExample(example);
        PageInfo<Section> pageInfo = new PageInfo<>(sections);
        pageDto.setTotal(pageInfo.getTotal());
        List<SectionDto> sectionDtoList = CopyUtil.copyList(sections,SectionDto.class);
//        for (int i = 0; i <sections.size(); i++) {
//            SectionDto sectionDto = new SectionDto();
//            Section section = sections.get(i);
//            BeanUtils.copyProperties(section,sectionDto);
//            sectionDtoList.add(sectionDto);
//        }
        pageDto.setList(sectionDtoList);
    }

    public void save(SectionDto sectionDto) {
        Section section = CopyUtil.copy(sectionDto,Section.class);
        if(StringUtils.isEmpty(sectionDto.getId())){
            insert(section);
        }else{
            update(section);
        }
    }
    private void insert(Section section) {
        section.setId(UuidUtil.getShortUuid());
        sectionMapper.insert(section);
    }
    private void update(Section section) {
        sectionMapper.updateByPrimaryKey(section);
    }

    public void delete(String id) {
        sectionMapper.deleteByPrimaryKey(id);
    }
}
