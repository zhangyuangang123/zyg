package com.zyg.item.service;

import com.zyg.item.mapper.CategoryMapper;
import com.zyg.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2020/9/26.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据父节点查询子节点
     * @param pid
     * @return
     */
    public List<Category> queryCategoryByPid(Long pid) {
        Category category=new Category();
        category.setParentId(pid);
       return this.categoryMapper.select(category);
    }

    public List<String> queryNamesByIds(List<Long> ids){
        List<Category> categories=this.categoryMapper.selectByIdList(ids);
        return   categories.stream().map(category -> category.getName()).collect(Collectors.toList());
    }
}
