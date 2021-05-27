package com.zyg.item.mapper;

import com.zyg.item.pojo.Category;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.ids.SelectByIdsMapper;

/**
 * Created by Administrator on 2020/9/26.
 */
public interface CategoryMapper extends Mapper<Category>,SelectByIdListMapper<Category,Long>{

}
