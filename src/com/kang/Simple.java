package com.kang;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

/*********************************************************************************************/
/**********************************         文字识别          ********************************/
/*********************************************************************************************/
public class Simple {

    public AipOcr getAipOcr(){
        String APP_ID = "16846199";
        String API_KEY = "rHGTbL4vGRrx4MyR18eMnTyI";
        String SECRET_KEY = "YdijolQ7GZ1ynGr8FBFZbKuU4rhI8raV";
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(12000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        //client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        //client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "log4j.properties");
        return client;
    }

    
    @Test
    public void fun1() {

        AipOcr client = getAipOcr();

        // 调用接口
        String path = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }

    //用户向服务请求识别某张图中的所有文字
    @Test
    public void fun2() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("language_type", "CHN_ENG");//识别语言类型，默认为CHN_ENG
        options.put("detect_direction", "true");//是否检测图像朝向，默认不检测，即：false
        options.put("detect_language", "true");//是否检测语言，默认不检测。当前支持（中文、英语、日语、韩语）
        options.put("probability", "true");//否返回识别结果中每一行的置信度


        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res = client.basicGeneral(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        /*byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg");
        res = client.basicGeneral(file, options);
        System.out.println(res.toString(2));*/

        // 通用文字识别, 图片参数为远程url图片
        /*String url = "https://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%B8%A6%E6%96%87%E5%AD%97%E7%9A%84%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=undefined&hd=undefined&latest=undefined&copyright=undefined&cs=1891476059,2353774149&os=170071656,3290683930&simid=4241681120,757048551&pn=23&rn=1&di=138930&ln=1028&fr=&fmq=1563578856900_R&fm=&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&hs=2&objurl=http%3A%2F%2Fwx1.sinaimg.cn%2Flarge%2F005CjUdnly1fg4w7fr1v3j30hj0mytan.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined";
        JSONObject res1 = client.basicGeneralUrl(url, options);
        System.out.println(res1.toString(2));*/
    }

    private byte[] readFile(String filePath) {
        File file = new File(filePath);
        ByteArrayOutputStream out = null;
        FileInputStream in =null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int i = 0;
            while ((i = in.read(b)) != -1) {
                String str = new String(b,0,i);
                out.write(str.getBytes(), 0, str.getBytes().length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] bytes = out.toByteArray();
        return bytes;
    }

    //用户向服务请求识别某张图中的所有文字，相对于通用文字识别该产品精度更高，但是识别耗时会稍长。
    @Test
    public void fun3() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("probability", "true");


        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res = client.basicAccurateGeneral(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg");
        res = client.basicAccurateGeneral(file, options);
        System.out.println(res.toString(2));
    }

    //用户向服务请求识别某张图中的所有文字，并返回文字在图中的位置信息。
    @Test
    public void fun4() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("recognize_granularity", "big");
        options.put("language_type", "CHN_ENG");
        options.put("detect_direction", "true");
        options.put("detect_language", "true");
        options.put("vertexes_location", "true");
        options.put("probability", "true");


        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res = client.general(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg");
        res = client.general(file, options);
        System.out.println(res.toString(2));

        // 通用文字识别（含位置信息版）, 图片参数为远程url图片
        String url = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res1 = client.generalUrl(url, options);
        System.out.println(res1.toString(2));
    }

    //用户向服务请求识别一些网络上背景复杂，特殊字体的文字。
    @Test
    public void fun5() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_language", "true");


        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res = client.webImage(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg");
        res = client.webImage(file, options);
        System.out.println(res.toString(2));

        // 网络图片文字识别, 图片参数为远程url图片
        String url = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\baidu1.jpeg";
        JSONObject res1 = client.webImageUrl(url, options);
        System.out.println(res1.toString(2));
    }

    //用户向服务请求识别身份证，身份证识别包括正面和背面。
    @Test
    public void fun6() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("detect_direction", "true");
        options.put("detect_risk", "false");

        String idCardSide = "front";//front：身份证含照片的一面；back：身份证带国徽的一面

        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\my1.jpg";
        JSONObject res = client.idcard(image, idCardSide, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\my1.jpg");
        res = client.idcard(file, idCardSide, options);
        System.out.println(res.toString(2));
    }


    //识别银行卡并返回卡号和发卡行。
    @Test
    public void fun7() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\my3.jpg";
        JSONObject res = client.bankcard(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\my3.jpg");
        res = client.bankcard(file, options);
        System.out.println(res.toString(2));
    }

    //此接口需要您在页面中提交合作咨询开通权限识别条形码、二维码中包含的URL或其他信息内容
    @Test
    public void fun8() {
        AipOcr client = getAipOcr();

        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();


        // 参数为本地路径
        String image = "C:\\Users\\Dell\\Desktop\\小图标\\qr\\my3.jpg";
        JSONObject res = client.qrcode(image, options);
        System.out.println(res.toString(2));

        // 参数为二进制数组
        byte[] file = readFile("C:\\Users\\Dell\\Desktop\\小图标\\qr\\my3.jpg");
        res = client.qrcode(file, options);
        System.out.println(res.toString(2));
    }

}
