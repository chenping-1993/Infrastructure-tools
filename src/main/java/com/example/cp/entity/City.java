package com.example.cp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author: chenping
 * @Date: 2021-02-08
 */
@Data
@NoArgsConstructor
public class City {

    @Excel(name = "省份",mergeVertical = true)//mergeVertical 纵向合并内容相同的单元格 默认false
    private String province;
    @Excel(name = "城市")
    private String cityName;
    @Excel(name = "人口")
    private Integer population;
    @Excel(name = "排名")
    private Integer rank;


    public City(String cityName, Integer population, Integer rank, String province) {
        this.cityName = cityName;
        this.population = population;
        this.rank = rank;
        this.province = province;
    }
}
