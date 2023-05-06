package com.yalemang.ylskin

import android.app.Application
import com.yalemang.yl.skin.YlSkinSDK

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        //初始化SDK
        YlSkinSDK
            .init(this)
            .loadResources("")
    }

}