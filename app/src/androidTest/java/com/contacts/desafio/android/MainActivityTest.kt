package com.contacts.desafio.android

import androidx.test.platform.app.InstrumentationRegistry
import com.contacts.desafio.android.data.remote.RetrofitInstance
import com.list.desafio.android.R
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val server = MockWebServer()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        server.start(SERVER_PORT)
        RetrofitInstance.baseUrl = server.url("/").toString()
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun shouldDisplayListItem() {
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(loadMockResponse())
        )

        mainActivityRobot {
            launch()

            server.takeRequest()

            checkTitleIsDisplayed(context.getString(R.string.title))
            checkItem(name = "Michelle Brito", username = "@michelle.brito", position = 0)
        }
    }

    private fun loadMockResponse(): String {
        val fileName = "mock_response.json"
        return javaClass.classLoader?.getResource(fileName)?.readText()
            ?: throw IllegalArgumentException("Arquivo de resposta não encontrado: $fileName")
    }

    companion object {
        private const val SERVER_PORT = 8080
    }
}
