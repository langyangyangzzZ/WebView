package com.example.webh5;

import android.webkit.WebView;

import com.just.agentweb.WebViewClient;

/**
 * @Package: com.example.webh5
 * @ClassName: MyWebViewClient
 * @Author: szj
 * @CreateDate: 5/19/21 8:10 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyWebViewClient extends WebViewClient {

    //一种线条
    private StringBuffer value;
    private ClientCallback2 clientCallback2;

    public MyWebViewClient(StringBuffer xAxis, StringBuffer value, ClientCallback2 callback) {
        this.xAxis = xAxis;
        this.value = value;
        this.clientCallback2 = callback;
    }
    public interface ClientCallback2 {
        //普通线性统计表 1 条数据
        void refreshChart(WebView view, StringBuffer xAxis, StringBuffer value);
    }

    @Override
    public void onPageFinished(android.webkit.WebView view, String url) {
        super.onPageFinished(view, url);
        if (clientCallback1 != null) {
            clientCallback1.refreshChart(view, xAxis, value1, value2, value3);
        } else if (clientCallback2 != null) {
            clientCallback2.refreshChart(view, xAxis, value);
        }
    }


    //三种线条
    private StringBuffer xAxis;
    private StringBuffer value1;
    private StringBuffer value2;
    private StringBuffer value3;
    private ClientCallback clientCallback1;

    public MyWebViewClient(StringBuffer xAxis, StringBuffer value1, StringBuffer value2, StringBuffer value3, ClientCallback callback) {
        this.xAxis = xAxis;
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
        clientCallback1 = callback;
    }
    public interface ClientCallback {
        // 3 条数据
        void refreshChart(WebView view, StringBuffer xAxis, StringBuffer value1, StringBuffer value2, StringBuffer value3);
    }
}
