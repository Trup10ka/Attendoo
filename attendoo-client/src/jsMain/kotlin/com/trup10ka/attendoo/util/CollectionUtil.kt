package com.trup10ka.attendoo.util

fun <K, V> MutableMap<K, V>.addAll(vararg pairs: Pair<K, V>)
{
    for (pair in pairs)
    {
        this[pair.first] = pair.second
    }
}
