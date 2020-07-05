package com.sibin.embibeassignment.appFeatures.ui

import android.os.Bundle
import com.sibin.embibeassignment.appFeatures.ui.fragment.MoviesListFragment
import com.sibin.embibeassignment.base.baseclass.BaseActivity
import com.sibin.embibeassignment.base.common.logDebug


class MoviesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        logDebug("onCreate")
    }

    override fun fragment() = MoviesListFragment()

}

