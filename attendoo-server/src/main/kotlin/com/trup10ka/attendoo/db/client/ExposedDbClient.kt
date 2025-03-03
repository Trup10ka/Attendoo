package com.trup10ka.attendoo.db.client

import com.trup10ka.attendoo.config.ConfigDistributor.config
import com.trup10ka.attendoo.db.dao.ProposalTag
import com.trup10ka.attendoo.db.services.ExposedEmployeeService
import com.trup10ka.attendoo.db.tables.EmployeeAttendances
import com.trup10ka.attendoo.db.tables.EmployeeDepartments
import com.trup10ka.attendoo.db.tables.EmployeeStatuses
import com.trup10ka.attendoo.db.tables.Employees
import com.trup10ka.attendoo.db.tables.ProposalTags
import com.trup10ka.attendoo.db.tables.Proposals
import com.trup10ka.attendoo.db.tables.Roles
import com.trup10ka.attendoo.db.tables.Tags
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

private val logger = KotlinLogging.logger {}

class ExposedDbClient : DbClient()
{
    override fun connect()
    {
        Database.connect(
            url = "${config.database.url}/${config.database.name}",
            driver = config.database.driver,
            user = config.database.user,
            password = config.database.password,
        )
        testConnection()
    }

    private fun testConnection()
    {
        transaction {
            addLogger(StdOutSqlLogger)
            !connection.isClosed
        }
        logger.info { "Test connection to DB succeeded" }
    }

    override fun init()
    {
        initClient()
        initSchema()
    }

    private fun initClient()
    {
        userService = ExposedEmployeeService()
    }

    private fun initSchema()
    {
        transaction {
            SchemaUtils.create(
                Roles,
                Tags,
                EmployeeStatuses,
                EmployeeDepartments,
                Employees,
                EmployeeAttendances,
                Proposals,
                ProposalTags
            )
            
        }
        logger.info { "Schema initialized" }
    }
}
