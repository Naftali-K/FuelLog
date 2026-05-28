package com.example.fuellog

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fuellog.viewModels.ChartFuelConsumptionViewModel
import com.example.fuellog.views.FuelConsumptionChartView
import kotlinx.coroutines.launch

class ChartFuelConsumptionActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TRANSPORT_ID = "EXTRA_TRANSPORT_ID"
    }

    private lateinit var backBtn: ImageView
    private lateinit var fuelChartView: FuelConsumptionChartView
    private lateinit var viewModel: ChartFuelConsumptionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart_fuel_consumption)

        // Инициализация ViewModel (используем стандартный провайдер для совместимости)
        viewModel = ViewModelProvider(this)[ChartFuelConsumptionViewModel::class.java]

        setReferences()
        setupListeners()
        observeViewModel()

        // Получаем ID транспорта и загружаем данные
        val transportIdString = intent.getStringExtra(EXTRA_TRANSPORT_ID)
        transportIdString?.toIntOrNull()?.let { id ->
            viewModel.loadData(id)
        }
    }

    private fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
        fuelChartView = findViewById(R.id.fuel_chart_view)
    }

    private fun setupListeners() {
        backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun observeViewModel() {
        // Подписка на StateFlow с учетом жизненного цикла
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.fuelData.collect { data ->
                    if (data.isNotEmpty()) {
                        fuelChartView.setData(data)
                    }
                }
            }
        }
    }
}
