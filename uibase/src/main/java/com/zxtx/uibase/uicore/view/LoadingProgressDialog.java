package com.zxtx.uibase.uicore.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;
import com.zxtx.uibase.R;
import com.zxtx.uibase.uicore.utils.UICoreConfig;

/**
 * 加载中dialog
 * author novice
 * 
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
