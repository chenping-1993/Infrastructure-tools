package com.example.cp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling
@Slf4j
@ServletComponentScan
@MapperScan("com.example.cp.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            log.info("swagger 地址：http://"+ hostAddress +":"+port+"/swagger-ui.html");
            log.info("druid监控 地址：http://"+ hostAddress +":"+port+"/druid/index.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
