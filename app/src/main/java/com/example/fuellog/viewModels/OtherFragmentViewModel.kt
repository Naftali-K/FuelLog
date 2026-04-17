package com.example.fuellog.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fuellog.DBRoom.ApplicationDataBase
import com.example.fuellog.DBRoom.OtherExpensesDAO
import com.example.fuellog.models.OtherExpenses
import kotlinx.coroutines.launch

/**
 * @Author: naftalikomarovski
 * @Date: 2026/04/14
 */
class OtherFragmentViewModel: ViewModel() {

    private val TAG: String = "Test_code"
    private lateinit var context: Context
    private lateinit var otherExpensesDAO: OtherExpensesDAO

    fun initViewModel(context: Context) {
        this.context = context
        otherExpensesDAO = ApplicationDataBase.getInstance(context).otherExpensesDAO()
    }




    private val  otherExpensesCurrentTransport: MutableLiveData<List<OtherExpenses>> = MutableLiveData()
    fun thisTransportOtherExpenses(): LiveData<List<OtherExpenses>> {
        return otherExpensesCurrentTransport
    }

    fun getThisTransportOtherExpenses(id: String?) {
        id ?: return

        val idLong = id.toLong()

        viewModelScope.launch {
            val otherExpensesList = otherExpensesDAO.getTransportOtherExpenses(idLong)

            if (otherExpensesList == null) {
                return@launch
            }

            otherExpensesCurrentTransport.value = otherExpensesList
        }
    }




    private val isAddedNewOtherExpenses: MutableLiveData<Boolean> = MutableLiveData()
    fun isAddedOtherExpenses(): LiveData<Boolean> {
        return isAddedNewOtherExpenses
    }

    fun addThisTransportOtherExpenses(otherExpenses: OtherExpenses) {
        viewModelScope.launch {
            val itemID = otherExpensesDAO.addTransportOtherExpenses(otherExpenses)

            if (itemID < 0) {
                return@launch
            }

            isAddedNewOtherExpenses.value = true
        }
    }
}