package com.lyn.server.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.File;
import com.lyn.server.domain.FileExample;
import com.lyn.server.dto.FileDto;
import com.lyn.server.dto.PageDto;
import com.lyn.server.mapper.FileMapper;
import com.lyn.server.util.CopyUtil;
import com.lyn.server.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
public class FileService {
    @Resource
    FileMapper fileMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        FileExample example = new FileExample();
        List<File> files = fileMapper.selectByExample(example);
        PageInfo<File> pageInfo = new PageInfo<>(files);
        pageDto.setTotal(pageInfo.getTotal());
        List<FileDto> fileDtoList = CopyUtil.copyList(files,FileDto.class);
//        for (int i = 0; i <files.size(); i++) {
//            FileDto fileDto = new FileDto();
//            File file = files.get(i);
//            BeanUtils.copyProperties(file,fileDto);
//            fileDtoList.add(fileDto);
//        }
        pageDto.setList(fileDtoList);
    }

    public void save(FileDto fileDto) {
        File file = CopyUtil.copy(fileDto,File.class);
        File fileDb = selectByKey(fileDto.getKey());
        if(fileDb == null){
            insert(file);
        }else{
            fileDb.setShardIndex(fileDto.getShardIndex());
            update(fileDb);
        }
    }
    private void insert(File file) {
        Date now = new Date();
        file.setId(UuidUtil.getShortUuid());
            file.setCreatedAt(now);
            file.setUpdatedAt(now);
        fileMapper.insert(file);
    }
    private void update(File file) {
        Date now = new Date();
        file.setUpdatedAt(now);
        fileMapper.updateByPrimaryKey(file);
    }

    private File selectByKey(String key){
        FileExample example = new FileExample();
        example.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(fileList)){
            return null;
        }else{
            return  CopyUtil.copyList(fileList,File.class).get(0);
        }
    }

    private File check(String key) {
        FileExample fileExample = new FileExample();
        fileExample.createCriteria().andKeyEqualTo(key);
        List<File> fileList = fileMapper.selectByExample(fileExample);
        if(CollectionUtils.isEmpty(fileList)){
            return null;
        }else{
            return fileList.get(0);
        }
    }
    public FileDto findByKey(String key){
        return CopyUtil.copy(check(key),FileDto.class);
    }

}
