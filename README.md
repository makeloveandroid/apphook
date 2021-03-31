# 前介
我不知道大伙有没有这么一个烦恼，随着项目越来越庞大。从代码海中去定位一块实现逻辑，犹如大海捞针。  

![](https://i04piccdn.sogoucdn.com/bc5406df8ff09590)  

# 解决思路
通过 `Xposed` 拦截一些核心的关键方法，然后将收集的信息下发，我们就可以快速的定位代码啦！  

![](https://i01piccdn.sogoucdn.com/38ed0109b513fa4b)

# 看看成品吧
![](https://z3.ax1x.com/2021/03/31/ckzVx0.png)  

![](https://i02piccdn.sogoucdn.com/d2dd8354fdccd5e8)  

![demo.gif](image/demo.gif)

# 如何使用呢？
1. 激活Xposed插件
2. 选择需要分析的应用（最好直选中1个）
3. 打开mac程序
4. 输入指令 `adb forward tcp:8000 localabstract:app_hook`

# 源码分析
## 入口
`Core.kt` 负责 `xposed` 的入口，主要用途来初始化&拦截。  

```java
/**
 * 应用级hook
 */
fun appHook(application: Application) {
    val context = application.applicationContext
    val app = application
    val classLoader = application.classLoader
    Utils.init(app)
    SharedPreferencesUtils.init(context)
    CallActionManager.init()
    WebSocketManager.onOpen(context)
    LocationHookUtils.startHook(classLoader)
//        DexHelper.loadDex(context)
    InitManager.init(app)
    HookManager.register()
    HookManager.hookAll(classLoader)
    log("hook完成!!")
}
```

## 虚拟定位
主要封装在 `LocationHookUtils` 中。

## 拦截Activity创建等事件
封装在 `ActivityAction` 中。

```java
Vclass ActivityAction : BaseAction<Activity, ActivityOnCreateData>() {
    override fun hook(classLoader: ClassLoader) {
        MethodHook
            .Builder()
            .setClass(Activity::class.java)
            .methodName("onCreate")
            .parameterTypes(Bundle::class.java)
            .afterHookedMethod {
                val activity = it.thisObject as Activity
                send(parsingData(activity))
            }
            .build()
            .execute()
    }
    .....
}
```

## View事件拦截
1. 点击事件 `ViewClickEventAction` 中，主要hook所有View的 `performClick` 方法，然后反射获取View的 `mOnClickListener` 输出对应View的点击监听对象。
2. 长按事件 `ViewLongClickEventAction` 中，主要hook所有View的 `performLongClickInternal` 方法，然后反射获取View的 `mOnLongClickListener` 输出对应View的点击监听对象。

## Fragment创建拦截
`FragmentCreateAction` 拦截所有 `Fragment` 的生命周期。  

```java
class FragmentCreateAction : BaseAction<Any, FragmentData>() {
    override fun hook(classLoader: ClassLoader) {
        // 区分Fragment
        val xFragment = ReflectUtils.findClass(classLoader, "androidx.fragment.app.Fragment")
        if (xFragment != null) {
            hookFragment("androidx.fragment.app.Fragment", xFragment)
        }
        val fragment = ReflectUtils.findClass(classLoader, "android.app.Fragment")
        if (fragment != null) {
            hookFragment("android.app.Fragment", fragment)
        }

        val v4fragment = ReflectUtils.findClass(classLoader, "android.support.v4.app.Fragment")
        if (v4fragment != null) {
            hookFragment("android.app.Fragment", v4fragment)
        }
    }
}
```
## startActivity方法拦截
`StartActivityAction` 中拦截 `Instrumentation` 的 `execStartActivity` 方法。

```java
class StartActivityAction : BaseAction<Intent, StartActivityOnCreateData>() {
    override fun hook(classLoader: ClassLoader) {
        MethodHook
            .Builder()
            .setClass(Instrumentation::class.java)
            .methodName("execStartActivity")
            .isHookAll(true)
            .afterHookedMethod {
                log("activity启动:${it.args.size}")
                if (it.args.size == 7) {
                    val context = it.args[0] as Context
                    val intent = it.args[4] as Intent
                    val data = parsingData(intent)
                    data.startContext = context.javaClass.name
                    data.stack = Log.getStackTraceString(Throwable())
                    send(data)
                }
            }
            .build()
            .execute()
    }
}
```

## 数据通讯
`WebSocketManager` 拦截应用启动后，移动端创建 `WebSocket` 服务。

```java
object WebSocketManager : SimpleEndpoint, CoroutineScope by GlobalScope {
    private lateinit var mServer: LocalSocketServer
    private lateinit var context: Context

    val conns = mutableListOf<SimpleSession>()

    fun onOpen(context: Context) {
        this.context = context
        try {
            log("启动!!")
            mServer = LocalSocketServer(
                "app_hook", "app_hook",
                LazySocketHandler(ForwardSocketHandlerFactory(context, this))
            )
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    mServer.run()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } catch (e: Exception) {
            log("出错:${e.message}")
        }
    }
    .....
}
```

在通过 `adb forward` 命令，将移动端的端口隐射到客户端。这样就能移动端&客户端通讯啦！  

![](https://z3.ax1x.com/2021/03/31/cAFJtU.png)

# 桌面程序
桌面程序我就用 `Flutter` 写啦！

# 总结
好啦！可以快乐的分析APP啦！详细代码大伙自己看吧！
