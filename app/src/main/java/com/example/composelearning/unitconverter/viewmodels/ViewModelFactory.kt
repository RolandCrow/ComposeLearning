package com.example.composelearning.unitconverter.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.composelearning.unitconverter.Repository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: Repository):
    ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        if(modelClass.isAssignableFrom(TemperatureViewModel::class.java))
            TemperatureViewModel(repository) as T
        else
            DistanceViewModel(repository) as T
}