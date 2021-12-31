package com.yxsh.uicore.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.yxsh.uibase.net.CommonResponse
import com.yxsh.uibase.uicore.ui.SimpleListFragment
import com.yxsh.uibase.uicore.viewmodel.SimpleListViewModel
import com.yxsh.uicore.module.list.TestRepository
import com.yxsh.uicore.module.tablayout.adapter.IncomeListAdapter

/**
 * @author novice
 */
class IncomeListFragment : SimpleListFragment<String, IncomeListAdapter, IncomeListFragment.IncomeListViewModel>() {

    override fun enabledImmersion(): Boolean {
        return false
    }

    override fun initAdapter(): IncomeListAdapter {
        return IncomeListAdapter()
    }

    override fun initViewModel(): IncomeListViewModel {
        return ViewModelProvider(this).get(IncomeListViewModel::class.java)
    }

    class IncomeListViewModel : SimpleListViewModel<String>() {

        override suspend fun requestData(offset: Int, length: Int): CommonResponse<MutableList<String>> {
            return TestRepository.getTestList(offset, length)
        }
    }

}