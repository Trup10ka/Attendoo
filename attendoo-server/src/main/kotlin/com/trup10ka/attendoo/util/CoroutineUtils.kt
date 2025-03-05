package com.trup10ka.attendoo.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun launchIOCoroutine(block: suspend () -> Unit)
{
    CoroutineScope(Dispatchers.IO).launch {
        block()
    }
}
