package com.yjy.moxibustion.abstracts;

import com.yjy.moxibustion.dto.RestResp;
import com.yjy.moxibustion.persistence.BaseService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 杨景元
 * @date 2021/1/16 19:41
 */
public abstract class AbstractBaseController<T,S extends BaseService<T>> {
    @Autowired
    protected S service;

    public abstract RestResp save(T entity);

    public abstract RestResp<T> getOne(Integer id);


}
