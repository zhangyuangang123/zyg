package com.zyg.item.controller;

import com.zyg.core.base.BaseResponse;
import com.zyg.item.bo.SpuBo;
import com.zyg.common.pojo.PageResult;
import com.zyg.item.pojo.Spu;
import com.zyg.item.service.GoodsService;
import com.zyg.item.pojo.Sku;
import com.zyg.item.pojo.SpuDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2020/10/2.
 */
@Controller
@RequestMapping("goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/spu/page")
        public ResponseEntity<Object> querySpuBoByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false)Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "rows",defaultValue = "5")Integer rows
        ){

        PageResult<SpuBo> pageResult=this.goodsService.querySpuBoByPage(key,saleable,page,rows);

        return BaseResponse.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Object> saveGoods(@RequestBody SpuBo spuBo){
        this.goodsService.saveGoods(spuBo);
        return BaseResponse.ok();
    }

    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<Object> querySpuDetailBySpuId(@PathVariable("spuId")Long spuId){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(spuId);
        return BaseResponse.ok(spuDetail);
    }

    @GetMapping("/sku/list")
    public ResponseEntity<Object> querySkusBySpuId(@RequestParam("id")Long spuId){
        List<Sku> skus = this.goodsService.querySkusBySpuId(spuId);
        return BaseResponse.ok(skus);
    }

    @PutMapping
    public ResponseEntity<Object> updateGoods(@RequestBody SpuBo spuBo){
        this.goodsService.updateGoods(spuBo);
        return BaseResponse.ok();
    }

    @GetMapping("spu/{id}")
    public ResponseEntity<Object> querySpuById(@PathVariable("id") Long id){
        Spu spu = this.goodsService.querySpuById(id);
        return BaseResponse.ok(spu);
    }
}
