package com.zl52074.leyou.sms;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.leyou.LySmsApplication;
import com.leyou.sms.utils.SmsUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.3</version>
</dependency>
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySmsApplication.class)
public class SendSms {
    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private AmqpTemplate amqpTemplate;

    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "xx", "xx");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "17695459574");
        request.putQueryParameter("SignName", "leyou商城");
        request.putQueryParameter("TemplateCode", "SMS_206750650");
        request.putQueryParameter("TemplateParam", "{\"code\":\"6666\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void sendTest(){
        smsUtil.sendSms("17695459574", "leyou商城", "SMS_206750650", "\"code\":\"6666\"");
    }

    @Test
    public void mqSmsTest() throws InterruptedException {
        Map<String,String> msg = new HashMap<>();
        msg.put("phone", "17695459574");
        msg.put("code", "6666");
        amqpTemplate.convertAndSend("ly.sms.exchange", "sms.verify.code",msg);
        Thread.sleep(10000);
    }
}
