package cn.bdqn.photography.config;

import org.springframework.context.annotation.Configuration;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 支付宝沙箱支付配置
 */
@Configuration
public class AliPayConfig {

    //↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
    //    netapp映射的地址，，，根据自己的需要修改
    private static String neturl = "http://localhost:8088/shoot-user";

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号 按照我文章图上的信息填写
    public static String app_id = "2016101900722243";

    // 商户私钥，您的PKCS8格式RSA2私钥  刚刚生成的私钥直接复制填写
    public static String merchant_private_key ="MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCy1vTL3ZnQ328jP1jKTyazpoyj6Q9M9x3E4xbXYQsYPsu/Aw73umy16VvhaCv+o37ohZ/juGraYub24lgkOTeD8t3gMFiAFtaw0xexUTTnrQjwow0xI0Xs+KAyyPFCOEbF5UBnG7J7llETSMHnzf1A+DTtJAoOfbxsqrAt/0tn4O9fd/1bWMXg6ghWUWcXmkhgaT/EnK3sSxP32TodXeXd/DQrmI119JByxe1/ktJoi0WbLQuqahakKMGNSkiwz9pmPEWcjLPWcKZu7C9BPOK67Gpwu0afzvbEjeHlLtJ/r655O5De9h4Jl/UUOrovC1a+BRR/0a+71TSaa1OAoNPjAgMBAAECggEAV5v4Ffq8BFfAkQrSS2FU+0MGuLFOXozcV3xLzzzp+3mfxDYSWgdIhCEKcrTegILFg9EaD6tjJRK4RI+DUFN7P7d1PANTB4SorbzraWD3wtrAe2nU55J5CeqOR/KqRZ9eBEoXL6ug1uw/UWweGZmOjApLmzRBfPHZoX5Iim/7F6qGtKu7kl0rJmEeLi0HqPQDlCk+WVjxzo38212AVpxNmfcs2XtaKMvktWcK+p9viMa5wTT1i6A/XeOKRaU69AqtW8pgnpbJOL3w4tVyitJ8q/+LtnOWRpoYIlXXPVMT2qMUzGGCRSNDKwbLe87T0QCyxC/RYJ+AR4h5OLSbTL9/uQKBgQDdkeqHZxo2MmGC2GAlWi933BHnllSeRWBcXdZLRZPmiGPqBuKv2dZa0OhgSZFEp22jdllmebsU1z6pZELdLtjupPj7ZQPu78rlUvF5DziBtPZqa2xh3L6Ze0w0p4dfYZzuCUOPIVhmUpIcf371J8ZhJimxf9jwQb8dbUMHahJZtwKBgQDOoTj30rt/yyQqkGBRVDJ1jM+R5ATRCZg/ndHxsE4zsDw5pfTq6r9hcCyqOvyFX0ZT3YDIfFVC9ifl9qd9bDcA4cIiaWxeK3nw6gtl326N/enh1PHJCRI9h+pHZc7MlAXSmRx/c3aQTEcDGcQ3iLZnqvUnVgzZzFq1FLHu/aHHNQKBgDCMgG8a0Et/zo31C1wj3rLMrb+o2PG1EeArT4E+xaGF0R6+U31kpetQSxqOBB6e8Q8YJ46krbFrWHeVWHFOion/6+5pu+rm7/Y/rllwVFlXZYb78sueqsTGl3QSIT9tDA28SelazRvei9ViulbNWFKBIjGaadZ7EX1JK568RzkRAoGAJup4ZzxaMeRZiGaurUKVSkxd35U5TwFmmjyoCMPyxotfnF/kM8ZIixpsXxdC7f+WqD7kfV3lVPo1Y3tD6jxSU6kxPffrAAViLvx1bxdYpAd5YB4R95HszIfnmNDU1PTf790EYACoCN9E1JGqwebOq9Gn4GO1geOaBdbC+EbujC0CgYEAr2JYLiHNmYMPxPRseIVpQ7xOO4D2ImWJey2bkV6lszh/gKMhCOI2ipMcNrBCxTEcMO3p7fYgo8qA/V+72XtTjcu343zH6Vv60lbRIB6HMCYcrluPOJxchCFxIHjjYu5IFiq2qnaSLNF81EY+WcTP7BiDw4QlOdYx1vGAvB/PTpY=";

    // 支付宝公钥,对应APPID下的支付宝公钥。 按照我文章图上的信息填写支付宝公钥，别填成商户公钥
    public static String alipay_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDIgHnOn7LLILlKETd6BFRJ0GqgS2Y3mn1wMQmyh9zEyWlz5p1zrahRahbXAfCfSqshSNfqOmAQzSHRVjCqjsAw1jyqrXaPdKBmr90DIpIxmIyKXv4GGAkPyJ/6FTFY99uhpiq0qadD/uSzQsefWo0aTvP/65zi3eof7TcZ32oWpwIDAQAB";
    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static String notify_url = neturl+"/alipay/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，其实就是你的一个支付完成后返回的页面URL
    public static String return_url = neturl+"/alipay/return_url";

    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

