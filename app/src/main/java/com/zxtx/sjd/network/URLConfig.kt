package com.zxtx.sjd.network

/**
 * @author novice
 */
object URLConfig {

    const val URL_TYPE = 0// 0 测试环境 1 正式环境

    const val BASE_URL_DEBUG = "http://39.106.44.116:8263" //测试环境
    const val BASE_URL_RELEASE = "http://8.141.56.9:8363" //生产环境

    var BASE_URL = if (URL_TYPE == 0) BASE_URL_DEBUG else BASE_URL_RELEASE

    var USER_API = "$BASE_URL/userapi/"


}