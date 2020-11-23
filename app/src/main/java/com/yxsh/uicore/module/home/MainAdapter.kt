package com.yxsh.uicore.module.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yxsh.uicore.R

class MainAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_main_list) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.btn_name, item)
    }
}