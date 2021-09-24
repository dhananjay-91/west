package com.example.myapplication.data.remote

import android.util.Log
import com.example.myapplication.data.parser.BaseDefaultHandler
import com.example.myapplication.data.parser.GetSongsListHandler
import com.example.myapplication.data.parser.response.GetTopSongsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.lang.Exception
import javax.xml.parsers.SAXParserFactory

/**
 * Created by Dhananjay on 7/17/2020
 */
class ApiServiceImpl : ApiService {

    companion object {
        const val TAG = "ApiServiceImpl";
    }

    private val serviceParser = ServiceParser()
    override suspend fun getTopSongs(): GetTopSongsResponse {
        var response: GetTopSongsResponse? = null
        withContext(Dispatchers.IO)
        {
            val url = EndPoint.getSongUrl()

            Log.d(TAG, "------- GetStoreList Request-------------")
            Log.d(TAG, "#### url: $url")

            var result = ""
            try {
                result = serviceParser.makeHttpRequest(
                    url,
                    "GET",
                    null,
                    null,
                    null
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.d(TAG, "------- GetStoreList Response-------------")
            Log.d(TAG, "#### response: $result")


            // parsing the response
            Log.d(TAG, "------Parsing------")
            response = getParsingResponse(result, GetSongsListHandler)
            Log.d(TAG, "#### parsed result: $response")

        }
        return response!!;
    }

    private fun <T : Any> getParsingResponse(response: String, handler: BaseDefaultHandler): T {
        val spf =
            SAXParserFactory.newInstance()
        val sp = spf.newSAXParser()
        val xr = sp.xmlReader

        // Create handler to handle XML Tags ( extends DefaultHandler )

        xr.contentHandler = handler
        val inputStream =
            ByteArrayInputStream(response.toByteArray())
        xr.parse(InputSource(inputStream))

        return handler.getResponse()
    }

}