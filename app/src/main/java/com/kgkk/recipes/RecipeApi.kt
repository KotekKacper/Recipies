package com.kgkk.recipes

import android.os.AsyncTask
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL

class RecipeApi : AsyncTask<String, Void, String>() {

    private var response: String? = null

    override fun doInBackground(vararg urls: String): String {
        val url = URL(urls[0])
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.setRequestProperty("X-Api-Key", "ujmK0BnoO4njw07+yQ3e0w==Fvj7Xv5sMja1JNEi")

        val responseCode = connection.responseCode
        val inputStream = connection.inputStream
        val response = inputStream.bufferedReader().use { it.readText() }

        connection.disconnect()
        inputStream.close()

        return response
    }

    override fun onPostExecute(result: String) {
        this.response = result
        Log.i("API output", result)
    }
}
