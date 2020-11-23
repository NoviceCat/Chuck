package com.yxsh.uicore.module.tablayout.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yxsh.uibase.glide.GlideUtils
import com.yxsh.uicore.R

/**
 * @author novic
 * @date 2020/6/29
 */
class ExpendListAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_expend_list), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: String) {
        GlideUtils.loadCicle(context, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2792996918,3369613861&fm=26&gp=0.jpg", holder.getView(R.id.img_avatar))
        holder.setText(R.id.tv_content, item)
    }
}