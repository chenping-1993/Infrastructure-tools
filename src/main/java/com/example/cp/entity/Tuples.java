package com.example.cp.entity;

import lombok.Data;

/**
 * @Description: jedis tuple转换实体
 * @Author: chenping
 * @Date: 2020-05-24
 */
@Data
public class Tuples {
    //value值
    private String element;
    //排名
    private Double score;
}
