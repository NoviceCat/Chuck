package com.yxsh.uibase.dialog.inner

import android.view.View

/**
 * author novic
 * date 2019/11/7
 */
interface DialogClickListener {

    fun onCloseBtnClick(view: View)

    fun onLeftBtnClick(view: View)

    fun onRightBtnClick(view: View)

    fun onConfirmBtnClick(view: View)

    fun onRightEditBtnClick(view: View, content: String?)

    open class DefaultLisener : DialogClickListener {

        override fun onCloseBtnClick(view: View) {

        }

        override fun onLeftBtnClick(view: View) {

        }

        override fun onRightBtnClick(view: View) {

        }

        override fun onConfirmBtnClick(view: View) {

        }

        override fun onRightEditBtnClick(view: View, content: String?) {
        }

    }

}