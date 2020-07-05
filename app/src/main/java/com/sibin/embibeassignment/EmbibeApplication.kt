package com.sibin.embibeassignment

import android.app.Application
import com.sibin.embibeassignment.base.dagger.ApplicationComponent
import com.sibin.embibeassignment.base.dagger.DaggerApplicationComponent
import com.sibin.embibeassignment.base.dagger.module.ApplicationModule


class EmbibeApplication : Application() {
    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(
                ApplicationModule(
                    this
                )
            )
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()
    }

    private fun injectMembers() = appComponent.inject(this)
}