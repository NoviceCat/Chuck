package com.zxtx.sjd.network.login

import com.zxtx.sjd.bean.user.LoginCheckPicBean
import com.zxtx.sjd.bean.user.LoginResBean
import com.zxtx.sjd.network.manager.ResModel
import io.reactivex.Observable
import retrofit2.http.*

/**
 * @author novice
 */
interface UserApi {

    //手机登录
    @POST("login/go/new")
    fun phoneLogin(
        @Field("code") code: String?,//前端随机生成的标识码
        @Field("verificationCode") verificationCode: String?,//用户输入的手机验证码
        @Field("phone") phone: String?,//手机号
        @Field("inviteType") inviteType: Int?,//邀请方式
        @Field("cardId") cardId: String?,//邀请人ID
        @Field("cid") cid: String?//设备ID
    ): Observable<ResModel<LoginResBean?>?>?

    //获取短信验证码
    @FormUrlEncoded
    @POST("login/get/sms/verify")
    fun getLoginVerifyCode(
        @Field("code") code: String?,//前端随机生成的标识码
        @Field("left") left: Int?,//随机图左距离
        @Field("phone") phone: String?//手机号
    ): Observable<ResModel<Any?>?>?

    //获取短信验证码
    @FormUrlEncoded
    @POST("login/get/verify/code")
    fun getVerifyCode(
        @Field("code") code: String?//前端随机生成的标识码
    ): Observable<ResModel<LoginCheckPicBean?>?>?

}