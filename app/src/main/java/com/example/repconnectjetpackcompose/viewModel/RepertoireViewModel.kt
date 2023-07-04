package com.example.repconnectjetpackcompose.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repconnectjetpackcompose.models.MagazineYear
import com.example.repconnectjetpackcompose.models.MagazinesIssues
import com.example.repconnectjetpackcompose.repositories.RepertoireRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepertoireViewModel :ViewModel() {
    private var repertoireRepository = RepertoireRepository()
    var magList : MutableLiveData<List<MagazineYear>> = MutableLiveData()

    fun getRepertoireMagazines(data: (List<MagazineYear>?) -> Unit){
        viewModelScope.launch(Dispatchers.IO) {
            repertoireRepository.getRepertoireMagazineYears {
                magList.postValue(it)
                data.invoke(it)
            }
        }
    }
    fun getRepertoireMagazineYear(year:String, data: (List<MagazinesIssues>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            repertoireRepository.getRepertoireMagazineByYear(year) {
                data.invoke(it)
            }
        }
    }
}