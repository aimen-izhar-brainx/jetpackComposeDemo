package com.example.repconnectjetpackcompose.repositories

import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.utils.Utils.downloadXml
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManufacturerRepository {
    fun getManufacturerList(data: (List<Manufacturer>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mXml = downloadXml("https://feeds.repertoireconnect.com/api/ManufacturerList")
                var jsonString = Gson().fromJson(mXml, Array<Manufacturer>::class.java).asList()
                data.invoke(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                data.invoke(null)
            }
        }
    }
}