package com.yalemang.ylskin

import android.content.res.Resources
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yalemang.yl.skin.SkinChangeListener
import com.yalemang.yl.skin.YlSkinSDK

class SkinManagerActivity:AppCompatActivity(),SkinChangeListener{

    private lateinit var bt:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin_manager)
        bt = findViewById(R.id.bt)
        bt.setOnClickListener {
            YlSkinSDK.loadResourceAndApply(App.file2.absolutePath)
        }
        YlSkinSDK.registerSkinChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        YlSkinSDK.unRegisterSkinChangeListener(this)
    }

    override fun change(resources: Resources) {
       Toast.makeText(this,"皮肤切换成功",Toast.LENGTH_SHORT).show()
    }

}