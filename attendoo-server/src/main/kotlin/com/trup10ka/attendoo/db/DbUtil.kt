package com.trup10ka.attendoo.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

suspend fun <T> dbQuery(database: Database? = null, transaction: suspend Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, db  = database, statement = transaction)
