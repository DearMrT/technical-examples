package com.mrt.dingding;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *  钉钉发送消息有两种模式
 *  1. 基于关键词
 *  2. 基于sign发送
 * @Author: Mr.T
 * @Date: 2024/4/23
 */
@Slf4j
public class DingDingMessage {
    /**
     *  可以调整为自己地址
     *  钉钉机器人设置时的地址
     */
    private static String url = "https://oapi.dingtalk.com/robot/send?access_token=6ebfe3d9e415b88844136947d33575c655f4ce7dbf7a9fac49f174811a5f965d";

    /**
     *  调整为自己秘钥
     * 秘钥
     */
    private static String secret = "SEC3ad384f0c7b5f6c5d092daf29377c3b526daf517400481834c9e1da9557f423c";


    /**
     * 发送钉钉消息
     * @param title
     * @param msg
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     * @throws InvalidKeyException
     */
    public static void sendDingdingTalkMessage(String title,String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {

        Long timestamp = System.currentTimeMillis();
        System.out.println(timestamp);
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");

        //sign字段和timestamp字段必须拼接到请求URL上，否则会出现 310000 的错误信息
        DingTalkClient client = new DefaultDingTalkClient(url+"&sign="+sign+"&timestamp="+timestamp);
        OapiRobotSendRequest req = new OapiRobotSendRequest();
        OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
        markdown.setTitle(title);
        markdown.setText(msg);
        //设置消息类型
        req.setMsgtype("markdown");
        req.setMarkdown(markdown);
        OapiRobotSendResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (Exception ex) {
            log.error("消息发送异常，{}",ex.getMessage());
            return;
        }
        log.info("消息发送结果:{}",rsp.getBody());
    }


    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        sendDingdingTalkMessage("标题","消息");
    }

}
