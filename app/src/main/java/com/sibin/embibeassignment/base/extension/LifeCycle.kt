package com.sibin.embibeassignment.base.extension

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.sibin.embibeassignment.base.exception.Failure
import kotlin.reflect.KFunction1

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Failure>> LifecycleOwner.error(liveData: L, body: KFunction1<Failure, Unit>) =
    liveData.observe(this, Observer(body))