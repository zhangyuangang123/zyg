package com.zyg.item.controller;

import com.zyg.core.base.BaseResponse;
import com.zyg.item.pojo.SpecParam;
import com.zyg.item.service.SpecificationService;
import com.zyg.item.pojo.SpecGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Administrator on 2020/10/2.
 */
@Controller
@RequestMapping("spec")
public class SpecificationConntroller {

    @Autowired
    private SpecificationService specificationService;

    /**
     * 根据分类id查询参数组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<Object> querySpecGroupByCid(@PathVariable("cid")Long cid){
        List<SpecGroup> groups=this.specificationService.querySpecGroupByCid(cid);
        return BaseResponse.ok(groups);
    }

    /**
     * 根据分组id查询参数
     * @param cid
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<Object> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ){

        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);

        return BaseResponse.ok(params);
    }


    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return  ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(list);
    }


}
