package cn.bdqn.photography;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.bdqn.photography.shootUser.mapper",
        "cn.bdqn.photography.common.mapper"})
public class PhotographyApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotographyApplication.class, args);
    }

}