package tk.mybatis;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 自己的Mapper
 * 特别注意，该接口不能被扫描到，否则会出错
 * @author 杨景元
 * @date 2021/1/15 10:36
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
