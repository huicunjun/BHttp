package com.ldw.bhttp

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * @date 2020/6/5 19:53
 * @user 威威君
 */
suspend fun <T> BaseBHttp<T>.await(): T = suspendCancellableCoroutine { continuation ->
/*    val subscribe = subscribe({

    }, {

    })
    continuation.resume(it)
    continuation.resumeWithException(it)
    continuation.invokeOnCancellation {
        subscribe.dispose()
    }*/
}