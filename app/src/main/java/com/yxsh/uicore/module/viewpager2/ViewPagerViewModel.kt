package com.yxsh.uicore.module.viewpager2

import androidx.lifecycle.MutableLiveData
import com.yxsh.uibase.uicore.viewmodel.BaseViewModel
import kotlin.random.Random

/**
 * @author novice
 */
class ViewPagerViewModel : BaseViewModel() {

    val updateItemEvent = MutableLiveData<Int>()

    fun updateItem(){
        updateItemEvent.value = Random(999).nextInt()
    }

}