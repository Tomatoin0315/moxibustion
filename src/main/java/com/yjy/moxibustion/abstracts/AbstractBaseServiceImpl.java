package com.yjy.moxibustion.abstracts;

import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.MyMapper;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/19 16:02
 */
public abstract class AbstractBaseServiceImpl<T,M extends MyMapper> implements BaseService<T> {
    @Autowired
    protected M mapper;

    @Transactional
    @Override
    public RestResp save(T entity) {
        try {
            mapper.insert(entity);
            return RestResp.success("保存成功");
        } catch (Exception e) {
            e.printStackTrace();
            return RestResp.fail("保存失败");
        }
    }

    @Transactional
    @Override
    public T getOne(Integer id) {
        return (T) mapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public List<T> getAll() {
        return mapper.selectAll();
    }
}
