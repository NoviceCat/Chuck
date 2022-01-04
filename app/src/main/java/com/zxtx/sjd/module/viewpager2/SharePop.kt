package com.zxtx.sjd.module.viewpager2

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import com.zxtx.sjd.R
import razerdp.basepopup.BasePopupWindow
import razerdp.util.animation.AnimationHelper
import razerdp.util.animation.TranslationConfig

/**
 * @author novice
 */
class SharePop(context: Context) : BasePopupWindow(context) {

    init {
        popupGravity = Gravity.BOTTOM
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.layout_menu2)
    }

    override fun onCreateShowAnimation(): Animation? {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.FROM_BOTTOM).toShow()
    }

    override fun onCreateDismissAnimation(): Animation? {
        return AnimationHelper.asAnimation().withTranslation(TranslationConfig.TO_BOTTOM).toShow()
    }


}