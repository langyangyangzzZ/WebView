package com.example.webh5;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;

/**
 * @Package: com.example.webh5
 * @ClassName: WebViewActivity
 * @Author: szj
 * @CreateDate: 5/22/21 1:24 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        setTitle("Html页面");

        initView();

        String url = getIntent().getStringExtra("url");
        String htmlData = getIntent().getStringExtra("htmlData");
        if (isEmpty(url)) {
            initWeb(url,true);
        }else if(isEmpty(htmlData)){
            initWeb(htmlData,false);
        }
    }


    private void initWeb(String url, boolean isUrl) {
        AgentWeb mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(frameLayout, new LinearLayout.LayoutParams(-1, -1))
                .closeIndicator()
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .createAgentWeb()
                .ready()
                .get();
        mAgentWeb.getAgentWebSettings().getWebSettings().setJavaScriptEnabled(true);
        mAgentWeb.clearWebCache();
        if (isUrl) {
            mAgentWeb.getUrlLoader().loadUrl(url);
        }else{
            //解析 html 文件
            mAgentWeb.getUrlLoader().loadData(url,"text/html", "utf-8");
        }


    }


    //判空
    public boolean isEmpty(String str) {
        if (str != null && !str.equals("") && !str.isEmpty())
            return true;
        return false;
    }

    private void initView() {
        frameLayout = findViewById(R.id.frameLayout);
    }

}
