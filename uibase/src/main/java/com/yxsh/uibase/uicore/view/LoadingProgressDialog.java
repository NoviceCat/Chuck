package com.yxsh.uibase.uicore.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.yxsh.uibase.R;
import com.yxsh.uibase.uicore.utils.UICoreConfig;

/**
 * 加载中dialog
 * author novice
 * date 2019/11/6
 */
public class LoadingProgressDialog extends ProgressDialog {

    private boolean onTouchOutsideCanceled, backCanceled;

    public LoadingProgressDialog(Context context) {
        this(context, R.style.CustomDialog);
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    private void init() {
        setCancel();
        setContentView(R.layout.dialog_common_progress);
        LottieAnimationView lottieProgress = findViewById(R.id.lottie_progress);
        lottieProgress.setAnimation(UICoreConfig.INSTANCE.getProgressLottie());
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    private void setCancel() {
        setCancelable(backCanceled);
        setCanceledOnTouchOutside(onTouchOutsideCanceled);
    }

    /**
     * @param onTouchOutside 点击空白区域
     * @param back           返回键
     */
    public void setCanceled(boolean onTouchOutside, boolean back) {
        this.onTouchOutsideCanceled = onTouchOutside;
        this.backCanceled = back;
    }

    public void show() {
        setCancel();
        super.show();
    }

}
