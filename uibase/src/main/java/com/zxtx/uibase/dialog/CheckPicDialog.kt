package com.zxtx.uibase.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.ToastUtils
import com.luozm.captcha.Captcha
import com.luozm.captcha.Captcha.CaptchaListener
import com.zxtx.uibase.R
import razerdp.basepopup.BasePopupWindow

/**
 * @Author : novice
 * @Date : 2022/1/4
 * @Desc : 图形滑块校验弹窗
 */
class CheckPicDialog(context: Context, var listener: CheckCallBack) : BasePopupWindow(context) {

    private var captCha: Captcha? = null

    init {
        captCha = findViewById(R.id.captCha)
        captCha?.setBitmap(R.drawable.bg_check_pic)
        captCha?.setCaptchaListener(object : CaptchaListener {
            override fun onAccess(time: Long): String {
                listener.onCheckSuccess()
                ToastUtils.showShort("验证成功")
                dismiss()
                return "验证通过"
            }

            override fun onFailed(count: Int): String {
                listener.onCheckFail()
                ToastUtils.showShort("验证失败")
                return "验证失败"
            }

            override fun onMaxFailed(): String {
                listener.onCheckFail()
                ToastUtils.showShort("验证超过次数，请重试")
                dismiss()
                return "可以走了"
            }
        })
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.dialog_check_pic)
    }

    override fun getPopupGravity(): Int {
        return Gravity.CENTER
    }

    interface CheckCallBack {
        fun onCheckSuccess()
        fun onCheckFail()

    }
}