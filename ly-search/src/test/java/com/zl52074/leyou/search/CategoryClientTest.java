package com.zl52074.leyou.search;

import com.zl52074.leyou.LySearchApplication;
//import com.zl52074.leyou.item.api.client.CategoryClient;
import com.zl52074.leyou.item.api.client.CategoryClient;
import com.zl52074.leyou.item.po.Category;
import org.apache.http.util.Asserts;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;



/**
 * @description:
 * @author: zl52074
 * @time: 2020/11/15 22:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
public class CategoryClientTest {
    @Autowired
    private CategoryClient categoryClient;

    @Test
    public void queryCategoryByIds(){
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(1L, 2L, 3L));
        Assert.assertEquals(3, categories.size());
        for(Category category:categories){
            System.out.println(category);
        }
    }
}
