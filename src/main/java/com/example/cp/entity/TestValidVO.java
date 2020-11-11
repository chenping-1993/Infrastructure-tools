package com.example.cp.entity;

import lombok.Data;

import javax.validation.constraints.*;

/**
 * @Description: 测试校验实体
 * @Author: chenping
 * @Date: 2020-11-11
 */
@Data
public class TestValidVO {

    private String id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Size(min = 7,max = 11,message = "电话号码格式不正确")
    private String phone;

    @NotBlank
    @Email(message = "邮箱格式不正确")
    private String email;

    @DecimalMin(value = "0",message = "年龄不能小于0岁")
    @DecimalMax(value = "150",message = "年龄不能大于150岁")
    @NotNull(message = "年龄不能为空")
    private Integer age;
}
