package com.zl52074.leyou.page.service.impl;

import com.netflix.ribbon.template.TemplateParser;
import com.zl52074.leyou.item.api.client.BrandClient;
import com.zl52074.leyou.item.api.client.CategoryClient;
import com.zl52074.leyou.item.api.client.GoodsClient;
import com.zl52074.leyou.item.api.client.SpecClient;
import com.zl52074.leyou.item.bo.SpecGroupBO;
import com.zl52074.leyou.item.bo.SpuBO;
import com.zl52074.leyou.item.po.Brand;
import com.zl52074.leyou.item.po.Category;
import com.zl52074.leyou.item.po.Spu;
import com.zl52074.leyou.item.po.SpuDetail;
import com.zl52074.leyou.item.vo.SkuVO;
import com.zl52074.leyou.page.service.PageService;
import org.bouncycastle.jcajce.provider.symmetric.TEA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/3 18:38
 */
@Service
public class PageServiceImpl implements PageService {
    private static String ITEM_PAGE_PATH = "C:/WorkSpace/Leyou/leyou-portal/items";
    @Autowired
    private BrandClient brandClient;
    @Autowired
    private SpecClient specClient;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private CategoryClient categoryClient;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    public Map<String, Object> loadModel(Long id) {
        SpuBO spu = goodsClient.queryItemById(id);
        List<SkuVO> skus = spu.getSkus();
        SpuDetail detail = spu.getSpuDetail();
        Brand brand = brandClient.queryBrandById(spu.getBrandId());
        List<Category> categories = categoryClient.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        List<SpecGroupBO> specs = specClient.querySpecsByCid(spu.getCid3());
        Map<String, Object> map = new HashMap<>();
        map.put("title", spu.getTitle());
        map.put("subTitle", spu.getSubTitle());
        map.put("skus", skus);
        map.put("detail", detail);
        map.put("brand", brand);
        map.put("categories", categories);
        map.put("specs", specs);
        return map;
    }

    /**
     * @description 根据模板生成静态页到文件
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 17:39
     */
    public void createHtml(Long spuId){
        Context context = new Context();
        context.setVariables(this.loadModel(spuId));
        File dest = new File(ITEM_PAGE_PATH,spuId+".html");
        //判断文件是否已存在
        if(dest.exists()){
            dest.delete();
        }
        try (PrintWriter writer= new PrintWriter(dest,"UTF-8")){
            templateEngine.process("item",context,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @description 删除静态页
     * @param spuId
     * @return void
     * @author zl52074
     * @time 2020/12/8 17:39
     */
    @Override
    public void deleteHtml(long spuId) {
        File dest = new File(ITEM_PAGE_PATH,spuId+".html");
        //判断文件是否已存在
        if(dest.exists()){
            dest.delete();
        }
    }
}
