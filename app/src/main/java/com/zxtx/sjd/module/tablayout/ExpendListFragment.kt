package com.zxtx.sjd.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.net.CommonResponse
import com.zxtx.uibase.uicore.ui.SimpleListFragment
import com.zxtx.uibase.uicore.viewmodel.SimpleListViewModel
import com.zxtx.sjd.module.list.TestRepository
import com.zxtx.sjd.module.tablayout.adapter.ExpendListAdapter

/**
 * @author novice
 */
class ExpendListFragment : SimpleListFragment<String, ExpendListAdapter, ExpendListFragment.ExpendListViewModel>() {

    override fun enabledImmersion(): Boolean {
        return false
    }

    override fun initAdapter(): ExpendListAdapter {
        return ExpendListAdapter()
    }

    override fun initViewModel(): ExpendListViewModel {
        return ViewModelProvider(this).get(ExpendListViewModel::class.java)
    }

    class ExpendListViewModel : SimpleListViewModel<String>() {

        override suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}