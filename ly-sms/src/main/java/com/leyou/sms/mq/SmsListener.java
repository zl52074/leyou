package com.leyou.sms.mq;

import com.leyou.sms.config.SmsProperties;
import com.leyou.sms.utils.SmsUtil;
import com.zl52074.leyou.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/16 15:04
 */
@Slf4j
@Component
@EnableConfigurationProperties()
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private SmsProperties smsProperties;

    /**
     * @description 发送短信验证码
     * @param msg
     * @return void
     * @author zl52074
     * @time 2020/12/16 15:10
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.verify.code.queue",durable = "true"),
            exchange = @Exchange(name = "ly.sms.exchange",type = ExchangeTypes.TOPIC),
            key = {"sms.verify.code"}
    ))
    public void SendMsg(Map<String,String> msg){
        //非空判断
        if(CollectionUtils.isEmpty(msg)){
            return;
        }
        String phone = msg.remove("phone"); //返回删除的值
        if(StringUtils.isBlank(phone)){
            return;
        }
        smsUtil.sendSms(phone, smsProperties.getSignName(),smsProperties.getTemplateCode(), JsonUtils.serialize(msg));
    }


}
