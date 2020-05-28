package com.example.cp.mapper;

import com.example.cp.entity.User;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-25
 */
public interface UserMapper extends Mapper<User> {
//    @SelectProvider(type = UserProvider.class, method = "getListSql")
//    List<User> list(User vo);

}
