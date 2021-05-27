package com.zyg.item.controller;

import com.zyg.common.pojo.PageResult;
import com.zyg.core.base.BaseResponse;
import com.zyg.core.base.ResultCodeEnum;
import com.zyg.item.service.BrandService;
import com.zyg.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2020/9/27.
 */
@Controller
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * key=&page=1&rows=5&sortBy=id&desc=false
     * @return
     */
    @RequestMapping("page")
    public ResponseEntity<Object> queryBrandsByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",required = false) Boolean desc
        ){

       PageResult<Brand> pageResult=this.brandService.queryBrandsByPage(key,page,rows,sortBy,desc);

       if(CollectionUtils.isEmpty(pageResult.getItems())){
           return BaseResponse.error(ResultCodeEnum.PARAM_ERROR);
       }

       return BaseResponse.ok(pageResult);
    }

    @PostMapping
    public ResponseEntity<Object> saveBrand(Brand brand,@RequestParam(value = "cids") List<Long> cids){

        this.brandService.saveBrand(brand,cids);

        return BaseResponse.ok();
    }

    @GetMapping("/cid/{cid}")
    public ResponseEntity<Object> queryBrandsByCid(@PathVariable("cid") Long cid){
      List<Brand> brands= this.brandService.queryBrandsByCid(cid);

      if(CollectionUtils.isEmpty(brands)){
          return BaseResponse.error(ResultCodeEnum.PARAM_ERROR);
      }

      return BaseResponse.ok(brands);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> queryBrandById(@PathVariable("id") Long id){
        Brand brand=this.brandService.queryBrandById(id);
        if(brand == null){
            return BaseResponse.error(ResultCodeEnum.PARAM_ERROR);
        }

        return BaseResponse.ok(brand);
    }
}
