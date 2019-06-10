package com.zxbking.kdxf.yysb;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * Created by ykp on 2018/9/20.
 */
@Controller
public class yyController {
//    @RequestMapping(value = "home",method = RequestMethod.GET)
//    public String yylbglinsert(HttpServletResponse response){
//        //允许跨域请求
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT,OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//
//
//    }
    @RequestMapping("/")
    String test(HttpServletRequest request) {
        //逻辑处理
        request.setAttribute("key", "hello world");
        return "index";
    }
    @RequestMapping("/upload")
    @ResponseBody
    public String uploadMore(HttpServletRequest request,@RequestParam("file") MultipartFile[] multipartfiles ){
        try {
            if (null != multipartfiles && multipartfiles.length > 0) {
                //遍历并保存文件
                for (int i=0;i< multipartfiles.length;i++) {
                    MultipartFile file  = multipartfiles[i];
//                    String name = file.getOriginalFilename();//文件名
//                    String type = name.substring(name.lastIndexOf("."),name.length());
//                    long size = file.getSize();
                    String AUDIO_PATH = "d:/opt/"+(new Date().getTime())+".wav";
                    File file2 = new File("d:/opt/"+(new Date().getTime())+".wav");
                    file.transferTo(file2);
                    Map<String, String> header = WebIAT.buildHttpHeader();
                    byte[] audioByteArray = FileUtil.read(AUDIO_PATH);
                    String audioBase64 = new String(Base64.encodeBase64(audioByteArray), "UTF-8");
                    String result = HttpUtil.doPost1(WebIAT.WEBIAT_URL, header, "audio=" + URLEncoder.encode(audioBase64, "UTF-8"));
                    System.out.println("听写 WebAPI 接口调用结果：" + result);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "文件上传成功";
    }

}
