package com.example.repconnectjetpackcompose.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.repconnectjetpackcompose.models.Manufacturer
import com.example.repconnectjetpackcompose.repositories.ManufacturerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ManufacturerViewModel :ViewModel() {
    private var manufacturerRepository : ManufacturerRepository = ManufacturerRepository()
    fun getManufacturerList(data: (List<Manufacturer>?) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            manufacturerRepository.getManufacturerList {
                data.invoke(it)
            }
        }
    }

}