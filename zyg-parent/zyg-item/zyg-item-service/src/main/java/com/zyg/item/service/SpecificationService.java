package com.zyg.item.service;

import com.zyg.item.mapper.SpecGroupMapper;
import com.zyg.item.mapper.SpecParamMapper;
import com.zyg.item.pojo.SpecGroup;
import com.zyg.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by Administrator on 2020/10/2.
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupManager;

    @Autowired
    private SpecParamMapper specParamManager;


    /**
     * 根据分类id 查询参数组
     * @param cid
     * @return
     */
    public List<SpecGroup> querySpecGroupByCid(Long cid) {
        SpecGroup specGroup=new SpecGroup();
        specGroup.setCid(cid);
       return this.specGroupManager.select(specGroup);
    }



    /**
     * 根据分类id 查询参数组
     * @param cid
     * @return
     */
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam=new SpecParam();
        specParam.setCid(cid);
        specParam.setGroupId(gid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return this.specParamManager.select(specParam);
    }


    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> groups = this.querySpecGroupByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParams(g.getId(), null, null, null));
        });
        return groups;
    }

}
