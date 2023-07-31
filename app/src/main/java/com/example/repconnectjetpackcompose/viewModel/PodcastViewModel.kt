package com.example.repconnectjetpackcompose.viewModel

import Podcast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repconnectjetpackcompose.repositories.PodcastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PodcastViewModel : ViewModel() {
    private var podcastRepository : PodcastRepository = PodcastRepository()
    fun getPodcastList(url:String,data: (List<Podcast>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            podcastRepository.getPodcastList(url) {
                data.invoke(it)
            }
        }
    }

}