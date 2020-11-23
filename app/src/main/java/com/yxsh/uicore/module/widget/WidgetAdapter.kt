package com.yxsh.uicore.module.widget

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yxsh.uicore.R

class WidgetAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_widget_list) {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_name, item)
    }
}