package com.trup10ka.attendoo.util

import com.trup10ka.attendoo.pages.constant.StyleClass

fun <K, V> MutableMap<K, V>.addAll(vararg pairs: Pair<K, V>)
{
    for (pair in pairs)
    {
        this[pair.first] = pair.second
    }
}

fun stylesOf(vararg styleClasses: StyleClass): Array<String>
{
    val styleClassNames = mutableListOf<String>()
    styleClasses.forEach {
        styleClassNames.add(it.toString())
    }
    return styleClassNames.toTypedArray()
}
