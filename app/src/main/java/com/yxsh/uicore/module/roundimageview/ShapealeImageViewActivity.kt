package com.yxsh.uicore.module.roundimageview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.R
import com.yxsh.uicore.util.DefaultViewModel

class ShapealeImageViewActivity : BaseActivity<DefaultViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ShapealeImageViewActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_shapeable_image_view
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        setToolBarTitle("ShapeableImageView")
        //        val radius = 50.0f
//        imageView.shapeAppearanceModel = imageView.shapeAppearanceModel
//            .toBuilder()
//            .setTopRightCorner(CornerFamily.ROUNDED, radius)
//            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
//            .build()
    }

}
