package com.ks.apphook.dao

import android.content.Context
import com.ks.apphook.AppInfo

object DaoManager {
    lateinit var daoSession: DaoSession
    lateinit var helper: DaoMaster.DevOpenHelper
    fun init(context: Context) {
        if (!this::daoSession.isInitialized){
            helper = DaoMaster.DevOpenHelper(context, "HOOK_APPS", null);
            daoSession = DaoMaster(helper.writableDatabase).newSession()
        }
    }

    fun getHookApps(): List<AppInfo> {
        return daoSession.appInfoDao.loadAll()
    }

    fun haveHookApp(pkg: String): Boolean {
        return daoSession.appInfoDao.queryBuilder().where(AppInfoDao.Properties.Pkg.eq(pkg))
            .unique() != null
    }

    fun deleteHookApp(pkg: String) {
        daoSession.appInfoDao.queryBuilder().where(AppInfoDao.Properties.Pkg.eq(pkg))
            .buildDelete().executeDeleteWithoutDetachingEntities()
    }

    fun saveHookApp(appInfo: AppInfo): Long {
        return daoSession.appInfoDao.insert(appInfo)
    }
}