package com.Lhan.personal_blog.controller;

import com.Lhan.personal_blog.common.base.Result;
import com.Lhan.personal_blog.common.enums.ErrorEnum;
import com.Lhan.personal_blog.component.PhoneRandomBuilder;
import com.Lhan.personal_blog.redis.StringRedisServiceImpl;
import com.Lhan.personal_blog.util.JsonResult;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

@RestController
public class GetPhoneCodeController {

    @Autowired
    StringRedisServiceImpl stringRedisService;


    private static final String REGISTER = "register";

    /**
     * 阿里云accessKeyId
     *
     */
    @Value("${aliyun.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.secret}")
    private String secret;

    /**
     * 短信模板
     */
    private static final String SIGN_NAME = "罗汉哥";

    @PostMapping(value = "/getCode",produces = MediaType.APPLICATION_JSON_VALUE)
    public Result getAuthCode(@RequestParam(value = "phone") String phone,@RequestParam("sign") String sign)
    {

        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        //判断手机号是否正确
        if(!Pattern.matches(regex,phone))
        {
            return Result.createWithErrorMessage(ErrorEnum.PHONE_NUMBER_ERROR);
        }

        String trueMsgCode = PhoneRandomBuilder.randomBuilder();

        // 在redis中保存手机号验证码并设置过期时间
        stringRedisService.set(phone,trueMsgCode);
        stringRedisService.expire(phone,80);

        String msgCode;
        //注册的短信模板
        if (REGISTER.equals(sign))
        {
            msgCode = "SMS_187756619";

        }
        else
        {
            msgCode = "SMS_187935372";

        }
        try
        {
            sendSmsResponse(phone,trueMsgCode,msgCode);
        }
        catch (ClientException e)
        {
            e.printStackTrace();
            return Result.createWithError();
        }
        return Result.createWithSuccessMessage();
    }


    public void sendSmsResponse(String phoneNumber, String code, String msgCode) throws ClientException {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
        //"***"分别填写自己的AccessKey ID和Secret
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, secret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Dysmsapi", "dysmsapi.aliyuncs.com");
        IAcsClient acsClient = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        //填写接收方的手机号码
        request.setPhoneNumbers(phoneNumber);
        //此处填写已申请的短信签名
        request.setSignName(SIGN_NAME);
        //此处填写获得的短信模版CODE
        request.setTemplateCode(msgCode);
        //笔者的短信模版中有${code}, 因此此处对应填写验证码
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

        // 判断是否发送成功
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
            //请求成功
            System.out.println("返回的状态码：" + sendSmsResponse.getCode());
            System.out.println("返回的信息：" + sendSmsResponse.getMessage());
        }
        else{
            System.out.println("发送短信失败");

        }
    }

}
