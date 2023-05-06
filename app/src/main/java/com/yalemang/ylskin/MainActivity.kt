package com.yalemang.ylskin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import com.yalemang.yl.skin.SkinActivity
import com.yalemang.yl.skin.YlSkinSDK
import java.io.File

class MainActivity : SkinActivity() {

    lateinit var tv:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv = findViewById(R.id.tv)

        tv.setOnClickListener {
            //切换皮肤
            YlSkinSDK.loadResourceAndApply(File(cacheDir,App.FILE_SKIN).absolutePath)
        }
    }
}