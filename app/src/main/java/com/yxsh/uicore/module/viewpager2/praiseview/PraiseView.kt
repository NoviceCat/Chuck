package com.yxsh.uicore.module.viewpager2.praiseview

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.blankj.utilcode.util.ClickUtils
import com.yxsh.uicore.R
import kotlinx.android.synthetic.main.view_praise.view.*

/**
 * @author novic
 * @date 2020/8/6
 */
class PraiseView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        FrameLayout(context, attrs, defStyleAttr) {

    private var limitTime = 300L
    private var curCount = 0
    var clickCallback: PraiseCallback?=null

    init {
        LayoutInflater.from(context).inflate(R.layout.view_praise, this, true)
        setOnClickListener(object :ClickUtils.OnMultiClickListener(100, limitTime){
            override fun onTriggerClick(v: View) {
            }

            override fun onBeforeTriggerClick(v: View, count: Int) {
                clickCallback?.clickAction()
                curCount = count
                img_rose.visibility = View.GONE
                cicle_progress_bar.visibility = View.VISIBLE
                cicle_progress_bar.updateCount(curCount)
                postDelayed({
                    if (curCount == count) {
                        if (curCount == 1) {
                            cicle_progress_bar.visibility = View.GONE
                            img_rose.visibility = View.VISIBLE
                            clickCallback?.clickComplete(1)
                        } else {
                            isEnabled = false
                            isClickable = false
                            cicle_progress_bar.setProgress(360, 1500, object : Animator.AnimatorListener {
                                override fun onAnimationRepeat(animation: Animator?) {
                                }

                                override fun onAnimationEnd(animation: Animator?) {
                                    cicle_progress_bar.visibility = View.GONE
                                    img_rose.visibility = View.VISIBLE
                                    clickCallback?.clickComplete(curCount)
                                    isEnabled = true
                                    isClickable = true
                                }

                                override fun onAnimationCancel(animation: Animator?) {
                                }

                                override fun onAnimationStart(animation: Animator?) {
                                }

                            })
                        }
                    }
                }, limitTime)
            }

        })
    }

}