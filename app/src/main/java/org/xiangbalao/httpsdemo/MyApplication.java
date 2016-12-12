package org.xiangbalao.httpsdemo;

import android.app.Application;


import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import org.xiangbalao.httpsdemo.logutils.LogUtil;



import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;



public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initNet();
    }

    private void initNet() {
        HttpsUtils.SSLParams sslParams =  HttpsUtils.getSslSocketFactory(null, null, null);;
        try {
            sslParams = HttpsUtils.getSslSocketFactory(new InputStream[]{getAssets().open("kyfw.12306.cn.crt")}, null, null);
            LogUtil.i(MyApplication.class.getSimpleName(),inputStream2String(new InputStream[]{getAssets().open("kyfw.12306.cn.crt")}));
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i(MyApplication.class.getSimpleName(),e.toString());
           // sslParams =  HttpsUtils.getSslSocketFactory(null, null, null);
        }

      
        OkHttpClient okHttpClients = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .addInterceptor(new LoggerInterceptor("OkHttpUtils"))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        // 较验服务器
                        LogUtil.i(MyApplication.class.getSimpleName(), hostname);
                        return true;
                    }
                })

                .sslSocketFactory(sslParams.sSLSocketFactory) // https 证书
                .build();



        OkHttpUtils.initClient(okHttpClients);




    }







    public   String   inputStream2String   (InputStream []  inS)   throws   IOException   {

        InputStream in=inS[0];


        StringBuffer   out   =   new   StringBuffer();
        byte[]   b   =   new   byte[4096];
        for   (int   n;   (n   =   in.read(b))   !=   -1;)   {
            out.append(new   String(b,   0,   n));
        }
        return   out.toString();
    }



}
