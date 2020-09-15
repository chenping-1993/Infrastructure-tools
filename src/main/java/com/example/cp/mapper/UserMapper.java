package com.example.cp.mapper;

import com.example.cp.entity.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-25
 */
public interface UserMapper extends Mapper<User> {
    @Select("select * from user ")
    List<User> list();

}
