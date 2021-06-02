package com.example.webh5.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.webh5.R;
/**
 *
 ==============================================================================
 =========================== DialogFragment 生命周期  ===========================
 ==============================================================================

 1.onAttach(Activity)： 当Activity与Fragment发生关联时调用（已废弃，但是还是调用了）

 2.onAttach(Context)：当Activity与Fragment发生关联时调用

 3.onCreate(Bundle)：初始化Fragment。可通过参数savedInstanceState获取之前保存的值

 4.onCreateDialog(Bundle)：重写以生成自己的对话框，通常用于显示AlertDialog，而不是常规对话框；执行此操作时，不需要实现OnCreateView），因为AlertDialog会处理自己的内容。

 5.onCreateView( LayoutInflater, ViewGroup, Bundle)：让Fragment实例化View。

 6.onViewCreated(View, Bundle)：紧随onCreateView调用，表示view已初始化完成。

 7.onActivityCreated(Bundle)：执行该方法时，与Fragment绑定的Activity的onCreate方法已经执行完成并返回，在该方法内可以进行与Activity交互的UI操作，所以在该方法之前Activity的onCreate方法并未执行完成，如果提前进行交互操作，会引发空指针异常。

 8.onStart()：执行该方法时，Fragment由不可见变为可见状态。

 9.onResume()：执行该方法时，Fragment处于活动状态，用户可与之交互。

 10.onCancel(DialogInterface)：取消对话框时将调用此方法。onCancel会调用onDismiss

 11.onDismiss(DialogInterface)：关闭对话框时将调用此方法。

 12.onPause()：执行该方法时，Fragment处于暂停状态，但依然可见，用户不能与之交互。

 13.onStop()：执行该方法时，Fragment完全不可见。

 14.onDestroyView()：销毁与Fragment有关的视图，但未与Activity解除绑定，依然可以通过onCreateView方法重新创建视图。通常在ViewPager+Fragment的方式下会调用此方法。

 15.onDestroy()：销毁Fragment。通常按Back键退出或者Fragment被回收时调用此方法。

 16.onDetach()：解除与Activity的绑定。在onDestroy方法之后调用。
 */

