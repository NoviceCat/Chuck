package com.yxsh.uicore.module.zxing

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import com.yxsh.uibase.uicore.ui.BaseActivity
import com.yxsh.uicore.util.DefaultViewModel
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.yxsh.uicore.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ThreadUtils.runOnUiThread
import com.blankj.utilcode.util.ToastUtils
import com.doule.base_lib.utils.extension.safe
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer
import com.google.zxing.qrcode.QRCodeReader
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.tools.MediaUtils
import com.yxsh.uibase.dialog.inner.DialogClickListener
import com.yxsh.uibase.uicore.view.ToolBarView
import com.yxsh.uibase.utils.permission.PermissionDescItem
import com.yxsh.uibase.utils.permission.PermissionerManager
import com.yxsh.uicore.inner.Constant
import kotlinx.android.synthetic.main.activity_scan_layout.*
import java.util.*
import kotlin.collections.ArrayList
import com.google.zxing.Result
import com.yxsh.uibase.application.Core

/**
 * @Author : novice
 * @Date : 2021/12/31
 * @Desc : scan QRCode
 */
class ScanActivity : BaseActivity<DefaultViewModel>(), QRCodeView.Delegate {

    companion object {
        const val SELECT_PIC = 111

        fun start(context: Context) {
            context.startActivity(Intent(context, ScanActivity::class.java))
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_scan_layout
    }

    override fun initViewModel(): DefaultViewModel {
        return ViewModelProvider(this).get(DefaultViewModel::class.java)
    }

    override fun onClickToolBarView(view: View, event: ToolBarView.ViewType) {
        super.onClickToolBarView(view, event)
        if (ToolBarView.ViewType.RIGHT_TEXT == event) {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage()) // 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .imageEngine(GlideEngine.createGlideEngine()) // 外部传入图片加载引擎，必传项
                .maxSelectNum(1) // 最大图片选择数量
                .minSelectNum(1) // 最小选择数量
                .imageSpanCount(4) // 每行显示个数
                .isReturnEmpty(false) // 未选择数据时点击按钮是否可以返回
                .isAndroidQTransform(true) // 是否需要处理Android Q 拷贝至应用沙盒的操作，只针对compress(false); && .isEnableCrop(false);有效,默认处理
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) // 设置相册Activity方向，不设置默认使用系统
                .selectionMode(PictureConfig.SINGLE) // 多选 or 单选
                .isSingleDirectReturn(true) // 单选模式下是否直接返回，PictureConfig.SINGLE模式下有效
                .isPreviewImage(false) // 是否可预览图片
                .isCamera(false) // 是否显示拍照按钮
                .isEnableCrop(false) // 是否裁剪
                .minimumCompressSize(100) // 小于多少kb的图片不压缩
                .forResult(SELECT_PIC)
        }
    }

    override fun initView(rootView: View, savedInstanceState: Bundle?) {
        super.initView(rootView, savedInstanceState)
        setToolBarTitle("二维码")
        setToolBarRightText("相册")
        zxingview.setDelegate(this)
    }

    override fun onResume() {
        super.onResume()
        zxingview.startSpot()
    }

    override fun onStart() {
        super.onStart()
        val list = ArrayList<PermissionDescItem>()
        list.add(PermissionDescItem("相机权限", "扫描二维码"))
        PermissionerManager.rxPermissionCheck(this,
            PermissionerManager.CAMERA, list,
            object : PermissionerManager.PerssionCallBack() {
                override fun granted() {
                    super.granted()
                    zxingview.startCamera()
                    zxingview.showScanRect()
                }

                override fun disGranted() {
                    super.disGranted()
                    PermissionerManager.showSettingDialog(this@ScanActivity,
                        "相机权限",
                        "前往设置打开相机权限",
                        object : DialogClickListener.DefaultLisener() {
                            override fun onLeftBtnClick(view: View) {
                                super.onLeftBtnClick(view)
                            }

                            override fun onRightBtnClick(view: View) {
                                super.onRightBtnClick(view)
                                PermissionerManager.goToAppSetting(this@ScanActivity)
                            }
                        })
                }
            }
        )


    }

    override fun onStop() {
        zxingview.stopCamera()
        super.onStop()
    }

    public override fun onDestroy() {
        zxingview.onDestroy()
        super.onDestroy()
    }

    override fun onScanQRCodeSuccess(result: String) {
        zxingview.startSpot()
        ToastUtils.showShort("scan result == $result")
        LogUtils.e(result)
    }

    override fun onScanQRCodeOpenCameraError() {
        ToastUtils.showShort("打开相机出错")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, result: Intent?) {
        super.onActivityResult(requestCode, resultCode, result)
        if (resultCode == RESULT_OK && SELECT_PIC == requestCode) {
            val selectList = PictureSelector.obtainMultipleResult(result)
            // 例如 LocalMedia 里面返回五种path
            // 1.media.getPath(); 原图path
            // 2.media.getCutPath();裁剪后path，需判断media.isCut();切勿直接使用
            // 3.media.getCompressPath();压缩后path，需判断media.isCompressed();切勿直接使用
            // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
            // 5.media.getAndroidQToPath();Android Q版本特有返回的字段，但如果开启了压缩或裁剪还是取裁剪或压缩路径；注意：.isAndroidQTransform 为false 此字段将返回空
            // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
            for (media in selectList) {
                LogUtils.i("novice", "是否压缩:" + media.isCompressed)
                LogUtils.i("novice", "压缩:" + media.compressPath)
                LogUtils.i("novice", "原图:" + media.path)
                LogUtils.i("novice", "绝对路径:" + media.realPath)
                LogUtils.i("novice", "是否裁剪:" + media.isCut)
                LogUtils.i("novice", "裁剪:" + media.cutPath)
                LogUtils.i("novice", "是否开启原图:" + media.isOriginal)
                LogUtils.i("novice", "原图路径:" + media.originalPath)
                LogUtils.i("novice", "Android Q 特有Path:" + media.androidQToPath)
                LogUtils.i("novice", "宽高: " + media.width + "x" + media.height)
                LogUtils.i("novice", "Size: " + media.size)
            }
            handleAlbumPic(getMediaUriFromPath(this, selectList[0].path.safe()))


        }
    }

    private fun getMediaUriFromPath(context: Context, path: String): Uri? {
        val mediaUri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val cursor: Cursor = context.contentResolver.query(
            mediaUri,
            null,
            MediaStore.Images.Media.DISPLAY_NAME.toString() + "= ?",
            arrayOf(path.substring(path.lastIndexOf("/") + 1)),
            null
        )!!
        var uri: Uri? = null
        if (cursor.moveToFirst()) {
            uri = ContentUris.withAppendedId(
                mediaUri,
                cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID))
            )
        }
        cursor.close()
        return uri
    }

    /**
     * 处理选择的图片
     * @param data
     */
    private fun handleAlbumPic(uri: Uri?) {
        //获取选中图片的路径
        ToastUtils.showShort("正在扫描...")
        runOnUiThread {
            val result: Result? = scanningImage(uri)
            if (result != null) {
                ToastUtils.showShort("识别成功：" + result.text.safe())
            } else {
                ToastUtils.showShort("识别失败")
            }
        }
    }

    /**
     * 扫描二维码图片的方法
     * @param path
     * @return
     */
    private fun scanningImage(uri: Uri?): Result? {
        if (uri == null) {
            return null
        }
        val hints: Hashtable<DecodeHintType, String> = Hashtable()
        hints[DecodeHintType.CHARACTER_SET] = "UTF8" //设置二维码内容的编码
        var scanBitmap = BitmapUtil.decodeUri(Core.getContext(), uri, 500, 500)
        val source = RGBLuminanceSource(scanBitmap)
        val bitmap1 = BinaryBitmap(HybridBinarizer(source))
        val reader = QRCodeReader()
        try {
            return reader.decode(bitmap1, hints)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}