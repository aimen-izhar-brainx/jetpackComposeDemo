package com.example.repconnectjetpackcompose.repositories

import com.example.repconnectjetpackcompose.models.HomeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList
import org.w3c.dom.Text
import java.net.URL
import java.net.URLConnection
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class HomeRepository {
    fun getHomeFeeds(url: String, data: (ArrayList<HomeItem>?) -> Unit) {
        val itemList: ArrayList<HomeItem> = ArrayList()
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
                    val homeItem = HomeItem()
                    val element: Element = nodes.item(i) as Element
                    val title = (((element.getElementsByTagName("title")
                        .item(0) as Element).firstChild as Text).data)
                    val image = (element.getElementsByTagName("media:content")
                        .item(0) as Element).getAttribute("url")
                    val date = (((element.getElementsByTagName("pubDate")
                        .item(0) as Element).firstChild as Text).data)
                    val link = (((element.getElementsByTagName("link")
                        .item(0) as Element).firstChild as Text).data)
                    homeItem.image = image
                    homeItem.link = link
                    homeItem.title = title
                    homeItem.pubDate = date
                    itemList.add(homeItem)
                }
                data.invoke(itemList)
            } catch (e: Exception) {
                data.invoke(null)
                e.printStackTrace()
            }
        }
    }
}