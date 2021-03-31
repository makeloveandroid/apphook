package com.ks.core.callaction.ks

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.ks.core.action.BaseAction
import com.ks.core.callaction.BaseCallAction
import com.ks.core.util.ReflectUtils

data class ApiHost(
    @SerializedName("host")
    var host: String = "",
    @SerializedName("isHttps")
    var isHttps: Boolean = false,
    @SerializedName("isGetConfig")
    var isGetConfig: Boolean = false
)

class TestApiAction : BaseCallAction, BaseAction<ApiHost, ApiHost>() {
    override fun getActionName(): String {
        return TestApiAction::class.simpleName!!
    }

    override fun execute(context: Context, data: String) {
        val parameter =
            Gson().fromJson(data, ApiHost::class.java)
        if (parameter.isGetConfig) {
            // 获取配置
            val isDisableHttps = ReflectUtils.invokeStaticMethod(
                context.classLoader,
                "com.kwai.framework.testconfig.ServerTestConfig",
                "isDisableHttps",
                emptyArray()
            ) as Boolean

            //     Host host = Singleton.get(Router.class).getHost(RouteType.API);
            val routerClzz = context.classLoader.loadClass("com.yxcorp.router.Router")
            val singleton = ReflectUtils.invokeStaticMethod(
                context.classLoader,
                "com.yxcorp.utility.singleton.Singleton",
                "get",
                arrayOf(Class::class.java),
                routerClzz
            )
            val routerTypeClzz = context.classLoader.loadClass("com.kwai.framework.model.router.RouteType")
            routerTypeClzz.enumConstants?.find {
                "API".equals(it.toString())
            }?.let {
                val host = ReflectUtils.invokeMethod(
                    singleton,
                    "getHost",
                    arrayOf(routerTypeClzz),
                    it
                )
                //    String apiTestServer = host != null ? host.mHost : "";
                val apiTestServer = host?.let {
                    ReflectUtils.getField(host::class.java, "mHost").get(host) as? String ?: ""
                } ?: ""
                parameter.host = apiTestServer
            }
            parameter.isHttps = isDisableHttps
            send(parsingData(parameter))
            return
        } else {
            // 设置配置
            // ServerTestConfig.setApiTestIdc(ServerTestConst.STAGING_SERVER_API_ADDRESS);
            ReflectUtils.invokeStaticMethod(
                context.classLoader,
                "com.kwai.framework.testconfig.ServerTestConfig",
                "setApiTestIdc",
                arrayOf(String::class.java),
                parameter.host
            )

            // 强制Http走https
            // ServerTestConfig.setDisableHttps(mForceUseHttpSwitch.getSwitch());

            ReflectUtils.invokeStaticMethod(
                context.classLoader,
                "com.kwai.framework.testconfig.ServerTestConfig",
                "setDisableHttps",
                arrayOf(Boolean::class.java),
                parameter.isHttps
            )
        }


    }

    override fun hook(classLoader: ClassLoader) {

    }

    override fun parsingData(input: ApiHost): ApiHost {
        return input
    }

}