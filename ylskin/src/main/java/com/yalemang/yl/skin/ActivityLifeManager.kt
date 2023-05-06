package com.yalemang.yl.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ActivityLifeManager: Application.ActivityLifecycleCallbacks {
    var activeActivity = ArrayList<Activity>()
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activeActivity.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
       activeActivity.remove(activity)
    }
}