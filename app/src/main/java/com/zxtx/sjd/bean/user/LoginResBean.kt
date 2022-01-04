package com.zxtx.sjd.bean.user

import java.io.Serializable

/**
 * @Author : novice
 * @Date : 2022/1/4
 * @Desc : 手机号码登录返回数据
 */
// "data": {
//        "accountStatus": 0,   //账号状态；0：代表第一次登录（注册）；1：代表正常登录
//        "cardId": "sadf2bba36b447fc9573796f0c70e1c1",
//        "token": "2d0b6bba36b447fc9573796f0c70e1c1"
//    }
data class LoginResBean(var accountStatus: Int?, var cardId: String?, var token: String?) : Serializable
