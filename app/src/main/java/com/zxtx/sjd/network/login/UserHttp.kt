package com.zxtx.sjd.network.login

import com.zxtx.sjd.bean.user.LoginCheckPicBean
import com.zxtx.sjd.network.manager.HttpManager
import com.zxtx.sjd.network.manager.ResponseTransformer
import com.zxtx.sjd.network.manager.RxHelper
import com.zxtx.sjd.util.LocalUtil
import io.reactivex.Observable

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