package com.example.cp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

import java.net.InetAddress;
import java.net.UnknownHostException;

@EnableScheduling
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@ServletComponentScan
@MapperScan("com.example.cp.mapper")
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        String port = context.getEnvironment().getProperty("server.port");
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            System.out.println("swagger 地址：http://"+ hostAddress +":"+port+"/swagger-ui.html");
            System.out.println("druid监控 地址：http://"+ hostAddress +":"+port+"/druid/index.html");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
//        System.out.println("swagger 地址：http://localhost:"+port+"/swagger-ui.html");
//        System.out.println("druid监控 地址：http://localhost:"+port+"/druid/index.html");
    }
}
