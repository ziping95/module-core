package com.wzp.module.core.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.DigestUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class WeChatUtil {

    /**
     * 生成微信支付签名
     */
    public static String createSign(Charset charset, SortedMap<String, Object> packageParams, String API_KEY) throws Exception  {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, Object>> es = packageParams.entrySet();
        for (Map.Entry<String, Object> entry : es) {
            String k = entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(API_KEY);
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(charset)).toUpperCase();
    }

    /**
     * 测试微信支付回调请求
     * @return
     * @throws Exception
     */
    public static int createPayNotifyRequest() throws Exception {
        SortedMap<String,Object> params = new TreeMap<>();
        params.put("appid","wx7be86d20a7f24de3");
        params.put("mch_id","1325341001");
        params.put("nonce_str",StringUtil.getRandomString(32));
        params.put("sign_type","MD5");
        params.put("result_code","SUCCESS");
        params.put("openid","oeYAP1aYJJM8RIa7ksCrGfen1iMo");
        params.put("is_subscribe","Y");
        params.put("trade_type","JSAPI");
        params.put("bank_type","CMC");
        params.put("total_fee","100");
        params.put("cash_fee","100");
        params.put("transaction_id","1217752501201407033233368018");
        params.put("out_trade_no","default20200319Ueb7kKexF6");
        params.put("attach","vip");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        params.put("time_end",simpleDateFormat.format(new Date()));
        params.put("sign",WeChatUtil.createSign(StandardCharsets.UTF_8,params,"9a69ebe4fb9e11e5a784001e676c6df4"));
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1/api/open/order/wxpay/payNotify");
        httpPost.setEntity(new ByteArrayEntity(FileUtil.createXmlFromMap(params).getBytes()));
        return httpClient.execute(httpPost).getStatusLine().getStatusCode();
    }
}
