package com.ks.core.callaction

import android.content.Context

interface BaseCallAction{
    fun getActionName(): String
    fun execute(context: Context, data: String)
}