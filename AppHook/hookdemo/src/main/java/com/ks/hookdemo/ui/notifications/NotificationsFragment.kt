package com.ks.hookdemo.ui.notifications

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ks.hookdemo.R
import com.ks.hookdemo.SampleApiService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsFragment : Fragment() {
    internal class Data(val thing: String)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        initView(root)
        return root
    }

    private fun initView(root: View) {
        root.findViewById<View>(R.id.btn1).setOnClickListener {
            doHttpActivity()
        }

    }

    private fun doHttpActivity() {
        val api = SampleApiService.getInstance(getClient(requireContext()))
        val cb: Callback<Void> = object : Callback<Void> {
            override fun onFailure(call: Call<Void>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                Log.d("wyz", "请求完成")
            }
        }
        api.post(SampleApiService.Data("posted")).enqueue(cb)
//        api.get().enqueue(cb)
//        api.delete().enqueue(cb)
//        api.status(201).enqueue(cb)
//        api.status(401).enqueue(cb)
//        api.status(500).enqueue(cb)
//        api.delay(9).enqueue(cb)
//        api.delay(15).enqueue(cb)
//        api.redirectTo("https://http2.akamai.com").enqueue(cb)
//        api.redirect(3).enqueue(cb)
//        api.redirectRelative(2).enqueue(cb)
//        api.redirectAbsolute(4).enqueue(cb)
//        api.stream(500).enqueue(cb)
//        api.streamBytes(2048).enqueue(cb)
//        api.image("image/png").enqueue(cb)
//        api.gzip().enqueue(cb)
//        api.xml().enqueue(cb)
//        api.utf8().enqueue(cb)
//        api.deflate().enqueue(cb)
//        api.cookieSet("v").enqueue(cb)
//        api.basicAuth("me", "pass").enqueue(cb)
//        api.drip(512, 5, 1, 200).enqueue(cb)
//        api.deny().enqueue(cb)
//        api.cache("Mon").enqueue(cb)
//        api.cache(30).enqueue(cb)
    }


    private fun getClient(context: Context): OkHttpClient? {
        return OkHttpClient.Builder() // Add a ChuckInterceptor instance to your OkHttp client
//            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }
}
