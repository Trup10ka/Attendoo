package com.trup10ka.attendoo.db

import kotlin.reflect.full.primaryConstructor
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(database: Database? = null, transaction: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, db = database, statement = transaction)

inline fun <reified T : Any> IntEntity.toDTO(): T
{
    val constructor = T::class.primaryConstructor
        ?: throw IllegalArgumentException("Class ${T::class} must have a primary constructor")
    
    val properties = this::class.members
        .filter { it.parameters.isEmpty() }
        .associateBy { it.name }
        .mapValues { it.value.call(this) }
    
    return constructor.callBy(constructor.parameters.associateWith { properties[it.name] })
}
