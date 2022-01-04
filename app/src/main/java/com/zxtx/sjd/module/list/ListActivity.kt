package com.zxtx.sjd.module.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.net.CommonResponse
import com.zxtx.uibase.uicore.ui.SimpleListActivity
import com.zxtx.uibase.uicore.viewmodel.SimpleListViewModel

class ListActivity : SimpleListActivity<String, ListAdapter, ListActivity.ListViewModel>() {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, ListActivity::class.java))
        }
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        setToolBarTitle("简易列表")
    }

    override fun initAdapter(): ListAdapter {
        return ListAdapter()
    }

    override fun initViewModel(): ListViewModel {
        return ViewModelProvider(this).get(ListViewModel::class.java)
    }

    class ListViewModel : SimpleListViewModel<String>() {
        override suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}
