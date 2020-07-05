package com.sibin.embibeassignment.base.dagger

import com.sibin.embibeassignment.EmbibeApplication
import com.sibin.embibeassignment.base.baseclass.BaseActivity
import com.sibin.embibeassignment.base.baseclass.BaseFragment
import com.sibin.embibeassignment.base.dagger.module.ApplicationModule
import com.sibin.embibeassignment.base.dagger.module.RepositoryModule
import com.sibin.embibeassignment.base.dagger.module.StorageModule
import com.sibin.embibeassignment.base.dagger.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, ViewModelModule::class, RepositoryModule::class, StorageModule::class]
)
interface ApplicationComponent {
    fun inject(application: EmbibeApplication)
    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)

}