package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.FULL_ALL_USERS_ENDPOINT
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.UsersPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.pages.constant.ElementID.MAIN
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class UsersPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = UsersPageBuilderImp()

    private val users = mutableListOf<User>()

    override fun init()
    {
        launchDefaultCoroutine {
            val fetchedUsers = httpClient.getVia(FULL_ALL_USERS_ENDPOINT) as HttpResponse

            val responseText = fetchedUsers.bodyAsText()
            val parsedUsers: List<User> = Json.decodeFromString(responseText)
            users.clear()
            users.addAll(parsedUsers)
            
            users.forEach { user ->
                pageBuilder.buildUserContainer(user)
            }
        }
    }

    override fun show()
    {
        pageBuilder.buildDynamicElement(getDivByID(MAIN))

        init()
    }

    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
}
