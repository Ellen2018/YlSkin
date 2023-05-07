package com.yalemang.ylskin

import android.app.Application
import com.yalemang.yl.skin.YlSkinSDK
import java.io.File

class App : Application() {

    companion object {
        const val FILE_NAME = "app-debug.apk"
        const val FILE_SKIN = "file_skin.apk"
        lateinit var file: File
        lateinit var file2: File
    }

    override fun onCreate() {
        super.onCreate()
        //初始化SDK
        file = File(cacheDir, FILE_NAME)
        file.delete()
        file2 = File(cacheDir, FILE_SKIN)
        file2.delete()
        copySkinApkToSD(FILE_NAME)
        copySkinApkToSD(FILE_SKIN)
        YlSkinSDK
            .init(this)
            .loadResources(file.absolutePath)
    }

    private fun copySkinApkToSD(assetName: String) {
        //不存在，先从服务器进行下载，这里模拟只从assets目录复制到本地目录
        FileUtils.copyFileFromAssets(this, assetName, cacheDir.absolutePath, assetName)
    }

}