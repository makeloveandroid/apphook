package com.ks.core.util

import java.io.File

object FileUtil {
    fun writeToFile(path: String, byteArray: ByteArray) {
        File(path).writeBytes(byteArray)
    }
}