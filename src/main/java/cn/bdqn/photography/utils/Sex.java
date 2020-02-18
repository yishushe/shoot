package cn.bdqn.photography.utils;

import org.springframework.stereotype.Component;

@Component("sex")
public class Sex {

    public static Long transition(String sex){
         if(sex.equals("男")){
             return 1l;
         }else {
             return 2l;
         }
    }

}
