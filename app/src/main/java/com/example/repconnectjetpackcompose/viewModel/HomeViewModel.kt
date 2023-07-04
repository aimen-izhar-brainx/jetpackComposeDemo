package com.example.repconnectjetpackcompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repconnectjetpackcompose.models.HomeItem
import com.example.repconnectjetpackcompose.repositories.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val homeRepository = HomeRepository()
    fun getHomeFeed(url:String, data: (ArrayList<HomeItem>?) -> Unit) {
        //showProcessingLoader()
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.getHomeFeeds(url) {
                //hideProcessingLoader()
                data.invoke(it)
            }
        }
    }

}