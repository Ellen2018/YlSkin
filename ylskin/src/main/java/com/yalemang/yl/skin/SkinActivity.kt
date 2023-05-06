package com.yalemang.yl.skin

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity

open class SkinActivity : AppCompatActivity() {

    override fun getResources(): Resources {
        return YlSkinSDK.getResources()
    }

    open fun updateSkin(resources: Resources){

    }

    open fun updateSkinMethod():ActivitySkinUpdateMethod{
        return ActivitySkinUpdateMethod.COLD
    }

    open fun isUpdateSkin():Boolean{
        return true
    }
}