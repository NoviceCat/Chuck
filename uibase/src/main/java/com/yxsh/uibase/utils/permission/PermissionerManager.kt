package com.yxsh.uibase.utils.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.LogUtils
import com.doule.base_lib.utils.extension.safe
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yxsh.uibase.R
import com.yxsh.uibase.application.Core
import com.yxsh.uibase.dialog.CommonAlertDialog
import com.yxsh.uibase.dialog.ConfirmDialog
import com.yxsh.uibase.dialog.inner.DialogClickListener
import io.reactivex.functions.Consumer


/**
 * @author novice
 *
 */
object PermissionerManager {
    val CAMERA: Array<String> = arrayOf(android.Manifest.permission.CAMERA)
    val VIEDOPERSSIONS: Array<String> = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.CAMERA
    )
    val PHONEPERSSIONS: Array<String> = arrayOf(
        android.Manifest.permission.READ_PHONE_STATE,
    )
    val FILESPERSSIONS: Array<String> = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )
    val CAMERAFILE: Array<String> = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.READ_EXTERNAL_STORAGE

    )
    val AUDIO_FILESPERSSIONS: Array<String> = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.RECORD_AUDIO
    )

    val ALL_PERSSIONS: Array<String> = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.READ_EXTERNAL_STORAGE,
        android.Manifest.permission.RECORD_AUDIO,
        android.Manifest.permission.CAMERA
    )

    val LOCATION: Array<String> = arrayOf(
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    const val PERMISSION_CODE = 5000

    abstract class PerssionCallBack {
        open fun granted() {}
        open fun disGranted() {}
        open fun never() {
//            showSettingDialog(activity, tip ?: "")
        }
    }

    abstract class DialogCallBack {
        open fun leftClick() {}
        open fun rightClick() {}
    }

    fun realCheck(
        activity: FragmentActivity?,
        perssionNames: Array<String>,
        percall: PerssionCallBack?
    ) {
        val rxPermissions = activity?.let { RxPermissions(it) }
        rxPermissions!!.requestEachCombined(*perssionNames)
            .subscribe(Consumer {
                when {
                    it.granted -> {
                        // 用户已经同意该权限
                        percall!!.granted()
                    }
                    it.shouldShowRequestPermissionRationale -> {
                        // 用户拒绝了该权限，没有选中『不再询问』
                        percall!!.disGranted()
                    }
                    else -> {
                        // 用户拒绝了该权限，并且选中『不再询问』
                        percall!!.never()
                    }
                }
            })
    }

    /**
     * 权限申请
     * @param permissionNames 要申请的权限列表
     * @param permissionDescList 权限申请提示消息 不为空时会弹窗提示 为空不显示
     */
    fun rxPermissionCheck(
        activity: FragmentActivity?,
        permissionNames: Array<String>,
        permissionDescList: ArrayList<PermissionDescItem>? = null, callBack: PerssionCallBack?
    ): Boolean {
        var isOpen = false
        for (str in permissionNames) {
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat
                    .checkSelfPermission(Core.getContext(), str)
            ) {
                //判断是否以授权相机权限，没有则授权
                LogUtils.e("权限管理===》", "用户未获取==>${str}")
                isOpen = false
                realCheck(activity, permissionNames, callBack)
                break
            } else {
                LogUtils.e("权限管理===》", "用户已获取==>${str}")
                isOpen = true
            }
        }
        if (isOpen) {
            callBack!!.granted()
        }
        return isOpen
    }

    fun showSettingDialog(
        activity: FragmentActivity,
        strTitle: String,
        strTip: String,
        dialogClick: DialogClickListener.DefaultLisener
    ) {
        CommonAlertDialog(activity).apply {
            type = CommonAlertDialog.DialogType.Confirm
            title = strTitle
            content = strTip
            leftBtnText = "取消"
            rightBtnText = "确定"
            listener = dialogClick
        }.show()
    }

    fun goToAppSetting(activity: FragmentActivity) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", Core.getContext().packageName, null)
        intent.data = uri
        activity.startActivityForResult(intent, PERMISSION_CODE)
    }
}