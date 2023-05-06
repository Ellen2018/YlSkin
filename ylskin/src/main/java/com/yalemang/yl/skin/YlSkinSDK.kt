package com.yalemang.yl.skin

import android.app.Application
import android.content.res.AssetManager
import android.content.res.Resources
import java.lang.ref.WeakReference

object YlSkinSDK {

    //原始的Resources
    private lateinit var originalResources: Resources

    //皮肤Resources
    private lateinit var skinResources: Resources
    private lateinit var appWr: WeakReference<Application>
    private var skinUpdateMethod: SkinUpdateMethod = SkinUpdateMethod.COLD
    private var activityLifeManager: ActivityLifeManager = ActivityLifeManager()

    fun setUpdateMethod(skinUpdateMethod: SkinUpdateMethod):YlSkinSDK{
        this.skinUpdateMethod = skinUpdateMethod
        return this
    }

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

        //更新皮肤
        when(skinUpdateMethod){
            SkinUpdateMethod.COLD->{
                //冷更新，将所有Activity进行ReCreate
                for(activity in activityLifeManager.activeActivity){
                    if(activity is SkinActivity){
                        if(activity.isUpdateSkin()){
                            activity.recreate()
                        }
                    }else {
                        activity.recreate()
                    }
                }
            }
            SkinUpdateMethod.HOT->{
                //以回调方式通知所有存活的Activity
                for(activity in activityLifeManager.activeActivity){
                    if(activity is SkinActivity){
                        if(activity.isUpdateSkin()) {
                            activity.updateSkin()
                        }
                    }else {
                        activity.recreate()
                    }
                }
            }
            SkinUpdateMethod.AUTO->{
                //自定义方式,按照存活的Activity的updateSkinMethod方法返回的方式进行更换
                for(activity in activityLifeManager.activeActivity){
                    if(activity is SkinActivity){
                        if(activity.isUpdateSkin()) {
                            if (activity.updateSkinMethod() == ActivitySkinUpdateMethod.COLD) {
                                activity.recreate()
                            } else {
                                activity.updateSkin()
                            }
                        }
                    }else {
                        activity.recreate()
                    }
                }
            }
        }
    }


    /**
     * 初始化
     */
    fun init(application: Application): YlSkinSDK {
        appWr = WeakReference(application)
        originalResources = application.resources
        //注册Activity监听
        application.registerActivityLifecycleCallbacks(activityLifeManager)
        return this
    }

}