package com.example.cp.common.tool;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: mybatis 生成entity实体、mapper、xml 工具类
 * @Author: chenping
 * @Date: 2020-05-28
 */
public class MybatisGeneratorUtil {

    public static void main(String[] args) {
        generateMybatisCode();
    }

    public static boolean generateMybatisCode(){
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = false;//是否覆盖
        InputStream inputStream = MybatisGeneratorUtil.class.getClassLoader().getResourceAsStream("mybatis/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config;
        try {
            config = cp.parseConfiguration(inputStream);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (IOException | XMLParserException | InvalidConfigurationException | InterruptedException | SQLException e) {
            e.printStackTrace();
        }
        warnings.forEach(System.out::println);
        System.out.println("代码生成完毕>>>>>>>>>>>>");
        return  true;
    }
}
