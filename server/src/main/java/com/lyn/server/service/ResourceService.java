package com.lyn.server.service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyn.server.domain.Resource;
import com.lyn.server.domain.ResourceExample;
import com.lyn.server.dto.PageDto;
import com.lyn.server.dto.ResourceDto;
import com.lyn.server.mapper.ResourceMapper;
import com.lyn.server.util.CopyUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResourceService {
    @javax.annotation.Resource
    ResourceMapper resourceMapper;

    public void list(PageDto pageDto) {
        PageHelper.startPage(pageDto.getPage(),pageDto.getSize());
        ResourceExample example = new ResourceExample();
        List<Resource> resources = resourceMapper.selectByExample(example);
        PageInfo<Resource> pageInfo = new PageInfo<>(resources);
        pageDto.setTotal(pageInfo.getTotal());
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resources,ResourceDto.class);
//        for (int i = 0; i <resources.size(); i++) {
//            ResourceDto resourceDto = new ResourceDto();
//            Resource resource = resources.get(i);
//            BeanUtils.copyProperties(resource,resourceDto);
//            resourceDtoList.add(resourceDto);
//        }
        pageDto.setList(resourceDtoList);
    }

    public void saveJson(String json){
        //把json对象转成数组
        List<ResourceDto> jsonList = JSON.parseArray(json, ResourceDto.class);
        //树形结构jsonList变成没有嵌套的list
        List<ResourceDto>list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(jsonList)){
            for (ResourceDto d:jsonList){
                d.setParent("");
                add(list,d);
            }
        }
        resourceMapper.deleteByExample(null);
        for (int i = 0; i <list.size() ; i++) {
            this.insert(CopyUtil.copy(list.get(i),Resource.class));
        }
    }

    private void add(List<ResourceDto> list, ResourceDto dto) {
        //把顶级节点也要先放到list里
        list.add(dto);
        //判断是否有子节点
        if(!CollectionUtils.isEmpty(dto.getChildren())){
        //遍历当前的下的子节点
            for(ResourceDto d: dto.getChildren()){
                d.setParent(dto.getId());
                add(list,d);
            }
        }
    }

//    public void save(String resourceDto) {
//        Resource resource = CopyUtil.copy(resourceDto,Resource.class);
//        if(StringUtils.isEmpty(resourceDto.getId())){
//            insert(resource);
//        }else{
//            update(resource);
//        }
//    }
    private void insert(Resource resource) {
        resourceMapper.insert(resource);
    }
    private void update(Resource resource) {
        resourceMapper.updateByPrimaryKey(resource);
    }

    public void delete(String id) {
        resourceMapper.deleteByPrimaryKey(id);
    }

    public List<ResourceDto> loadTree() {
        ResourceExample example = new ResourceExample();
        example.setOrderByClause("id asc");
        List<Resource> resources = resourceMapper.selectByExample(example);
        List<ResourceDto> resourceDtoList = CopyUtil.copyList(resources,ResourceDto.class);
        //从后往前遍历 (也就是说先找到有子节点的数据)
        for (int i = resourceDtoList.size()-1; i>0 ; i--) {
            ResourceDto child = resourceDtoList.get(i);
            // 如果当前节点没有父节点，则不用往下了
            if(StringUtils.isEmpty(child.getParent())){
                continue;
            }
            //从i开始往前继续查，查找父节点
            for (int j = i-1; j>0 ; j--) {
                ResourceDto parent = resourceDtoList.get(j);
                if(child.getParent().equals(parent.getId())){
                    if(CollectionUtils.isEmpty(parent.getChildren())){
                        parent.setChildren(new ArrayList<>());
                    }
                    // 添加到最前面，否则会变成倒序，因为循环是从后往前循环的
                    parent.getChildren().add(0,child);

                    // 子节点找到父节点后，删除列表中的子节点
                    resourceDtoList.remove(child);
                }
            }
        }
        return resourceDtoList;
    }
}
