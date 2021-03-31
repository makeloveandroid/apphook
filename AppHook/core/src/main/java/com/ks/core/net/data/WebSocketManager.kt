package com.ks.core.net.data

import android.content.Context
import com.facebook.stetho.server.*
import com.facebook.stetho.server.ProtocolDetectingSocketHandler.AlwaysMatchMatcher
import com.facebook.stetho.server.http.ExactPathMatcher
import com.facebook.stetho.server.http.HandlerRegistry
import com.facebook.stetho.server.http.LightHttpServer
import com.facebook.stetho.websocket.SimpleEndpoint
import com.facebook.stetho.websocket.SimpleSession
import com.ks.core.callaction.CallActionManager
import com.ks.core.log
import kotlinx.coroutines.*
import org.json.JSONObject

internal class ForwardSocketHandlerFactory(
    val context: Context,
    val endpoint: SimpleEndpoint
) :
    SocketHandlerFactory {
    override fun create(): SocketHandler {
        val socketHandler = ProtocolDetectingSocketHandler(context)

        // Setup a light http server
        val registry = HandlerRegistry()
        registry.register(
            ExactPathMatcher("/apphook"),
            MyWebSocketHandler(endpoint)
        )
        val server = LightHttpServer(registry)
        socketHandler.addHandler(
            AlwaysMatchMatcher(),
            SocketLikeHandler { socket -> server.serve(socket) })
        return socketHandler
    }

}


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

    override fun onOpen(session: SimpleSession?) {
        session?.let(conns::add)
    }

    override fun onMessage(session: SimpleSession?, message: String?) {
        log("消息来了:$message")
        val jsonObject = JSONObject(message)
        val action = jsonObject.getString("action")
        val data = jsonObject.getJSONObject("data").toString()
        CallActionManager.execute(context, action, data)
    }

    override fun onMessage(session: SimpleSession?, message: ByteArray?, messageLen: Int) {
    }

    override fun onClose(
        session: SimpleSession?,
        closeReasonCode: Int,
        closeReasonPhrase: String?
    ) {
        conns.remove(session)
    }

    override fun onError(session: SimpleSession?, t: Throwable?) {
        log("onError:服务出错  ${t?.message}")
    }

    fun close() {
        cancel()
    }

    fun sendMsg(data: String) = launch {
        conns.forEach {
            it.sendText(data)
        }
    }

}