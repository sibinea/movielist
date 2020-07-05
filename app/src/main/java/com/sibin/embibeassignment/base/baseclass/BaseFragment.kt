package com.sibin.embibeassignment.base.baseclass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.sibin.embibeassignment.EmbibeApplication
import com.sibin.embibeassignment.R
import com.sibin.embibeassignment.base.dagger.ApplicationComponent
import com.sibin.embibeassignment.base.extension.inTransaction
import com.sibin.embibeassignment.base.extension.inTransactionWithBack
import com.sibin.embibeassignment.base.extension.viewContainer
import kotlinx.android.synthetic.main.activity_layout.*
import javax.inject.Inject


abstract class BaseFragment : Fragment() {

    abstract fun layoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as EmbibeApplication).appComponent
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(
        layoutId(), container,
        false
    )

    internal fun showProgress() = progressStatus(View.VISIBLE)

    internal fun hideProgress() = progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is BaseActivity) this.progress.visibility = viewStatus }

    internal fun notify(message: String) =
        Snackbar.make(viewContainer, message, Snackbar.LENGTH_SHORT).show()

    open fun addFragment(fragment: BaseFragment, hasBackStack: Boolean = false) {
        if (hasBackStack) {
            activity?.supportFragmentManager?.inTransactionWithBack {
                add(R.id.fragmentContainer, fragment)
            }
        } else {
            activity?.supportFragmentManager?.inTransaction {
                replace(R.id.fragmentContainer, fragment)
            }
        }
    }


}