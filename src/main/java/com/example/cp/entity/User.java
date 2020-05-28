package com.example.cp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-25
 */
@Data
@Table(name = "user")
public class User {
    private Integer id;

    private String name;
    private Integer age;

    @Column(name = "manager_id")
    private String managerId;
//    private String password;

//    private Date ctime;
//
//    private Integer uid;
//    @Column(name = "phone_passwd")
//    private String phonePasswd;
//    private String header;
//    private String uuid;
//    private Integer gender;
//    private Integer coin;
//    private String phone;
//    private String signature;
//
//    private String platform;
//
//    @Column(name = "qq_uid")
//    private String qqUid;
//
//    @Column(name = "qq_token")
//    private String qqToken;
//
//    @Column(name = "sina_uid")
//    private String sinaUid;
//
//    @Column(name = "sina_token")
//    private String sinaToken;
//
//    @Column(name = "qzone_uid")
//    private String qzoneUid;
//
//    @Column(name = "qzone_token")
//    private String qzoneToken;
//
//    @Column(name = "renren_uid")
//    private String renrenUid;
//
//    @Column(name = "renren_token")
//    private String renrenToken;
//
//    @Column(name = "tqq_uid")
//    private String tqqUid;
//
//    @Column(name = "tqq_token")
//    private String tqqToken;
//
//    private Integer sfrom;
//
//    private Long mobile;
//
//    private Integer isbind;
//
//    @Column(name = "sex_lock")
//    private Integer sexLock;
//
//    @Column(name = "visited_num")
//    private Integer visitedNum;
//
//    private String ver;
//    private Date mtime;

}
