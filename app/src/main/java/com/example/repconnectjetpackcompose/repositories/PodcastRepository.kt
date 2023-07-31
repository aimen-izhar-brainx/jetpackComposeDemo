package com.example.repconnectjetpackcompose.repositories

import Podcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.*
import java.net.URL
import java.net.URLConnection
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class PodcastRepository {
    fun getPodcastList(url : String, data: (ArrayList<Podcast>?) -> Unit) {
        val itemList: ArrayList<Podcast> = ArrayList()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL(url)
                val conn: URLConnection =
                    url.openConnection()
                val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
                val builder: DocumentBuilder = factory.newDocumentBuilder()
                val doc: Document =
                    builder.parse(conn.getInputStream())
                val nodes: NodeList = doc.getElementsByTagName("item")
                for (i in 0 until nodes.length) {
                    val podcast = Podcast()
                    val element: Element = nodes.item(i) as Element
                    val mTitle = (((element.getElementsByTagName("title")
                        .item(0) as Element).firstChild as Text).data)
                    val mDescription = (((element.getElementsByTagName("description")
                        .item(0) as Element).firstChild as Text).data)
                    val mImage =  (element.getElementsByTagName("itunes:image")
                        .item(0).attributes.item(0) as Attr).value
                    val mDate = (((element.getElementsByTagName("pubDate")
                        .item(0) as Element).firstChild as Text).data)
                    val mLink = (element.getElementsByTagName("enclosure")
                        .item(0) as Element).getAttribute("url")
                    val mDuration = (element.getElementsByTagName("itunes:duration").item(0).firstChild as Text).data
                    podcast.apply {
                        image = mImage
                        link = mLink
                        title = mTitle
                        pubDate = mDate
                        duration = mDuration
                        description = mDescription
                        itemList.add(this)
                    }
                }
                data.invoke(itemList)
            } catch (e: Exception) {
                data.invoke(null)
                e.printStackTrace()
            }
        }
    }

}