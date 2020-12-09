package com.zl52074.leyou.search.mq;

import com.zl52074.leyou.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: mq监听器
 * @author: zl52074
 * @time: 2020/12/8 14:51
 */
@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.update.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.insert","item.update"}
    ))
    public void ListenInsertOrUpdate(Long spuId){
        //获取消息，新增或更新索引
        searchService.createOrUpdateIndex(spuId);
        System.out.println("新增或更新索引"+spuId);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue",durable = "true"),
            exchange = @Exchange(name = "ly.item.exchange",type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
    ))
    public void ListenDelete(Long spuId){
        //获取消息，删除索引
        searchService.deleteIndex(spuId);
        System.out.println("删除索引"+spuId);
    }

}
