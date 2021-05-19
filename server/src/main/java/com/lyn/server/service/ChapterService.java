package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Chapter;
import com.lyn.server.domain.ChapterExample;
import com.lyn.server.dto.ChapterDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChapterService {
    @Resource
    ChapterMapper chapterMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ChapterExample example = new ChapterExample();
        List<Chapter> chapters = chapterMapper.selectByExample(example);
        PageInfo<Chapter> pageInfo = new PageInfo<>(chapters);
        pageDto.setTotal(pageInfo.getTotal());
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        for (int i = 0; i <chapters.size(); i++) {
            ChapterDto chapterDto = new ChapterDto();
            Chapter chapter = chapters.get(i);
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        pageDto.setList(chapterDtoList);
    }
}
