package cn.bdqn.photography;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = {"cn.bdqn.photography.shootUser.mapper",
        "cn.bdqn.photography.common.mapper","cn.bdqn.photography.shootattention.mapper",
"cn.bdqn.photography.shootimages.mapper","cn.bdqn.photography.shootinfo.mapper",
"cn.bdqn.photography.shootletter.mapper","cn.bdqn.photography.shootselfie.mapper",
"cn.bdqn.photography.shoottheme.mapper","cn.bdqn.photography.houtai.mapper",
"cn.bdqn.photography.shootOrder.mapper","cn.bdqn.photography.shootinform.mapper",
"cn.bdqn.photography.shootevaluate.mapper"})
@ComponentScan(basePackages = {"cn.bdqn"})
@Component(value = "cn.bdqn")
public class PhotographyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PhotographyApplication.class, args);
    }

}

