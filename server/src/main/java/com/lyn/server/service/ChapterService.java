package com.lyn.server.service;

import com.lyn.server.domain.Chapter;
import com.lyn.server.domain.ChapterExample;
import com.lyn.server.dto.ChapterDto;
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

    public List<ChapterDto> list() {
        ChapterExample chapterExample = new ChapterExample();
        chapterExample.setOrderByClause("id asc");
        List<Chapter> chapters = chapterMapper.selectByExample(chapterExample);
        List<ChapterDto> chapterDtoList = new ArrayList<>();
        ChapterDto chapterDto = new ChapterDto();
        for (int i = 0; i <chapters.size() ; i++) {
            Chapter chapter = chapters.get(i);
            BeanUtils.copyProperties(chapter,chapterDto);
            chapterDtoList.add(chapterDto);
        }
        return chapterDtoList;
    }
}
