package com.yalemang.ylskin

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yalemang.yl.skin.YlSkinSupport

/**
 * 对应旧项目的Activity接入方式
 */
class OldActivity : AppCompatActivity(), YlSkinSupport {

    private lateinit var tv:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_old)
        tv = findViewById(R.id.tv)
        tv.setOnClickListener {
            //切换皮肤
            startActivity(Intent(this,SkinManagerActivity::class.java))
        }
    }

    override fun updateSkin(resources: Resources) {
        tv.text = resources.getString(R.string.app_name)
        tv.setTextColor(resources.getColor(R.color.black,theme))
    }
}