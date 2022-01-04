package com.zxtx.uibase.uicore.ui

import android.view.View
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zxtx.uibase.uicore.inner.SimpleListContract
import com.zxtx.uibase.uicore.statuslayout.StatusLayoutType
import com.zxtx.uibase.uicore.viewmodel.SimpleListViewModel

/**
 * 简单列表
 * @author novice
 *
 */
abstract class SimpleListFragment<T, A : BaseQuickAdapter<T, *>, VM : SimpleListViewModel<T>> : BaseListFragment<T, A, VM>(),
    SimpleListContract.SimpleListView<T> {

    override fun statusLayoutRetry(view: View, status: StatusLayoutType) {
        hideStatusLayout()
        autoRefresh()
    }

    override fun registorUIChangeLiveDataCallBack() {
        viewModel.loadCompleteEvent.observe(viewLifecycleOwner, Observer {
            loadComplete()
        })
        viewModel.showErrorEvent.observe(viewLifecycleOwner, Observer {
            showError()
        })
        viewModel.showEmptyEvent.observe(viewLifecycleOwner, Observer {
            showEmpty()
        })
        viewModel.showNoMoreEvent.observe(viewLifecycleOwner, Observer {
            showNoMore()
        })
        viewModel.pushDataEvent.observe(viewLifecycleOwner, Observer { t ->
            pushData(t)
        })
    }

    override fun initLazyData() {
        autoRefresh()
    }

    override fun doHttpRequest(isRefresh: Boolean) {
        viewModel.getData(isRefresh)
    }

    override fun pushData(list: MutableList<T>) {
        setAdapterData(list)
    }

    override fun showEmpty() {
        if (isRefresh()) {
            setNewData(null)
            showEmptyLayout()
        } else {
            showNoMore()
        }
    }

    override fun showError() {
        if (isRefresh()) {
            if (adapter.data.size == 0) {
                setNewData(null)
                showLoadErrorLayout()
            } else {
                showNoMore()
            }
        } else {
            loadMoreFail()
        }
    }

    override fun showNoMore() {
        loadComplete()
        loadMoreEnd()
    }

}