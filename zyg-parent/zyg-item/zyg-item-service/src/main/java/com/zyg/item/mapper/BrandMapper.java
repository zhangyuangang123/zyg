package com.zyg.item.mapper;

import com.zyg.item.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2020/9/27.
 */
public interface BrandMapper extends Mapper<Brand>{

    @Insert("insert  into tb_category_brand (category_id,brand_id) VALUES(#{cid},#{bid})")
    void saveCategroyAndBrand(@Param("cid") Long cid,@Param("bid") Long id);

    @Select("SELECT b.* from tb_brand b INNER JOIN tb_category_brand cb on b.id=cb.brand_id where cb.category_id=#{cid}")
    List<Brand> selectBrandsByCid(Long cid);
}
