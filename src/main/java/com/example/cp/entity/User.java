package com.example.cp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-25
 */
@ApiModel("用户")
@Data
@Table(name = "user")
public class User {
    private Integer id;

    @Excel(name = "姓名")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Excel(name = "年龄")
    @ApiModelProperty(value = "年龄")
    private Integer age;

    @Column(name = "manager_id")
    private String managerId;


}
