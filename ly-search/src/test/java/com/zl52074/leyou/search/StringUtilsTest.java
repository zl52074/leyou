package com.zl52074.leyou.search;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/17 15:32
 */

public class StringUtilsTest {
    @Test
    public void test(){
        List<String> names = new ArrayList<>();
        names.add("zhangsan");
        names.add("lisi");
        names.add("wangwu");
        System.out.println(StringUtils.join(names," "));
    }
}
