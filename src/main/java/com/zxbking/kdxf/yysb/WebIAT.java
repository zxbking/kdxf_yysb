package com.zxbking.kdxf.yysb;

/**
 * 语音听写 WebAPI 接口调用示例
 * 运行方法：直接运行 main() 即可
 * 结果： 控制台输出语音听写结果信息
 * @author iflytek
 */

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * 语音听写 WebAPI 接口调用示例 接口文档（必看）：https://doc.xfyun.cn/rest_api/%E8%AF%AD%E9%9F%B3%E5%90%AC%E5%86%99.html
 * webapi 听写服务参考帖子（必看）：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=38947&extra=
 * 语音听写WebAPI 服务，热词使用方式：登陆开放平台https://www.xfyun.cn/后，找到控制台--我的应用---语音听写---服务管理--上传热词
 *（Very Important）创建完webapi应用添加听写服务之后一定要设置ip白名单，找到控制台--我的应用--设置ip白名单，如何设置参考：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=41891
 * 注意：热词只能在识别的时候会增加热词的识别权重，需要注意的是增加相应词条的识别率，但并不是绝对的，具体效果以您测试为准。
 * 错误码链接：https://www.xfyun.cn/document/error-code （code返回错误码时必看）
 * @author iflytek
 */

public class WebIAT {
    // 听写webapi接口地址
    public static final String WEBIAT_URL = "http://api.xfyun.cn/v1/service/v1/iat";
    // 应用APPID（必须为webapi类型应用，并开通语音听写服务，参考帖子如何创建一个webapi应用：http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=36481）
    public static final String APPID = "5cfdecd1";
    // 接口密钥（webapi类型应用开通听写服务后，控制台--我的应用---语音听写---相应服务的apikey）
    public static final String API_KEY = "753716cebe8aef4be7453c33569c73af";
    // 音频编码
    public static final String AUE = "raw";
    // 引擎类型
    //（听写服务：engine_type为识别引擎类型，开通webapi听写服务后默认识别普通话与英文：示例音频请在听写接口文档底部下载
    // sms16k（16k采样率、16bit普通话音频、单声道、pcm或者wav）
    // sms8k（8k采样率、16bit普通话音频、单声道、pcm或者wav）
    // sms-en16k（16k采样率，16bit英语音频，单声道，pcm或者wav）
    // sms-en8k（8k采样率，16bit英语音频，单声道，pcm或者wav）
    // 请使用cool edit Pro软件查看音频格式是否满足相应的识别引擎类型，不满足则识别为空（即返回的data为空，code为0），或者识别为错误文本）
    // 音频格式转换可参考（讯飞开放平台语音识别音频文件格式说明）：https://doc.xfyun.cn/rest_api/%E9%9F%B3%E9%A2%91%E6%A0%BC%E5%BC%8F%E8%AF%B4%E6%98%8E.html
    public static final String ENGINE_TYPE = "sms16k";
    // 后端点（取值范围0-10000ms）
    public static final String VAD_EOS = "10000";
    // 音频文件地址,示例音频请在听写接口文档底部下载
    public static final String AUDIO_PATH = "D:\\opt\\1560172755698.wav";


    /**
     * 听写 WebAPI 调用示例程序
     * @param args
     * @throws IOException
     */
//    public static void main(String[] args) throws IOException {
//
//    }
    // 返回code为错误码时，请查询https://www.xfyun.cn/document/error-code解决方案

    /**
     * 组装http请求头
     */
    public static Map<String, String> buildHttpHeader() throws UnsupportedEncodingException {
        String curTime = System.currentTimeMillis() / 1000L + "";
        String param = "{\"aue\":\""+AUE+"\""+",\"engine_type\":\"" + ENGINE_TYPE + "\""+",\"vad_eos\":\"" + VAD_EOS + "\"}";
        String paramBase64 = new String(Base64.encodeBase64(param.getBytes("UTF-8")));
        String checkSum = DigestUtils.md5Hex(API_KEY + curTime + paramBase64);
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        header.put("X-Param", paramBase64);
        header.put("X-CurTime", curTime);
        header.put("X-CheckSum", checkSum);
        header.put("X-Appid", APPID);
        return header;
    }
}

