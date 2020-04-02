package cn.bdqn.photography.utils;

import org.springframework.stereotype.Component;

@Component
public class SplitName {

    public Long[] styleName(String styleName) {
        String[] s = styleName.split(" ");
        Long[] style = new Long[s.length - 1];
        for (int i = 1; i < s.length; i++) {
            style[i - 1] = Long.valueOf(s[i]);
        }
        return style;
    }

}
