package com.leyou.sms.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.leyou.sms.config.SmsProperties;
import com.zl52074.leyou.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: zl52074
 * @time: 2020/12/16 14:06
 */
@Slf4j
@Component
@EnableConfigurationProperties()
public class SmsUtil {
    @Autowired
    private SmsProperties smsProperties;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String PREFIX = "sms:phone:";
    private static final long SMS_MIN_INTERVAL_IN_MILLIS = 60*1000L;

    public CommonResponse sendSms(String phoneNumber,String signName,String templateCode,String templateParam){
        String key = PREFIX+phoneNumber;
        String lastTime = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotBlank(lastTime)){
            long last = Long.valueOf("1608188144135");
            if(System.currentTimeMillis()-last < SMS_MIN_INTERVAL_IN_MILLIS){
                log.info("【短信服务】发送频率过高被拦截,phoneNumber:{}",phoneNumber);
                return null;
            }
        }
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsProperties.getAccessKeyId(), smsProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", signName);
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", templateParam);
        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
            Map<String, String> map = JsonUtils.parseMap(response.getData(), String.class, String.class);
            if("!OK".equals(map.get("Code"))){
                log.info("【短信服务】发送短信失败,phoneNumber:{}, message:{}",phoneNumber,map.get("Message"));
            }
            log.info("【短信服务】发送短信验证码,phoneNumber:{}",phoneNumber);
            //短信发送成功后，写入redis,存活时间为1分钟
            redisTemplate.opsForValue().set(key,String.valueOf(System.currentTimeMillis()),1L, TimeUnit.MINUTES);

        } catch (ClientException e) {
            log.error("【短信服务】短信发送异常,phoneNumber:{}",phoneNumber,e);
        }
        return response;
    }
}
