package com.yxsh.uicore.module.list

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yxsh.uicore.R

class ListAdapter :BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_list),LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_text, item)
    }
}