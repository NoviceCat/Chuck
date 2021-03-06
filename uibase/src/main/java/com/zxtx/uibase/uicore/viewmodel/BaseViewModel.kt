package com.zxtx.uibase.uicore.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.*
import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonSyntaxException
import com.zxtx.uibase.net.CommonResponse
import com.zxtx.uibase.net.HttpNetCode
import com.zxtx.uibase.net.process
import com.zxtx.uibase.uicore.inner.IBaseViewModel
import com.zxtx.uibase.uicore.inner.INetView
import com.zxtx.uibase.uicore.utils.UICoreConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.EOFException
import java.net.SocketTimeoutException
import java.text.ParseException
import javax.net.ssl.SSLException

/**
 * @author novice
 *
 */
abstract class BaseViewModel : ViewModel(), IBaseViewModel, INetView {

    val showProgressDialogEvent = MutableLiveData<String>()
    val hideProgressDialogEvent = MutableLiveData<String>()
    val showEmptyLayoutEvent = MutableLiveData<String>()
    val showLoadingLayoutEvent = MutableLiveData<String>()
    val showLoadErrorLayoutEvent = MutableLiveData<String>()
    val showNetDisconnectLayoutEvent = MutableLiveData<String>()
    val hideStatusLayoutEvent = MutableLiveData<String>()
    val showToastStrEvent = MutableLiveData<String?>()
    val showLongToastStrEvent = MutableLiveData<String?>()
    val showToastResEvent = MutableLiveData<Int>()
    val showLongToastResEvent = MutableLiveData<Int>()
    val showCenterToastStrEvent = MutableLiveData<String?>()
    val showCenterLongToastStrEvent = MutableLiveData<String?>()
    val showCenterToastResEvent = MutableLiveData<Int>()
    val showCenterLongToastResEvent = MutableLiveData<Int>()
    val finishAcEvent = MutableLiveData<String>()
    var isViewDestroyed = false

    override fun onAny(owner: LifecycleOwner, event: Lifecycle.Event) {
    }

    override fun onCreate() {
    }

    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onPause() {
    }

    override fun onStop() {
    }

    override fun onDestroy() {
        isViewDestroyed = true
    }

    override fun showProgressDialog() {
        showProgressDialogEvent.value = ""
    }

    override fun hideProgressDialog() {
        hideProgressDialogEvent.value = ""
    }

    override fun showEmptyLayout() {
        showEmptyLayoutEvent.value = ""
    }

    override fun showLoadingLayout() {
        showLoadingLayoutEvent.value = ""
    }

    override fun showLoadErrorLayout() {
        showLoadErrorLayoutEvent.value = ""
    }

    override fun showNetDisconnectLayout() {
        showNetDisconnectLayoutEvent.value = ""
    }

    override fun hideStatusLayout() {
        hideStatusLayoutEvent.value = ""
    }

    override fun showToast(msg: String?) {
        showToastStrEvent.value = msg
    }

    override fun showLongToast(msg: String?) {
        showLongToastStrEvent.value = msg
    }

    override fun showToast(@StringRes resId: Int) {
        showToastResEvent.value = resId
    }

    override fun showLongToast(resId: Int) {
        showLongToastResEvent.value = resId
    }

    override fun showCenterToast(msg: String?) {
        showCenterToastStrEvent.value = msg
    }

    override fun showCenterLongToast(msg: String?) {
        showCenterLongToastStrEvent.value = msg
    }

    override fun showCenterToast(resId: Int) {
        showCenterToastResEvent.value = resId
    }

    override fun showCenterLongToast(resId: Int) {
        showCenterLongToastResEvent.value = resId
    }

    override fun finishAc() {
        finishAcEvent.value = ""
    }

    /**
     * ???????????????
     */
    override fun launch(block: suspend () -> Unit, failed: suspend (Int, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                block()
            } catch (t: Throwable) {
                onFailSuspend(t, failed)
            }
        }
    }

    /**
     * ???????????????
     */
    override fun <T> netLaunch(block: suspend () -> CommonResponse<T>, success: (msg: String?, d: T?) -> Unit, failed: (Int, String?, d: T?) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response = block()
                response.process(success, failed)
            } catch (t: Throwable) {
                onFailException(t, failed)
            }
        }
    }

    /**
     * ???????????????
     */
    override fun ioLaunch(block: suspend () -> Unit, failed: suspend (Int, String?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                block()
            } catch (t: Throwable) {
                onFailSuspend(t, failed)
            }
        }
    }

    /**
     * ???????????????
     */
    override fun <T> ioNetLaunch(block: suspend () -> CommonResponse<T>, success: (msg: String?, d: T?) -> Unit, failed: (Int, String?, d: T?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = block()
                response.process(success, failed)
            } catch (t: Throwable) {
                onFailException(t, failed)
            }
        }
    }

    private suspend fun onFailSuspend(t: Throwable, failed: suspend (Int, String?) -> Unit) {
        val loginExpired = t.message?.contains("HTTP 401") ?: false
        if (!loginExpired) {
            LogUtils.e(t)
            when (t) {
                is EOFException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "?????????????????????${t.message}")
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "??????????????????")
                    }
                }
                is SocketTimeoutException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_TIMEOUT, "???????????????${t.message}")
                    } else {
                        failed(HttpNetCode.NET_TIMEOUT, "????????????")
                    }
                }
                is SSLException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "SSL??????????????????${t.message}")
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "SSL???????????????")
                    }
                }
                is ParseException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.PARSE_ERROR, "Parse???????????????${t.message}")
                    } else {
                        failed(HttpNetCode.PARSE_ERROR, "Parse????????????")
                    }
                }
                is JsonSyntaxException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.JSON_ERROR, "Json???????????????${t.message}")
                    } else {
                        failed(HttpNetCode.JSON_ERROR, "Json????????????")
                    }
                }
                else -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "???????????????${t.message}")
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "????????????")
                    }
                }
            }
        }
    }

    private fun <T> onFailException(t: Throwable, failed: (Int, String?, d: T?) -> Unit) {
        val loginExpired = t.message?.contains("HTTP 401") ?: false
        if (!loginExpired) {
            LogUtils.e(t)
            when (t) {
                is EOFException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "?????????????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "??????????????????", null)
                    }
                }
                is SocketTimeoutException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_TIMEOUT, "???????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.NET_TIMEOUT, "????????????", null)
                    }
                }
                is SSLException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "SSL??????????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "SSL???????????????", null)
                    }
                }
                is ParseException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.PARSE_ERROR, "Parse???????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.PARSE_ERROR, "Parse????????????", null)
                    }
                }
                is JsonSyntaxException -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.JSON_ERROR, "Json???????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.JSON_ERROR, "Json????????????", null)
                    }
                }
                else -> {
                    if (UICoreConfig.mode) {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "???????????????${t.message}", null)
                    } else {
                        failed(HttpNetCode.NET_CONNECT_ERROR, "????????????", null)
                    }
                }
            }
        }
    }

}

