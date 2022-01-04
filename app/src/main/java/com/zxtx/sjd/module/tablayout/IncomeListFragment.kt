package com.zxtx.sjd.module.tablayout

import androidx.lifecycle.ViewModelProvider
import com.zxtx.uibase.net.CommonResponse
import com.zxtx.uibase.uicore.ui.SimpleListFragment
import com.zxtx.uibase.uicore.viewmodel.SimpleListViewModel
import com.zxtx.sjd.module.list.TestRepository
import com.zxtx.sjd.module.tablayout.adapter.IncomeListAdapter

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