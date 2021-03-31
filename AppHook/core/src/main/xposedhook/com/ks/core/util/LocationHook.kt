package com.ks.core.util

import android.location.*
import android.os.Build
import android.os.SystemClock
import android.telephony.*
import android.telephony.gsm.GsmCellLocation
import android.util.Log
import com.ks.core.action.MethodHook
import com.ks.core.util.SharedPreferencesUtils.LAT_S
import com.ks.core.util.SharedPreferencesUtils.LNG_S
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import java.lang.Boolean
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.util.*

object LocationHookUtils {
    fun randInt(min: Int, max: Int): Int {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        val rand = Random()

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt(max - min + 1) + min
    }

    fun startHook(classLoader: ClassLoader?) {
        try {
            val lat = LAT_S.toDouble()
            val lng = LNG_S.toDouble()
            if (lat > 0 && lng > 0) {
                val lac = randInt(200, 65535)
                val cid = randInt(200, 65535)
                Log.d("wyz", "虚拟定位:$lat  $lng  $lac  $cid")
                HookAndChange(classLoader, lat, lng, lac, cid)
            }
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
    }

    fun HookAndChange(
        classLoader: ClassLoader?,
        latitude: Double,
        longtitude: Double,
        lac: Int,
        cid: Int
    ) {
        MethodHook.Builder()
            .classLoader(classLoader!!)
            .className("android.telephony.TelephonyManager")
            .methodName("getCellLocation")
            .isHookAll(true)
            .afterHookedMethod {
                val gsmCellLocation =
                    GsmCellLocation()
                gsmCellLocation.setLacAndCid(lac, cid)
                it.result = gsmCellLocation
                null
            }
            .build()
            .execute()
        MethodHook.Builder()
            .classLoader(classLoader)
            .className("android.telephony.PhoneStateListener")
            .methodName("onCellLocationChanged")
            .isHookAll(true)
            .afterHookedMethod {
                val gsmCellLocation =
                    GsmCellLocation()
                gsmCellLocation.setLacAndCid(lac, cid)
                it.result = gsmCellLocation
                null
            }
            .build()
            .execute()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            MethodHook.Builder()
                .classLoader(classLoader)
                .className("android.telephony.TelephonyManager")
                .methodName("getPhoneCount")
                .isHookAll(true)
                .afterHookedMethod {
                    it.result = 1
                    null
                }
                .build()
                .execute()
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            MethodHook.Builder()
                .classLoader(classLoader)
                .className("android.telephony.TelephonyManager")
                .methodName("getNeighboringCellInfo")
                .isHookAll(true)
                .afterHookedMethod {
                    it.result = ArrayList<Any>()
                    null
                }
                .build()
                .execute()
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            XposedHelpers.findAndHookMethod("android.telephony.TelephonyManager",
                classLoader,
                "getAllCellInfo",
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun afterHookedMethod(param: MethodHookParam) {
                        param.result = getCell(460, 0, lac, cid, 0, 0)
                    }
                })
            XposedHelpers.findAndHookMethod("android.telephony.PhoneStateListener",
                classLoader,
                "onCellInfoChanged",
                MutableList::class.java,
                object : XC_MethodHook() {
                    @Throws(Throwable::class)
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        param.result = getCell(460, 0, lac, cid, 0, 0)
                    }
                })
        }
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiManager",
            classLoader,
            "getScanResults",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = ArrayList<Any>()
                }
            })
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiManager",
            classLoader,
            "getWifiState",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = 1
                }
            })
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiManager",
            classLoader,
            "isWifiEnabled",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiInfo",
            classLoader,
            "getMacAddress",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = "00-00-00-00-00-00-00-00"
                }
            })
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiInfo",
            classLoader,
            "getSSID",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = "null"
                }
            })
        XposedHelpers.findAndHookMethod(
            "android.net.wifi.WifiInfo",
            classLoader,
            "getBSSID",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = "00-00-00-00-00-00-00-00"
                }
            })
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo",
            classLoader,
            "getTypeName",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = "WIFI"
                }
            })
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo",
            classLoader,
            "isConnectedOrConnecting",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo",
            classLoader,
            "isConnected",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })
        XposedHelpers.findAndHookMethod("android.net.NetworkInfo",
            classLoader,
            "isAvailable",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })
        XposedHelpers.findAndHookMethod("android.telephony.CellInfo",
            classLoader,
            "isRegistered",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = true
                }
            })
        XposedHelpers.findAndHookMethod(
            LocationManager::class.java,
            "getLastLocation",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    Log.d("wyz", "getLastLocation")
                    val l =
                        Location(LocationManager.GPS_PROVIDER)
                    l.latitude = latitude
                    l.longitude = longtitude
                    l.accuracy = 100f
                    l.time = System.currentTimeMillis()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        l.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
                    }
                    param.result = l
                }
            })
        XposedHelpers.findAndHookMethod(
            LocationManager::class.java, "getLastKnownLocation",
            String::class.java, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    Log.d("wyz", "getLastKnownLocation")
                    val l =
                        Location(LocationManager.GPS_PROVIDER)
                    l.latitude = latitude
                    l.longitude = longtitude
                    l.accuracy = 100f
                    l.time = System.currentTimeMillis()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        l.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
                    }
                    param.result = l
                }
            })
        XposedBridge.hookAllMethods(
            LocationManager::class.java,
            "getProviders",
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    val arrayList =
                        ArrayList<String>()
                    arrayList.add("gps")
                    param.result = arrayList
                }
            })
        XposedHelpers.findAndHookMethod(
            LocationManager::class.java,
            "getBestProvider",
            Criteria::class.java,
            Boolean.TYPE,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    param.result = "gps"
                }
            })
        XposedHelpers.findAndHookMethod(
            LocationManager::class.java,
            "addGpsStatusListener",
            GpsStatus.Listener::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    if (param.args[0] != null) {
                        XposedHelpers.callMethod(
                            param.args[0],
                            "onGpsStatusChanged",
                            1
                        )
                        XposedHelpers.callMethod(
                            param.args[0],
                            "onGpsStatusChanged",
                            3
                        )
                    }
                }
            })
        XposedHelpers.findAndHookMethod(
            LocationManager::class.java,
            "addNmeaListener",
            GpsStatus.NmeaListener::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun beforeHookedMethod(param: MethodHookParam) {
                    param.result = false
                }
            })
        XposedHelpers.findAndHookMethod("android.location.LocationManager",
            classLoader,
            "getGpsStatus",
            GpsStatus::class.java,
            object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    val gss =
                        param.result as GpsStatus ?: return
                    val clazz: Class<*> = GpsStatus::class.java
                    var m: Method? = null
                    for (method in clazz.declaredMethods) {
                        if (method.name == "setStatus") {
                            if (method.parameterTypes.size > 1) {
                                m = method
                                break
                            }
                        }
                    }
                    if (m == null) return

                    //access the private setStatus function of GpsStatus
                    m.isAccessible = true

                    //make the apps belive GPS works fine now
                    val svCount = 5
                    val prns = intArrayOf(1, 2, 3, 4, 5)
                    val snrs = floatArrayOf(0f, 0f, 0f, 0f, 0f)
                    val elevations = floatArrayOf(0f, 0f, 0f, 0f, 0f)
                    val azimuths = floatArrayOf(0f, 0f, 0f, 0f, 0f)
                    val ephemerisMask = 0x1f
                    val almanacMask = 0x1f

                    //5 satellites are fixed
                    val usedInFixMask = 0x1f
                    XposedHelpers.callMethod(
                        gss,
                        "setStatus",
                        svCount,
                        prns,
                        snrs,
                        elevations,
                        azimuths,
                        ephemerisMask,
                        almanacMask,
                        usedInFixMask
                    )
                    param.args[0] = gss
                    param.result = gss
                    try {
                        m.invoke(
                            gss,
                            svCount,
                            prns,
                            snrs,
                            elevations,
                            azimuths,
                            ephemerisMask,
                            almanacMask,
                            usedInFixMask
                        )
                        param.result = gss
                    } catch (e: Exception) {
                        XposedBridge.log(e)
                    }
                }
            })
        for (method in LocationManager::class.java.declaredMethods) {
            if (method.name == "requestLocationUpdates" && !Modifier.isAbstract(
                    method.modifiers
                )
                && Modifier.isPublic(method.modifiers)
            ) {
                XposedBridge.hookMethod(
                    method,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            if (param.args.size >= 4 && param.args[3] is LocationListener) {
                                val ll =
                                    param.args[3] as LocationListener
                                val clazz: Class<*> =
                                    LocationListener::class.java
                                var m: Method? = null
                                for (method in clazz.declaredMethods) {
                                    if (method.name == "onLocationChanged" && !Modifier.isAbstract(
                                            method.modifiers
                                        )
                                    ) {
                                        m = method
                                        break
                                    }
                                }
                                val l =
                                    Location(LocationManager.GPS_PROVIDER)
                                l.latitude = latitude
                                l.longitude = longtitude
                                l.accuracy = 10.00f
                                l.time = System.currentTimeMillis()
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                    l.elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos()
                                }
                                XposedHelpers.callMethod(
                                    ll,
                                    "onLocationChanged",
                                    l
                                )
                                try {
                                    if (m != null) {
                                        m!!.invoke(ll, l)
                                    }
                                } catch (e: Exception) {
                                    XposedBridge.log(e)
                                }
                            }
                        }
                    })
            }
            if (method.name == "requestSingleUpdate " && !Modifier.isAbstract(
                    method.modifiers
                )
                && Modifier.isPublic(method.modifiers)
            ) {
                XposedBridge.hookMethod(
                    method,
                    object : XC_MethodHook() {
                        @Throws(Throwable::class)
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            if (param.args.size >= 3 && param.args[1] is LocationListener) {
                                val ll =
                                    param.args[3] as LocationListener
                                val clazz: Class<*> =
                                    LocationListener::class.java
                                var m: Method? = null
                                for (method in clazz.declaredMethods) {
                                    if (method.name == "onLocationChanged" && !Modifier.isAbstract(
                                            method.modifiers
                                        )
                                    ) {
                                        m = method
                                        break
                                    }
                                }
                                try {
                                    if (m != null) {
                                        val l =
                                            Location(LocationManager.GPS_PROVIDER)
                                        l.latitude = latitude
                                        l.longitude = longtitude
                                        l.accuracy = 100f
                                        l.time = System.currentTimeMillis()
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                            l.elapsedRealtimeNanos =
                                                SystemClock.elapsedRealtimeNanos()
                                        }
                                        m!!.invoke(ll, l)
                                    }
                                } catch (e: Exception) {
                                    XposedBridge.log(e)
                                }
                            }
                        }
                    })
            }
        }
    }

    private fun getCell(
        mcc: Int,
        mnc: Int,
        lac: Int,
        cid: Int,
        sid: Int,
        networkType: Int
    ): ArrayList<*> {
        val arrayList: ArrayList<Any> = ArrayList()
        val cellInfoGsm =
            XposedHelpers.newInstance(
                CellInfoGsm::class.java
            ) as CellInfoGsm
        XposedHelpers.callMethod(
            cellInfoGsm, "setCellIdentity", XposedHelpers.newInstance(
                CellIdentityGsm::class.java, *arrayOf<Any>(
                    Integer.valueOf(mcc),
                    Integer.valueOf(mnc),
                    Integer.valueOf(
                        lac
                    ),
                    Integer.valueOf(cid)
                )
            )
        )
        val cellInfoCdma =
            XposedHelpers.newInstance(
                CellInfoCdma::class.java
            ) as CellInfoCdma
        XposedHelpers.callMethod(
            cellInfoCdma, "setCellIdentity", XposedHelpers.newInstance(
                CellIdentityCdma::class.java,
                *arrayOf<Any>(
                    Integer.valueOf(lac),
                    Integer.valueOf(sid),
                    Integer.valueOf(cid),
                    Integer.valueOf(0),
                    Integer.valueOf(0)
                )
            )
        )
        val cellInfoWcdma =
            XposedHelpers.newInstance(
                CellInfoWcdma::class.java
            ) as CellInfoWcdma
        XposedHelpers.callMethod(
            cellInfoWcdma, "setCellIdentity", XposedHelpers.newInstance(
                CellIdentityWcdma::class.java,
                *arrayOf<Any>(
                    Integer.valueOf(mcc),
                    Integer.valueOf(mnc),
                    Integer.valueOf(lac),
                    Integer.valueOf(cid),
                    Integer.valueOf(300)
                )
            )
        )
        val cellInfoLte =
            XposedHelpers.newInstance(
                CellInfoLte::class.java
            ) as CellInfoLte
        XposedHelpers.callMethod(
            cellInfoLte, "setCellIdentity", XposedHelpers.newInstance(
                CellIdentityLte::class.java,
                *arrayOf<Any>(
                    Integer.valueOf(mcc),
                    Integer.valueOf(mnc),
                    Integer.valueOf(cid),
                    Integer.valueOf(300),
                    Integer.valueOf(lac)
                )
            )
        )
        if (networkType == 1 || networkType == 2) {
            arrayList.add(cellInfoGsm)
        } else if (networkType == 13) {
            arrayList.add(cellInfoLte)
        } else if (networkType == 4 || networkType == 5 || networkType == 6 || networkType == 7 || networkType == 12 || networkType == 14) {
            arrayList.add(cellInfoCdma)
        } else if (networkType == 3 || networkType == 8 || networkType == 9 || networkType == 10 || networkType == 15) {
            arrayList.add(cellInfoWcdma)
        }
        return arrayList
    }
}