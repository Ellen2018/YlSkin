package com.yalemang.ylskin

import android.os.Bundle
import com.yalemang.yl.skin.SkinActivity

class MainActivity : SkinActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}