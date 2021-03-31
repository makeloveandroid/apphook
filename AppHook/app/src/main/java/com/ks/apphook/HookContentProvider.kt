package com.ks.apphook

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import com.ks.apphook.dao.DaoManager
import com.ks.core.log

class HookContentProvider : ContentProvider() {
    lateinit var readableDatabase: SQLiteDatabase
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        log("收到判断")
        return readableDatabase.query(
            "APP_INFO",
            null,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }

    override fun onCreate(): Boolean {
        log("HookContentProvider 创建")
        DaoManager.init(context!!)
        readableDatabase = DaoManager.helper.readableDatabase
        return true
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return "Hook"
    }


}