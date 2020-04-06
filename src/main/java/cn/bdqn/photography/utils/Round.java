package cn.bdqn.photography.utils;


import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component("round")
public class Round {

    public String round() {
        Random random = new Random();
        int val = random.nextInt(1000000000);
        String uuid = UUID.randomUUID().toString(); // 创建随机名
        return String.valueOf(val + uuid);
    }

}