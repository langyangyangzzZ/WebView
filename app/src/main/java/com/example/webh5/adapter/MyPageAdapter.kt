package com.example.webh5.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 *
 * @Package: com.example.webh5.adapter
 * @ClassName: MyPageAdapter
 * @Author: szj
 * @CreateDate: 6/1/21 11:28 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
/**
 *
 * @Package: com.example.webh5.adapter
 * @ClassName: MyPageAdapter
 * @Author: szj
 * @CreateDate: 6/1/21 11:28 AM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 * 参考文档:https://blog.csdn.net/wuyinlei/article/details/50231535
 */
class MyPageAdapter(var list: List<View>) : PagerAdapter() {

    /**
     * Return the number of views available.
     * 返回一个可以用的view的个数
     * @return
     */
    override fun getCount(): Int = list.size

    /**
     * 确定是否一个页面视图中所返回的instantiateItem（ViewGroup中，INT）
     * 与特定键对象相关联。此方法需要一个PagerAdapter才能正常工作。
     * @param view
     * @param object
     * @return
     */
    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    /**
     * 移除给定位置的数据，适配器负责从container（容器）中取出，但是这个必须保证是在finishUpdate（view）
     * 返回的时间内完成
     * @param container
     * @param position
     * @param object
     */
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(list.get(position))
    }

    /**
     * 这个同destroyItem（）相反，是对于给定的位置创建视图，适配器往container中添加
     * @param container
     * @param position
     * @return
     */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(list.get(position))
        return list.get(position)
    }
}