package com.yxsh.uibase.uicore.inner

/**
 * @author novic
 * @date 2020/1/18
 */
interface SimpleListContract {

    interface SimpleListView<T> {
        fun pushData(list: MutableList<T>)
        fun showEmpty()
        fun showError()
        fun showNoMore()
    }

    interface ISimpleListViewModel {
        fun getData(isRefresh: Boolean)
    }

}