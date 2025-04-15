package com.trup10ka.attendoo.db

import kotlin.reflect.full.primaryConstructor
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(database: Database? = null, transaction: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, db = database, statement = transaction)

/**
 * General toDTO function for converting IntEntity to DTO.
 *
 * Note that this only works on IntEntities that are not referenced by other entities or are referring to other entities.
 *
 * @return DTO object of type `T` with parsed values from DAO
 *
 * Object of type `T` with `null` values
 */
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
