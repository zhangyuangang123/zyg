package com.zyg.item.bo;

import com.zyg.item.pojo.Sku;
import com.zyg.item.pojo.Spu;
import com.zyg.item.pojo.SpuDetail;

import java.util.List;

/**
 * Created by Administrator on 2020/10/2.
 */
public class SpuBo extends Spu{

    String cname;// 商品分类名称

    String bname;// 品牌名称

    SpuDetail spuDetail;// 商品详情

    List<Sku> skus;// sku列表

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }
}
