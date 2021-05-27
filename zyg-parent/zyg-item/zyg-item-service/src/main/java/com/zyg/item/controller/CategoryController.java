package com.zyg.item.controller;

import com.zyg.core.base.BaseResponse;
import com.zyg.core.base.ResultCodeEnum;
import com.zyg.item.service.CategoryService;
import com.zyg.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2020/9/26.
 */
@Controller
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @RequestMapping("list")
    public ResponseEntity<Object> queryCategoryById(@RequestParam(value ="pid",defaultValue = "0")Long pid){

        if(pid == null || pid < 0) {
            // 400 参数不合法
            //return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            return BaseResponse.error(ResultCodeEnum.PARAM_ERROR);
        }

       List<Category> categorys=this.categoryService.queryCategoryByPid(pid);

       /* if(CollectionUtils.isEmpty(categorys)){

             //404 : 资源未找到
            //return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.notFound().build();
        }*/

            // 200 查询成功
        return BaseResponse.ok(categorys);
    }

    @GetMapping("names")
    public ResponseEntity<Object> queryNameByIds(@RequestParam("ids")List<Long> ids){
        List<String> names = this.categoryService.queryNamesByIds(ids);

        return BaseResponse.ok(names);
    }
}
