package com.example.repconnectjetpackcompose.repositories

import com.example.repconnectjetpackcompose.models.MdsVideo
import com.example.repconnectjetpackcompose.utils.Utils.downloadXml
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoRepository {
    fun getMdsVideos(data: (List<MdsVideo>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mXml = downloadXml("https://feeds.repertoireconnect.com/api/Drills")
                var jsonString = Gson().fromJson(mXml, Array<MdsVideo>::class.java).asList()
                data.invoke(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                data.invoke(null)
            }
        }
    }

    fun getCustomerVideos(data: (List<MdsVideo>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mXml = downloadXml("https://feeds.repertoireconnect.com/api/EndUser")
                var jsonString = Gson().fromJson(mXml, Array<MdsVideo>::class.java).asList()
                data.invoke(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                data.invoke(null)
            }
        }
    }
}