package com.example.repconnectjetpackcompose.models

import com.example.repconnectjetpackcompose.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){

    object Home : BottomNavItem("Home", R.drawable.ic_home_selected,"home")
    object Repertoire: BottomNavItem("Repertoire",R.drawable.ic_repertoire_unselected,"repertoire")
    object Manufacturer: BottomNavItem("Manufacturers",R.drawable.ic_icon_manufactuters,"manufacturer")
    object Podcast: BottomNavItem("Podcast",R.drawable.ic_icon_podcast,"podcast")
    object Video: BottomNavItem("Video",R.drawable.ic_icon_video,"video")
}