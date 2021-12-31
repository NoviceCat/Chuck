package com.yxsh.uicore.module.tablayout.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yxsh.uicore.R

/**
 * @author novice
 * @date 2020/6/29
 */
class IncomeListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_income_list), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_text, item)
    }
}