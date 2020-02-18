package cn.bdqn.photography.utils;


import org.springframework.stereotype.Component;

import java.util.Random;

@Component("round")
public class Round {

    public String round(){
        Random random=new Random();
        int val=random.nextInt(1000000);
        return String.valueOf("yp"+val);
    }

}