package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.FULL_ALL_REQUESTS_ENDPOINT
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.RequestPageBuildImp
import com.trup10ka.attendoo.pages.constant.ElementID.MAIN
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.pages.constant.StyleClass.CENTER
import com.trup10ka.attendoo.pages.constant.StyleClass.INNER_CONTAINER
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createHeader
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class RequestsPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = RequestPageBuildImp()

    private val requests = mutableListOf<Request>()

    override fun init()
    {
        launchDefaultCoroutine {
            try {
                val fetchedRequests = httpClient.getVia(FULL_ALL_REQUESTS_ENDPOINT) as HttpResponse

                val responseText = fetchedRequests.bodyAsText()
                val parsedRequests: List<Request> = Json.decodeFromString(responseText)
                requests.clear()
                requests.addAll(parsedRequests)

                val mainContainer = getDivByID(MAIN)

                if (requests.isEmpty()) {
                    val messageContainer = createHeader(
                        clazz = stylesOf(INNER_CONTAINER, CENTER),
                        text = "No requests found"
                    )

                    mainContainer?.appendChild(messageContainer)
                } else {
                    requests.forEach { request ->
                        pageBuilder.buildRequestContainer(mainContainer, request)
                    }
                }
            } catch (e: Exception) {
                console.error("Error fetching requests: ${e.message}")
            }
        }
    }

    override fun show()
    {
        init()
    }

    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
}
