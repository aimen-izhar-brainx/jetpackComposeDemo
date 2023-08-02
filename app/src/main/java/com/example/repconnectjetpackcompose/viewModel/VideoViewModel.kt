package com.example.repconnectjetpackcompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repconnectjetpackcompose.models.MdsVideo
import com.example.repconnectjetpackcompose.repositories.RepertoireRepository
import com.example.repconnectjetpackcompose.repositories.VideoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VideoViewModel :ViewModel() {
    private var videoRepository = VideoRepository()
    fun getVideos(data: (List<MdsVideo>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getMdsVideos {
                data.invoke(it)
            }
        }
    }

    fun getCustomerVideos(data: (List<MdsVideo>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            videoRepository.getCustomerVideos {
                data.invoke(it)
            }
        }
    }
}