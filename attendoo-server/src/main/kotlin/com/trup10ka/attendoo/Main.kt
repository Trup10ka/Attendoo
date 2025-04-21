package com.trup10ka.attendoo

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trup10ka.attendoo.config.ConfigDistributor.config
import java.util.Date

fun main()
{
    val attendoo = Attendoo()
    attendoo.init()
    attendoo.startApp()
}
