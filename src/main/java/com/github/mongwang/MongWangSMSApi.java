package com.github.mongwang;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 梦网短信发送
 */
@Component
@Data
@Slf4j
public class MongWangSMSApi {
    //获取apikey
    @Value("${mj.mengWang.smApiKey}")
    private String apikey;

    public boolean sendSms(String phone, String content) {
        if (StringUtils.hasLength(apikey)) {
            try {
                String body = "apikey=" + apikey + "&mobile=" + phone + "&content=" + content;
                String result = HttpClientUtil.post("http://api01.monyun.cn:7901/sms/v2/std/single_send", body, "application/x-www-form-urlencoded", "GBK", 10000, 10000);
                log.info("MongWangSMSApi----sendSms----" + result);
                JSONObject jsonObject = JSONUtil.parseObj(result);
                return (Integer) (jsonObject.get("result")) == 0;
            } catch (Exception e) {
                log.error("MongWangSMSApi----sendSms----is---error", e);
            }
        }
        return false;
    }
}
