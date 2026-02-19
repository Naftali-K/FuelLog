package com.example.fuellog.DBRoom

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Build
import android.webkit.WebView
import com.facebook.stetho.BuildConfig
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient


class StethoChromeDB: Application() {

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (0 != (getApplicationInfo().flags and ApplicationInfo.FLAG_DEBUGGABLE)) {
//                WebView.setWebContentsDebuggingEnabled(true)
//            }
//        }
//
//        Stetho.initialize(Stetho.newInitializerBuilder(this)
//            .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//            .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//            .build());
//
//        val client = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()

        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                    .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this)
                    )
                    .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this)
                    )
                    .build()
            )
        }
    }
}