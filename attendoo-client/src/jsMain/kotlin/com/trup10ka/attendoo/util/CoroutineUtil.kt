package com.trup10ka.attendoo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun launchDefaultCoroutine(block: suspend () -> Unit)
{
    CoroutineScope(Dispatchers.Default).launch {
        block()
    }
}
