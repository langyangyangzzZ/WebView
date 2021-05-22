package com.example.webh5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private StringBuffer xAxis;
    private StringBuffer value1;
    private StringBuffer value2;
    private StringBuffer value3;
    private FrameLayout frameLayout, frameLayout2;
    private StringBuffer value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);
        frameLayout2 = findViewById(R.id.frameLayout2);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add((i + 1) + "号");
        }
        //三条图
        setlineData1(list);
        //一条图
        setlineData2(list);
    }


    private Random mRandom = new Random();

    private void setlineData1(ArrayList<String> strs) {
        //x轴
        xAxis = new StringBuffer();
        value1 = new StringBuffer();
        value2 = new StringBuffer();
        value3 = new StringBuffer();

        for (int i = 0; i < strs.size(); i++) {
            if (i != 0) {
                xAxis.append(",");
                value1.append(",");
                value2.append(",");
                value3.append(",");
            }
            xAxis.append("'");
            xAxis.append(strs.get(i));
            xAxis.append("'");

            value1.append("'");
            value1.append(mRandom.nextInt(20));
            value1.append("'");

            value2.append("'");
            value2.append(mRandom.nextInt(40));
            value2.append("'");

            value3.append("'");
            value3.append(mRandom.nextInt(60));
            value3.append("'");
        }
        initWeb("file:///android_asset/echartMoreLineStyle.html");
    }

    private void initWeb(String url) {
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(frameLayout, new FrameLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setWebViewClient(new MyWebViewClient(xAxis, value1, value2, value3, (view, xAxis, v1, v2, v3) ->
                        WebViewUtil.getInstance().loadLineSurveyStatistics(view, xAxis.toString(), v1.toString(), v2.toString(), v3.toString())))
//                .setWebViewClient(new WebViewClient(){
//                    @Override
//                    public void onPageFinished(WebView view, String url) {
//                        super.onPageFinished(view, url);
//                        WebViewUtil.getInstance().loadLineSurveyStatistics(view, xAxis.toString(), value1.toString(), value2.toString(), value3.toString());
//                    }
//                })
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()
                .ready()
                .get();

        mAgentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(true);
        mAgentWeb.clearWebCache();
        mAgentWeb.getUrlLoader().loadUrl(url);
    }


    private void setlineData2(List<String> strs) {
        //x轴
        xAxis = new StringBuffer();
        value = new StringBuffer();

        for (int i = 0; i < strs.size(); i++) {
            if (i != 0) {
                xAxis.append(",");
                value.append(",");
            }
            xAxis.append("'");
            xAxis.append(strs.get(i));
            xAxis.append("'");

            value.append("'");
            value.append(mRandom.nextInt(60));
            value.append("'");
        }
        initWeb2("file:///android_asset/echart.html");
    }

    private void initWeb2(String url) {
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(frameLayout2, new FrameLayout.LayoutParams(-1, -1))
                .closeIndicator()
//                .setWebViewClient(new MyWebViewClient(xAxis, value, (view, xAxis, value) ->
//                        WebViewUtil.getInstance().loadLine(view, xAxis.toString(), value.toString())))
                .setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        super.onPageFinished(view, url);
                        WebViewUtil.getInstance().loadLine(view, xAxis.toString(), value1.toString());
                    }
                })

                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()
                .ready()
                .get();
        mAgentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(true);
        mAgentWeb.clearWebCache();
        mAgentWeb.getUrlLoader().loadUrl(url);
    }

    String htmlData = "<p style=\"text-align: center;\"><span style=\"font-size:18px\"><strong>帮助说明</strong></span></p>\n" +
            "\n" +
            "<p style=\"text-align: justify;\"><span style=\"font-size:18px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 我是 html 标签内容</span>\n" +
            "</p>\n" +
            "\n" +
            "<p style=\"text-align: justify;\"><span style=\"font-size:18px\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 😛😛😛😛</span>\n" +
            "</p>\n" +
            "\n" +
            "\n" +
            "<p style=\"text-align:center\"><img alt=\"\" src=\"https://alifei02.cfp.cn/creative/vcg/800/new/VCG211147957933.jpg\"\n" +
            "                                  width=\"100%\"/></p>\n" +
            "\n" +
            "<p><span style=\"font-size:18px\"><span style=\"color:red\">注</span>:记得点赞评论收藏哦~~&nbsp;</span></p>";

    public void OnHtmlButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//        intent.putExtra("url", "http://www.baidu.com");
//        intent.putExtra("url", "file:///android_asset/help.html");
        intent.putExtra("htmlData", htmlData);
        startActivity(intent);
    }
}