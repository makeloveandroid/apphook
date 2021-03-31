package com.ks.core.callaction

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.ks.core.net.data.JsonConvertor
import com.ks.core.util.SharedPreferencesUtils

data class Location(
    @SerializedName("j")
    var j: Double = -1.0,
    @SerializedName("w")
    var w: Double = -1.0
)

class LocationAction : BaseCallAction {
    override fun getActionName(): String {
        return javaClass.simpleName
    }

    override fun execute(context: Context, data: String) {
        val parameter =
            JsonConvertor.getInstance().fromJson(data, Location::class.java)
        SharedPreferencesUtils.LAT_S = parameter.w.toString()
        SharedPreferencesUtils.LNG_S = parameter.j.toString()
    }
}
