package com.trup10ka.attendoo.api.department

import com.trup10ka.attendoo.ALL_DEPARTMENTS_ENDPOINT
import com.trup10ka.attendoo.api.attendooRole
import com.trup10ka.attendoo.api.attendooUsername
import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.DepartmentDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllDepartments()
{
    get(ALL_DEPARTMENTS_ENDPOINT)
    {
        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, "No JWT token provided")
            return@get
        }

        val role = principal.attendooRole
        if (!role.equals("admin", ignoreCase = true))
        {
            call.respond(HttpStatusCode.Forbidden, "Only admins can access this endpoint")
            return@get
        }
        
        val username = principal.attendooUsername
        
        val departments = dbQuery {
            val user = User.find { 
                Users.attendooUsername eq username 
            }.firstOrNull()
            
            user?.getAllDepartments()?.map {
                DepartmentDTO(it.id.value, it.name)
            } ?: emptyList()
        }

        call.respond(HttpStatusCode.OK, departments)
    }
}
