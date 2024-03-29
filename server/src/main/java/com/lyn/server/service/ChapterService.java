package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Chapter;
import com.lyn.server.domain.ChapterExample;
import com.lyn.server.dto.ChapterDto;
import com.lyn.server.dto.ChapterPageDto;
import com.lyn.server.mapper.ChapterMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    public void list(ChapterPageDto chapterPageDto) {
        PageHelper.startPage(chapterPageDto.getPage(),chapterPageDto.getSize());
        ChapterExample example = new ChapterExample();
        ChapterExample.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(chapterPageDto.getCourseId())){
            criteria.andCourseIdEqualTo(chapterPageDto.getCourseId());
        }
        List<Chapter> chapters = chapterMapper.selectByExample(example);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        chapterPageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = CopyUtil.copyList(chapters,ChapterDto.class);
//        for (int i = 0; i <chapters.size(); i++) {
//            ChapterDto chapterDto = new ChapterDto();
//            Chapter chapter = chapters.get(i);
//            BeanUtils.copyProperties(chapter,chapterDto);
//            chapterDtoList.add(chapterDto);
//        }
        chapterPageDto.setList(chapterDtoList);
    }

    public void save(ChapterDto chapterDto) {
        Chapter chapter = CopyUtil.copy(chapterDto,Chapter.class);
        if(StringUtils.isEmpty(chapterDto.getId())){
            insert(chapter);
        }else{
            update(chapter);
        }
    }
    private void insert(Chapter chapter) {
        chapter.setId(UuidUtil.getShortUuid());
        chapterMapper.insert(chapter);
    }
    private void update(Chapter chapter) {
        chapterMapper.updateByPrimaryKey(chapter);
    }

    public void delete(String id) {
        chapterMapper.deleteByPrimaryKey(id);
    }
}
