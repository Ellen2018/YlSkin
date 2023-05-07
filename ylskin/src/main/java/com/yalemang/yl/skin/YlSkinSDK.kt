package com.yalemang.yl.skin

import android.app.Application
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
import java.io.File
import java.lang.ref.WeakReference

object YlSkinSDK {

    //原始的Resources
    private lateinit var originalResources: Resources

    //皮肤Resources
    private lateinit var skinResources: Resources
    private lateinit var appWr: WeakReference<Application>
    private var activityLifeManager: ActivityLifeManager = ActivityLifeManager()
    private var skinChangeListeners = ArrayList<SkinChangeListener>()
    private const val FIELD_APP_RESOURCES = "mResources"

    /**
     * 获取当前的Resources
     */
    fun getResources(): Resources {
        return if (this::skinResources.isInitialized) {
            skinResources
        } else {
            originalResources
        }
    }

    fun getOriginalResources(): Resources {
        return originalResources
    }

    fun isUseSkin(): Boolean {
        return this::skinResources.isInitialized
    }

    private fun getApp(): Application? {
        return appWr.get()
    }

    /**
     * 加载皮肤并且应用
     */
    fun loadResourceAndApply(apkPath: String) {
        if (!loadResources(apkPath)) {
            return
        }
        //更新皮肤
        for (activity in activityLifeManager.activeActivity) {
            if (activity is SkinActivity) {
                if (activity.isUpdateSkin()) {
                    activity.updateSkin(skinResources)
                }
            }else{
                if(activity is YlSkinSupport){
                    activity.updateSkin(skinResources)
                }
            }
        }
        for(s in skinChangeListeners){
            s.change(skinResources)
        }
    }

    /**
     * 一般在Application里进行调用
     */
    fun loadResources(apkPath: String): Boolean {
        if (!File(apkPath).exists()) {
            return false
        }
        //需要判断皮肤包是否存在
        val appResources = getApp()?.resources
        val assetManager = AssetManager::class.java.newInstance()
        val addAssetPath = assetManager.javaClass.getMethod(
            "addAssetPath",
            String::class.java
        )
        addAssetPath.invoke(assetManager, apkPath)
        skinResources = Resources(
            assetManager,
            appResources?.displayMetrics, appResources?.configuration
        )
        replaceAppResources()
        return true
    }

    private fun replaceAppResources() {
        val app = getApp() as Application
        val context = app.baseContext
        val mResourcesField = context.javaClass.getDeclaredField(FIELD_APP_RESOURCES)
        mResourcesField.isAccessible = true
        mResourcesField.set(context, skinResources)
    }

    fun registerSkinChangeListener(skinChangeListener: SkinChangeListener) {
        skinChangeListeners.add(skinChangeListener)
    }

    fun unRegisterSkinChangeListener(skinChangeListener: SkinChangeListener) {
        skinChangeListeners.remove(skinChangeListener)
    }

    /**
     * 初始化
     */
    fun init(application: Application): YlSkinSDK {
        appWr = WeakReference(application)
        originalResources = application.resources
        skinResources = originalResources
        //注册Activity监听
        application.registerActivityLifecycleCallbacks(activityLifeManager)
        return this
    }

}