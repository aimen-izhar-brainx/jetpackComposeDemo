package com.example.repconnectjetpackcompose.repositories

import com.example.repconnectjetpackcompose.models.MagazineYear
import com.example.repconnectjetpackcompose.models.MagazinesIssues
import com.example.repconnectjetpackcompose.utils.Utils.downloadXml
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepertoireRepository {
    fun getRepertoireMagazineYears(data: (List<MagazineYear>?) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mXml = downloadXml("https://feeds.repertoireconnect.com/api/MagazineYears")
                var jsonString = Gson().fromJson(mXml, Array<MagazineYear>::class.java).asList()
                data.invoke(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                data.invoke(null)
            }
        }
    }

    fun getRepertoireMagazineByYear(year:String,data: (List<MagazinesIssues>?) -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val mXml = downloadXml("https://feeds.repertoireconnect.com/api/RepertoireIssues/$year")
                var jsonString = Gson().fromJson(mXml, Array<MagazinesIssues>::class.java).asList()
                data.invoke(jsonString)
            } catch (e: Exception) {
                e.printStackTrace()
                data.invoke(null)
            }
        }
    }
}