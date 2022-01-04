package com.zxtx.uibase.utils

import android.Manifest.permission
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.DeviceUtils
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.PhoneUtils
import com.blankj.utilcode.util.SPUtils
import com.zxtx.uibase.application.Core.Companion.getContext
import java.util.*

/**
 *  Project Name : Yubo
 *  Package Name : com.yxsh.libservice.utils
 *  @since 2020/7/29 10: 35
 *  @author : RuisZeng
 *  @Email : ruiszeng@yeah.net
 *  @Version :
 */
object DeviceIdUtil {

    fun getDeviceId(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return getUniqueId()
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                val deviceId = PhoneUtils.getDeviceId()
                if (!deviceId.isNullOrEmpty()) {
                    return deviceId
                }
                return getUniqueId()
            } else {
                return getUniqueId()
            }
        }
    }

    private fun getUniqueId(): String {
        // 不选用需要权限的获取 ID 方式
        val data = DeviceUtils.getAndroidID() + getSerialNumber() + getUniquePsuedoId() + getUuid()
        return EncryptUtils.encryptMD5ToString(data).toUpperCase(Locale.CHINA)
    }

    /**
     * 获取序列号
     * @return 序列号
     */
    private fun getSerialNumber(): String = Build.SERIAL

    /**
     * 伪 IMEI
     * @return 伪 IMEI
     */
    private fun getUniquePsuedoId(): String? =
        "35" +
                Build.BOARD.length % 10 +
                Build.BRAND.length % 10 +
                Build.CPU_ABI.length % 10 +
                Build.DEVICE.length % 10 +
                Build.DISPLAY.length % 10 +
                Build.HOST.length % 10 +
                Build.ID.length % 10 +
                Build.MANUFACTURER.length % 10 +
                Build.MODEL.length % 10 +
                Build.PRODUCT.length % 10 +
                Build.TAGS.length % 10 +
                Build.TYPE.length % 10 +
                Build.USER.length % 10 //13 digits

    /**
     * 获取 UUID
     * @param context 上下文
     */
    private fun getUuid(): String {
        // UUID 键
        val key = "key_uuid"
        // 获取 UUID
        var uuid: String? = SPUtils.getInstance().getString(key, "")
        // UUID 为空值
        if (uuid.isNullOrEmpty()) {
            // 创建新的 UUID
            uuid = UUID.randomUUID().toString()
            // 保存
            SPUtils.getInstance().put(key, uuid)
        }
        return uuid
    }

}