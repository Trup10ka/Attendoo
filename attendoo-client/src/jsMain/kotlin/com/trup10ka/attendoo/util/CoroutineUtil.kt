package com.trup10ka.attendoo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun launchCoroutine(block: suspend () -> Unit)
{
    CoroutineScope(Dispatchers.Default).launch {
        block()
    }
}
