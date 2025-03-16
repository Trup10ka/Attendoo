package com.trup10ka.attendoo.db.client

import com.trup10ka.attendoo.config.ConfigDistributor.config
import com.trup10ka.attendoo.db.services.ProposalExposedService
import com.trup10ka.attendoo.db.services.RoleExposedService
import com.trup10ka.attendoo.db.services.TagExposedService
import com.trup10ka.attendoo.db.services.UserAttendanceExposedService
import com.trup10ka.attendoo.db.services.UserDepartmentExposedService
import com.trup10ka.attendoo.db.services.UserExposedService
import com.trup10ka.attendoo.db.services.UserStatusExposedService
import com.trup10ka.attendoo.db.tables.UserAttendances
import com.trup10ka.attendoo.db.tables.UserDepartments
import com.trup10ka.attendoo.db.tables.UserStatuses
import com.trup10ka.attendoo.db.tables.Users
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
            url = "${config.database.url}:${config.database.port}/${config.database.name}",
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
        userStatusService = UserStatusExposedService()
        tagService = TagExposedService()
        userDepartmentService = UserDepartmentExposedService()
        roleService = RoleExposedService()
        proposalService = ProposalExposedService(userStatusService)
        userService = UserExposedService(roleService, userStatusService, userDepartmentService)
        attendanceService = UserAttendanceExposedService(userStatusService, userService)
        
        checkIfAllServicesInitialized()
    }

    private fun initSchema()
    {
        transaction {
            SchemaUtils.create(
                Roles,
                Tags,
                UserStatuses,
                UserDepartments,
                Users,
                UserAttendances,
                Proposals,
                ProposalTags
            )
            
        }
        logger.info { "Schema initialized" }
    }
}
