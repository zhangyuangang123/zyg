package com.zyg.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zyg.common.pojo.PageResult;
import com.zyg.item.mapper.BrandMapper;
import com.zyg.item.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by Administrator on 2020/9/27.
 */
@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, boolean desc) {

        //初始化 Example 对象
        Example example=new Example(Brand.class);
        Example.Criteria criteria=example.createCriteria();

        //根据 name 模糊查询，或者根据首字母模糊查询
        if(StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%"+key+"%").orEqualTo("letter",key);
        }

        //添加分页条件
        PageHelper.startPage(page,rows);

        //添加排序条件
        if(StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy+" "+(desc ? "desc" : "asc"));
        }


        List<Brand> brands = this.brandMapper.selectByExample(example);

        //包装成 pageInfo
        PageInfo<Brand> pageInfo=new PageInfo<>(brands);

        //包装分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());


    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        this.brandMapper.insertSelective(brand);

        cids.forEach(cid->{
            this.brandMapper.saveCategroyAndBrand(cid,brand.getId());
        });

    }

    public List<Brand> queryBrandsByCid(Long cid) {
        return this.brandMapper.selectBrandsByCid(cid);
    }

    public Brand queryBrandById(Long id) {
        Brand brand=new Brand();
        brand.setId(id);
        return this.brandMapper.selectOne(brand);
    }
}
