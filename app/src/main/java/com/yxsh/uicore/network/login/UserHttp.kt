package com.yxsh.uicore.network.login

import com.yxsh.uicore.bean.user.LoginCheckPicBean
import com.yxsh.uicore.network.HttpManager
import com.yxsh.uicore.network.ReqModel
import com.yxsh.uicore.network.ResponseTransformer
import com.yxsh.uicore.network.RxHelper
import com.yxsh.uicore.util.LocalUtil
import io.reactivex.Observable
import java.util.HashMap
import kotlin.random.Random

/**
 * @Author : novice
 * @Date : 2022/1/4
 * @Desc : 用户数据相关接口
 */
class UserHttp {

    companion object {

        fun getLoginCheckPicDetail(): Observable<LoginCheckPicBean>? {
            return HttpManager.instance!!.getUserApi().getVerifyCode(LocalUtil.getRandomStr())!!
                .compose(ResponseTransformer.handleResult())
                .compose(RxHelper.io_main())
        }

        fun getLoginVerifyCode(left: Int, phone: String): Observable<Any>? {
            return HttpManager.instance!!.getUserApi()
                .getLoginVerifyCode(LocalUtil.getRandomStr(), left, phone)!!
                .compose(ResponseTransformer.handleResult())
                .compose(RxHelper.io_main())
        }


    }
}