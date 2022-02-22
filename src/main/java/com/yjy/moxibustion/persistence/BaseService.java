package com.yjy.moxibustion.persistence;

import com.yjy.moxibustion.dto.RestResp;

import java.util.List;

/**
 * @author 杨景元
 * @date 2021/1/19 15:59
 */
public interface BaseService<T> {

    RestResp save(T entity);

    T getOne(Integer id);

    List<T> getAll();
}
