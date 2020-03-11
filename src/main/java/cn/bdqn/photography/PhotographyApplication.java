package cn.bdqn.photography;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = {"cn.bdqn.photography.shootUser.mapper",
        "cn.bdqn.photography.common.mapper","cn.bdqn.photography.shootattention.mapper",
"cn.bdqn.photography.shootimages.mapper","cn.bdqn.photography.shootinfo.mapper",
"cn.bdqn.photography.shootletter.mapper","cn.bdqn.photography.shootselfie.mapper",
"cn.bdqn.photography.shoottheme.mapper"})
//yang
public class PhotographyApplication {
    //yanglongyanglong
    public static void main(String[] args) {
        SpringApplication.run(PhotographyApplication.class, args);
    }

}