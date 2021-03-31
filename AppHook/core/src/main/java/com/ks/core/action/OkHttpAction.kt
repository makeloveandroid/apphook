package com.ks.core.action

import com.ks.core.DexHelper
import com.ks.core.net.data.HttpTransaction
import com.ks.core.net.data.JsonConvertor
import com.ks.core.util.ReflectUtils
import java.util.*

class OkHttpAction : BaseAction<String, HttpTransaction>() {
    override fun hook(classLoader: ClassLoader) {
        val okttpClzz = ReflectUtils.findClass(classLoader, "okhttp3.OkHttpClient")
        val builderClzz = ReflectUtils.findClass(classLoader, "okhttp3.OkHttpClient\$Builder")
        if (okttpClzz != null && builderClzz != null) {
            MethodHook.Builder()
                .isConstructor(true)
                .setClass(okttpClzz)
                .parameterTypes(builderClzz)
                .beforeHookedMethod {
                    val interceptorClzz = DexHelper.loadClass("com.ks.dexhook.ChuckInterceptor")
                    val interceptor = interceptorClzz.getConstructor(Observer::class.java)
                        .newInstance(Observer { o, arg ->
                            val http = parsingData(JsonConvertor.getInstance().toJson(arg))
                            send(http)
                        })
                    val builder = it.args[0]
                    val interceptors: ArrayList<Any> =
                        ReflectUtils.readField(builder, "networkInterceptors") as ArrayList<Any>
                    interceptors?.add(interceptor)
                }
                .build()
                .execute()

//            log("开始拦截快手")
//            MethodHook.Builder()
//                .classLoader(classLoader)
//                .className("com.yxcorp.retrofit.BaseRetrofitConfig")
//                .methodName("buildClient")
//                .isHookAll(true)
//                .afterHookedMethod {
//
//                    log("拦截成功2!!buildClient ${it.args.size}")
//                    val okHttpClient = it.result
//                    if (okHttpClient != null) {
//                        log("进入")
//                        val interceptors: List<Any> =
//                            ReflectUtils.readField(okHttpClient, "interceptors") as List<Any>
//                        log("获得拦截器${interceptors.size}")
//                        val chuckInterceptors = interceptors.filter { interceptor ->
//                            log("拦截器:${interceptor}")
//                            interceptor.toString().contains("ChuckInterceptor")
//                        }
//                        if (chuckInterceptors.isEmpty()) {
//                            val interceptorClzz = DexHelper.loadClass("com.ks.dexhook.ChuckInterceptor")
//                            val interceptor = interceptorClzz.getConstructor(Observer::class.java)
//                                .newInstance(Observer { o, arg ->
//                                    val http = parsingData(JsonConvertor.getInstance().toJson(arg))
//                                    send(http)
//                                })
//
//                            val newBuilder =
//                                ReflectUtils.invokeMethod(okHttpClient, "newBuilder", null)
//                            val interceptors: ArrayList<Any> =
//                                ReflectUtils.readField(
//                                    newBuilder,
//                                    "networkInterceptors"
//                                ) as ArrayList<Any>
//                            interceptors?.add(interceptor)
//
//                            val newOkhttp =
//                                ReflectUtils.invokeMethod(newBuilder, "build", null)
//                            it.result = newOkhttp
//                        }
//                    }
//
//                }
//                .build()
//                .execute()
        }
    }

    override fun parsingData(input: String): HttpTransaction {
        val httpTransaction = JsonConvertor.getInstance()
            .fromJson(input, HttpTransaction::class.java)
        return httpTransaction
    }

}