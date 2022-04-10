package org.example;

import okhttp3.*;
import org.junit.Test;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class YiCaiYao {

    @Scheduled(cron = "0 09 21 * * ?")  //每日0:5分开始报送,执行一次
    public void YiCaiYao_() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Map<String, String> mapList = new HashMap<String, String>();
        mapList.put("卢坤", "oUfcc5N8YZAOuV1P8UD2-YtnxoIs");
        mapList.put("王德勇", "oUfcc5PsZmUHdfHZNtDJ5AWtGr8I");
        mapList.put("张婷", "oUfcc5BKGS7_4bY-wdLqbQjwGpOc");
        mapList.put("艾永俊", "oUfcc5HGz4IqtHsCiSDqgUFo0e8k");
        mapList.put("蒋红辉", "oUfcc5Ip4oXdNFfAuv3VyS-sdcg4");
        mapList.put("赵善荣", "oUfcc5PqGCGkWav9ESr_ByZ8Cjvw");

        //使用for循环，直接提前报送30天，默认0为当天
        for (int i = 0; i < 1; i++) {
            //格式化日期
            Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //获取今天的日期
            Date today = new Date();
            System.out.println("今天是:" + f.format(today));
            //使用日历组件进行提前*天的报送
            Calendar c = Calendar.getInstance();
            c.setTime(today);
            c.add(Calendar.DAY_OF_MONTH, i);
            Date tomorrow = c.getTime();
            System.out.println("已报送:" + f.format(tomorrow));
            //遍历map，提交报送
            for (Map.Entry<String, String> entry : mapList.entrySet()) {
                System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "fillinTime=" + f.format(tomorrow) +
                        "&name=" + entry.getKey() +
                        "&companyName=建工集团" +
                        "&isConfirmed=0" +
                        "&isQuarntine=0" +
                        "&currentBodyState=0" +
                        "&enterprise=红河广源马堵山水电开发有限公司" +
                        "&livingAreaIsConfirmed=0" +
                        "&bodyStateRemark=" +
                        "&returnWork=1" +
                        "&returnWorkRemark=" +
                        "&quarntineAdress=" +
                        "&transportation=7" +
                        "&openId=" + entry.getValue() +
                        "&isInyiqu=0" +
                        "&isBywayofyiqu=0" +
                        "&ifBywayofyiqu=false" +
                        "&bywayofyiqu=" +
                        "&isTouchyiqu=0" +
                        "&touchyiqu=" +
                        "&ifTouchyiqu=false");
                Request request = new Request.Builder()
                        .url("https://ycy.gdyao.com.cn/percircle/epidemic/healthStatisticsDay/add")
                        .method("POST", body)
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36 MicroMessenger/7.0.9.501 NetType/WIFI MiniProgramEnv/Windows WindowsWechat")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();
                Response response = client.newCall(request).execute();
                String tempResponse = response.body().string();
                System.out.println(tempResponse);
            }
        }
    }
}
