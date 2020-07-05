package com.sibin.embibeassignment.base.data.network

import android.content.Context
import android.net.NetworkCapabilities
import com.sibin.embibeassignment.base.exception.Failure
import com.sibin.embibeassignment.base.extension.connectivityManager
import com.sibin.embibeassignment.base.data.functional.Either
import retrofit2.Call
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Injectable class which returns information about the network connection state.
 */
@Singleton
class NetworkHandler
@Inject constructor(private val context: Context) {

    fun isConnected(): Boolean {
        val networkCapabilities = context.connectivityManager?.activeNetwork ?: return false
        val activeNetwork =
            context.connectivityManager?.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun <T, R> request(call: Call<T>, transform: (T) -> R, default: T): Either<Failure, R> {
        return try {
            val response = call.execute()
            when (response.isSuccessful) {
                true -> {
                    if (response.body() != null) {
                        Either.Right(transform((response.body() ?: default)))
                    } else {
                        Either.Right(transform(default))
                    }
                }
                false -> {
                    Either.Left(Failure.ServerError)
                }

            }
        } catch (exception: Throwable) {
            exception.printStackTrace()
            Either.Left(Failure.ServerError)
        }
    }
}