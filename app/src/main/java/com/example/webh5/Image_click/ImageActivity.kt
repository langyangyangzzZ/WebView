package com.example.webh5.Image_click

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.webh5.adapter.MyPageAdapter
import com.example.webh5.R
import com.example.webh5.util.SwitchDialogFragment
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient


/**
 *
 * @Package: com.example.webh5
 * @ClassName: ImageActivity
 * @Author: szj
 * @CreateDate: 5/31/21 5:02 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
class ImageActivity : AppCompatActivity() {
    private val frameLayout by lazy {
        findViewById<FrameLayout>(R.id.frameLayout)
    }

    lateinit var mAgentWeb :AgentWeb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        setTitle("Android 与 Web 图片/文字操作")
        val webtext = WebViewText()
        initWeb(webtext.format)
    }


    @SuppressLint("AddJavascriptInterface", "SetJavaScriptEnabled")
    private fun initWeb(url: String) {
         mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(frameLayout, FrameLayout.LayoutParams(-1, -1))
            .closeIndicator()
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK) //打开其他应用时，弹窗咨询用户是否前往其他应用
            .createAgentWeb()
            .ready()
            .get()
        mAgentWeb.agentWebSettings.webSettings.javaScriptEnabled = true

        //监听 WebView 返回值
        mAgentWeb.webCreator.webView
            .addJavascriptInterface(AndroidToJs(), "test")
        mAgentWeb.clearWebCache()

        //解析 html 文件
        mAgentWeb.urlLoader.loadData(url, "text/html; charset=UTF-8", null)
    }

    inner class AndroidToJs {
        // 定义JS需要调用的方法
        // 被JS调用的方法必须加入@JavascriptInterface注解
        @JavascriptInterface
        fun hello(urls: String, msg: String) {
            //弹出单个图片
//            showImage(msg)

            //弹出多个图片
            showImages(urls, msg)
        }
    }

    //多个页面
    private fun showImages(urls: String, msg: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.view_pager_layout, null, false)

        //获取 ViewPager 需要添加的 View
        val list = getViews(urls, msg)

        //初始化多图片 ViewPager
        initViewPager(view, list.first,list.second)

        //弹出 DialogFragment
        showDialog(view)
    }

    private fun initViewPager(view: View, list: MutableList<View>, second: Int) {
        val viewPager = view.findViewById<ViewPager>(R.id.viewPager)

        val adapter = MyPageAdapter(list)

        viewPager.adapter = adapter

        viewPager.currentItem = second
    }

    //获取多个图片显示的 View 布局
    //利用 Kotlin 多返回机制,完成图片'锁定'
    private fun getViews(urls: String, msg: String): Pair<MutableList<View>,Int> {
        val list = mutableListOf<View>()

        //截取urls 的字符串
        val url = getUrl(urls)
        for (index in 0..url.lastIndex) {
            val view =
                LayoutInflater.from(this).inflate(R.layout.item_view_pager_layout, null, false)
            val itemTv = view.findViewById<TextView>(R.id.item_tv)
            val itemImage = view.findViewById<ImageView>(R.id.item_image)

            setUrl(url[index], itemImage)
            itemTv.text = "${index + 1}/${url.size}"
            list.add(view)
        }

        //获取当前点击的图片在图片集合中的位置
        val indexOf = url.indexOf(msg)
        return Pair(list,indexOf)
    }

    //解析 Url
    private fun getUrl(urls: String): MutableList<String> {
        val list = mutableListOf<String>()
        if (urls.contains("----")) {
            urls.split("----".toRegex()).toTypedArray().forEach {
                list.add(it)
            }
        }
        return list
    }

    //单个照片弹出
    private fun showImage(msg: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_item, null, false)
        val image = view.findViewById<ImageView>(R.id.image)

        //设置图片(Glide 加载的)
        setUrl(msg, image)

        //用 Dialog 显示
        showDialog(view)
    }

    //显示 Dialog
    private fun showDialog(view: View) {
        val dialog = SwitchDialogFragment(view, "美女来啦~", this)

        dialog.show(supportFragmentManager, dialog.tag)

        //左侧右侧按钮隐藏
        dialog.setLeftVisibility(true)
        dialog.setRightVisibility(true)

        //显示动画
        dialog.setDialogAnimation(true)
    }

    //设置 ImageView 图片
    private fun setUrl(url: String, image: ImageView) {
        Log.i("setUrl1", url)
        val options: RequestOptions = RequestOptions()
            .placeholder(R.mipmap.ic_launcher) //图片加载出来前，显示的图片
            .fallback(R.mipmap.ic_launcher) //url为空的时候,显示的图片
            .error(R.mipmap.ic_launcher) //图片加载失败后，显示的图片

        runOnUiThread {
            Glide.with(this)
                .load(url)
                .apply(options)
                .into(image)
        }
    }

    /**
     * 参考我其他博文: https://blog.csdn.net/weixin_44819566/article/details/114404236
     */
    //普通菜单
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu,menu)
        return true
    }

    //点击事件监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_1-> {
                mAgentWeb.webCreator.webView.settings.textSize = WebSettings.TextSize.NORMAL
            }
            R.id.menu_2-> {
                mAgentWeb.webCreator.webView.settings.textSize = WebSettings.TextSize.LARGER
            }
            R.id.menu_3-> {
                mAgentWeb.webCreator.webView.settings.textSize = WebSettings.TextSize.LARGEST
            }
        }
        return super.onOptionsItemSelected(item)
    }


}