package com.sibin.embibeassignment.base.baseclass


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sibin.embibeassignment.EmbibeApplication
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.base.dagger.ApplicationComponent
import com.sibin.embibeassignment.base.extension.inTransaction
import com.sibin.embibeassignment.base.extension.inTransactionWithBack

abstract class BaseActivity : AppCompatActivity() {

    private val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (application as EmbibeApplication).appComponent
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(R.layout.activity_layout)
        addFragment(savedInstanceState)
    }

    private fun addFragment(savedInstanceState: Bundle?) =
        savedInstanceState ?: supportFragmentManager.inTransaction {
            add(
                R.id.fragmentContainer,
                fragment()
            )
        }

    abstract fun fragment(): BaseFragment

}