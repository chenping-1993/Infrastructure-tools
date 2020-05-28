package com.example.cp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2020-05-23
 */
@Data
public class BaseRequestModel {

    //操作系统（ios、android）
    @JSONField(name="Os")
    private String os;

    /// <summary>
    /// 操作系统版本（12.1、8.0）
    /// </summary>
    @JSONField(name="Os_ver")
    public String osVer;

    /// <summary>
    /// 手机型号（iphone、huawei）
    /// </summary>
    @JSONField(name="P_model")
    public String pModel;

    /// <summary>
    /// 手机厂商（apple、xiaomi、huawei）
    /// </summary>
    @JSONField(name="P_mftr")
    public String pMftr;

    /// <summary>
    /// 设备唯一标识
    /// </summary>
    @JSONField(name="Udid")
    public String udid;

    /// <summary>
    /// 时间戳（精确到秒）
    /// </summary>
    @JSONField(name="Ts")
    public long ts;

    /// <summary>
    /// app版本
    /// </summary>
    @JSONField(name="Ver")
    public String ver;

    /// <summary>
    /// 用户token
    /// </summary>
    @JSONField(name="Token")
    public String token;

    /// <summary>
    /// 市场
    /// </summary>
    @JSONField(name="Market")
    public String market;

    /// <summary>
    /// 安卓device_id
    /// </summary>
    @JSONField(name="Adid")
    public String adid;

    /// <summary>
    /// 安卓android_id
    /// </summary>
    @JSONField(name="Aid")
    public String aid;

    @JSONField(name="_is_emulator")
    private byte isEmulator;
    /// <summary>
    /// 是否是模拟器（0:否 1:是）
    /// </summary>
//    @JSONField(name="Is_emulator")
//    public byte isEmulator;

    @JSONField(name="_is_root")
    private byte isRoot;
    /// <summary>
    /// 是否root（0:否 1:是）
    /// </summary>
    @JSONField(name="Os")
    public byte Is_root;

    /// <summary>
    /// 是否是模拟器（0:否 1:是）
    /// </summary>
    @JSONField(name="De_type")
    public byte De_type;

    /// <summary>
    /// 是否root（0:否 1:是）
    /// </summary>
    @JSONField(name="Dr_type")
    public byte Dr_type;

    @JSONField(name="Screen_width")
    public int screenWidth;

    @JSONField(name="Screen_height")
    public int screenHeight;

    @JSONField(name="_is_jailbroken")
    private byte isJailbroken;
    /// <summary>
    /// 是否越狱（0:否 1:是）
    /// </summary>
//    @JSONField(name="Is_jailbroken")
//    public byte isJailbroken;

    /// <summary>
    /// mac地址
    /// </summary>
    @JSONField(name="Mac_address")
    public String macAddress;

    /// <summary>
    /// 设备Id
    /// </summary>
    @JSONField(name="DeviceId")
    public String deviceId;

    /// <summary>
    /// 顶象设备指纹token
    /// </summary>
    @JSONField(name="Dx_token")
    public String dxToken;

    /// <summary>
    /// iOS广告标识id
    /// </summary>
    @JSONField(name="Idfa")
    public String Idfa ;

    /// <summary>
    /// 是否越狱（0:否 1:是）
    /// </summary>
    @JSONField(name="Device_jb")
    public byte deviceJb ;

    /// <summary>
    /// 是否安装微信（0:否 1:是）
    /// </summary>
    @JSONField(name="Install_wx")
    public byte installWx;

    /// <summary>
    /// 是否集成网易IM
    /// </summary>
    @JSONField(name="Is_nim")
    public byte isNim;


}
