package org.bxkr.vkarticle

import android.app.Application
import com.google.android.material.color.DynamicColors

class VKArticleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}