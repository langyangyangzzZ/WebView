package com.example.webh5;

import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;

/**
 * @Package: com.example.webh5
 * @ClassName: WebViewUtil
 * @Author: szj
 * @CreateDate: 5/19/21 8:19 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewUtil {

    private WebViewUtil(){}
    private static volatile WebViewUtil mWebViewUtil;
    public static WebViewUtil getInstance(){
        if (mWebViewUtil == null) {
            synchronized (WebViewUtil.class){
                if (mWebViewUtil == null) {
                    mWebViewUtil = new  WebViewUtil();
                }
            }
        }
        return mWebViewUtil;
    }
    /**
     *
     * @param mWebView
     * @param xAxis
     * @param lowRisk 低风险
     * @param mediumRisk 中风险
     * @param highRisk 高风险
     */
    public  void loadLineSurveyStatistics(WebView mWebView, String xAxis, String lowRisk, String mediumRisk, String highRisk) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("setXAxisData([" + xAxis + "])", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
            mWebView.evaluateJavascript("setLowValueDate([" + lowRisk + "])", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
            mWebView.evaluateJavascript("setMidValueDate([" + mediumRisk + "])", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
            mWebView.evaluateJavascript("setHigValueDate([" + highRisk + "])", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {

                }
            });
            mWebView.evaluateJavascript("update()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String s) {

                }
            });
        }
    }


    public static void loadLine(WebView mWebView,String xAxis,String value) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript("setXAxisData([" + xAxis + "])", value1 -> {

            });
            mWebView.evaluateJavascript("setValueDate([" + value + "])", value12 -> {

            });
            mWebView.evaluateJavascript("update()", s -> {

            });
        }
    }
}
