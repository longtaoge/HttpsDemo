package org.xiangbalao.httpsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import okhttp3.Call;
import okhttp3.Request;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_content;
    private Button bt_xutils;
    private Button bt_okhttps;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = "https://kyfw.12306.cn/otn/";
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }


    public void postXutilsHttps() {
        RequestParams params = new RequestParams();
        HttpUtils http = new HttpUtils();

        http.send(HttpRequest.HttpMethod.POST,
                url,

                params,
                new RequestCallBack<String>() {
                    @Override
                    public void onStart() {
                        tv_content.setText("请求中……");

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {


                        tv_content.setText("请求中……" + current / total);

                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        tv_content.setText("upload response:" + responseInfo.result);
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {

                        tv_content.setText("请求失败: " + msg);

                    }
                });
    }


    private void initView() {
        tv_content = (TextView) findViewById(R.id.tv_content);
        bt_xutils = (Button) findViewById(R.id.bt_xutils);
        bt_xutils.setOnClickListener(this);
        bt_okhttps = (Button) findViewById(R.id.bt_okhttps);
        bt_okhttps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_xutils:
                postXutilsHttps();
                break;
            case R.id.bt_okhttps:


                getOkhttps();


                break;
        }
    }


    /**
     * okhttps 测试
     */
    private void getOkhttps() {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {

            @Override
            public void onError(Call call, Exception e, int id) {
                tv_content.setText(e.toString());
            }

            @Override
            public void onResponse(String response, int id) {

                tv_content.setText(response);
            }


            public void onBefore(Request request,int id) {
                super.onBefore(request,id);

                tv_content.setText("请求中……");
            }


            public void onAfter(int id) {
                super.onAfter(id);
            }




        });
    }
}
