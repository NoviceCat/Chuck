package com.yxsh.uicore.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.net.CommonResponse
import com.yxsh.uibase.uicore.ui.SimpleListFragment
import com.yxsh.uibase.uicore.viewmodel.SimpleListViewModel
import com.yxsh.uicore.module.list.TestRepository
import com.yxsh.uicore.module.tablayout.adapter.ExpendListAdapter

/**
 * @author novice
 * @date 2020/6/29
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