/**
 * @ProjectName: My Application
 * @Package: com.example.myapplication.fragmentDialog
 * @ClassName: SwitchDialogFragment
 * @Author: szj
 * @CreateDate: 5/11/21 2:40 PM
 * @UpdateDate: 5/11/21 2:40 PM
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SwitchDialogFragment extends DialogFragment implements View.OnClickListener {
    private final String title;
    private final Context mContext;
    private String rightName = "关闭", leftName = "确定";

    //RootView 宽和高 默认自适应
    private int rootViewWeight = ViewGroup.MarginLayoutParams.WRAP_CONTENT;
    private int rootViewHeight = ViewGroup.MarginLayoutParams.WRAP_CONTENT;

    //左侧/右侧按钮是否显示 默认显示 (true隐藏)
    private boolean leftVisibility, rightVisibility;

    //Dialog 是否点击外部消失(true 消失)
    private boolean isDisappear;

    //是否底部弹出 (true 底部弹出)
    private boolean isBottomShow;

    //是否显示动画 (true 显示动画)
    private boolean isAnimation;

    //公用布局(构造器传入)
    private final View view;

    /**
     * @param view    中间公用布局
     * @param title   标题
     * @param context 上下文
     */
    public SwitchDialogFragment(View view, String title, Context context) {
        this.view = view;
        this.title = title;
        this.mContext = context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        //Dialog 布局
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_layout, null, false);
        //设置Dialog 布局宽高
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        //初始化公用布局
        initRootView(view);

        //设置自定义布局
        builder.setView(view);

        //初始化顶部标题
        initTitle(view);

        //初始化底部按钮操作
        initBtButton(view);

        AlertDialog alertDialog = builder.create();

        //点击外部是否消失
        setDialogCancelable(alertDialog, !isDisappear);

        //Dialog 是否底部显示
        setDialogBottom(alertDialog, isBottomShow);

        //设置 Dialog 动画
        setDialogAnimation(alertDialog, isAnimation);

        return alertDialog;
    }

    /**
     * 设置 Dialog Animation 动画
     *
     * @param alertDialog AlertDialog 对象
     * @param isAnimation 是否显示动画 true 显示动画
     */
    private void setDialogAnimation(AlertDialog alertDialog, boolean isAnimation) {
        if (isAnimation) {
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.BottomToTopAnim;
        }
    }

    /**
     * Dialog 底部弹出
     *
     * @param alertDialog  dialog 对象
     * @param isBottomShow true 底部弹出(默认 false)
     */
    private void setDialogBottom(AlertDialog alertDialog, boolean isBottomShow) {
        if (isBottomShow) {
            Window dialogWindow = alertDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialogWindow.setGravity(Gravity.BOTTOM);
        }
    }

    /**
     * 点击外部消失
     *
     * @param alertDialog alertDialog对象
     * @param isDisappear true 消失
     */
    private void setDialogCancelable(AlertDialog alertDialog, boolean isDisappear) {
        alertDialog.setCancelable(isDisappear);
        alertDialog.setCanceledOnTouchOutside(isDisappear);

        //回退键不消失
        alertDialog.setOnKeyListener((dialog, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK);
    }

    //顶部 View
    private void initTitle(View view) {
        TextView tv_data = view.findViewById(R.id.tv_data);
        tv_data.setText(title);
    }

    private void initRootView(View view1) {
        //Dialog 公共布局
        RelativeLayout root = view1.findViewById(R.id.rootView);
        root.setLayoutParams(new LinearLayout.LayoutParams(rootViewWeight, rootViewHeight));
        //添加 公共布局 到 Dialog
        root.addView(this.view);
    }

    //配置 RootView 宽高
    public void setRootViewLayoutParams(int weight, int height) {
        rootViewWeight = weight;
        rootViewHeight = height;
    }

    /**
     * 左侧按钮名字
     */
    public void setLeftName(String name) {
        leftName = name;
    }

    /**
     * 左侧按钮是否显示
     *
     * @param isVisibility true 隐藏
     */
    public void setLeftVisibility(boolean isVisibility) {
        leftVisibility = isVisibility;
    }

    /**
     * 左侧按钮名字
     */
    public void setRightName(String name) {
        rightName = name;
    }

    /**
     * 有侧按钮是否显示
     *
     * @param isVisibility true 隐藏
     */
    public void setRightVisibility(boolean isVisibility) {
        rightVisibility = isVisibility;
    }

    /**
     * dialog 点击外部是否消失
     *
     * @param isDisappear true 消失
     */
    public void setDisappear(boolean isDisappear) {
        this.isDisappear = isDisappear;
    }

    /**
     * Dialog 是否底部显示
     *
     * @param isShow true底部显示
     */
    public void setBottomShow(boolean isShow) {
        isBottomShow = isShow;
    }

    public void setDialogAnimation(boolean isAnimationShow) {
        isAnimation = isAnimationShow;
    }

    //底部按钮配置
    private void initBtButton(View view) {
        Button bt_left = view.findViewById(R.id.bt_left);
        Button bt_right = view.findViewById(R.id.bt_right);

        bt_left.setText(leftName);
        bt_right.setText(rightName);

        bt_left.setVisibility(!leftVisibility ? View.VISIBLE : View.GONE);
        bt_right.setVisibility(!rightVisibility ? View.VISIBLE : View.GONE);

        bt_left.setOnClickListener(this);
        bt_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_left) {
            if (monDialogClick != null) {
                monDialogClick.onClone();
            }
            dismiss();
        } else if (id == R.id.bt_right) {
            if (monDialogClick != null) {
                monDialogClick.onSuccess();
            }
            dismiss();
        }
    }

    public interface onDialogClick {
        void onSuccess();

        void onClone();
    }

    public onDialogClick monDialogClick;

    public void setOnDialogClick(onDialogClick monDialogClick) {
        this.monDialogClick = monDialogClick;
    }
}