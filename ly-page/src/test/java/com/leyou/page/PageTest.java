package com.leyou.page;

import com.zl52074.leyou.LyPageApplication;
import com.zl52074.leyou.common.pojo.PageResult;
import com.zl52074.leyou.item.api.client.GoodsClient;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.page.service.PageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/7 20:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LyPageApplication.class)
public class PageTest {
    @Autowired
    private PageService pageService;
    @Autowired
    private GoodsClient goodsClient;

    /**
     * @description 所有页面转为静态
     * @param
     * @return void
     * @author zl52074
     * @time 2020/12/8 11:11
     */
    @Test
    public void createHtmlTest() {
        //查询spu
        PageResult<Spu> spuResult = goodsClient.querySpuOnlyByPage(1, 999, true, null);
        List<Spu> spus = spuResult.getItems();
        for(Spu spu:spus){
            pageService.createHtml(spu.getId());
        }

    }
}
