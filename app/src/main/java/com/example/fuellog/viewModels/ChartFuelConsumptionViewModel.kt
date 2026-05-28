package com.example.fuellog.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.FuelLogRepository
import com.example.fuellog.models.FuelConsumption
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChartFuelConsumptionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FuelLogRepository
    private val _fuelData = MutableStateFlow<List<FuelConsumption>>(emptyList())
    val fuelData: StateFlow<List<FuelConsumption>> = _fuelData.asStateFlow()

    init {
        val dao = ApplicationDataBase.getInstance(application).fuelConsumptionDAO()
        repository = FuelLogRepository(dao)
    }

    fun loadData(transportId: Int) {
        viewModelScope.launch {
            _fuelData.value = repository.getFuelConsumptionAsc(transportId)
        }
    }
}