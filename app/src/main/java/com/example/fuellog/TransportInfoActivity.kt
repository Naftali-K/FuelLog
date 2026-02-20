package com.example.fuellog

import android.graphics.Typeface
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fuellog.fragments.FuelFragment
import com.example.fuellog.fragments.OtherFragment
import com.example.fuellog.models.Transport
import com.example.fuellog.viewModels.TransportInfoViewModel

class TransportInfoActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MANE: String = "transport_id"
    }

    private lateinit var backBtn: ImageView
    private lateinit var transportNameTv: TextView
    private lateinit var transportCompanyTv: TextView
    private lateinit var transportModelTv: TextView
    private lateinit var transportYearTv: TextView
    private lateinit var transportDescriptionTv: TextView
    private lateinit var fuelCategoryTv: TextView
    private lateinit var otherCategoryTv: TextView
    private var frameLayout: Int = 0

    private var transportId: String? = null
    private lateinit var viewModel: TransportInfoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_transport_info)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        transportId = intent.getStringExtra(EXTRA_MANE)
        if (transportId == null) {
            backPress()
            return
        }

        setReferences()
        setViewModel()
        getCurrentTransport()
        setFuelFragment()

        backBtn.setOnClickListener { view ->
            backPress()
        }

        fuelCategoryTv.setOnClickListener { view ->
            setFuelFragment()
        }

        otherCategoryTv.setOnClickListener { view ->
            setOtherFragment()
        }
    }

    fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
        transportNameTv = findViewById(R.id.transport_name_tv)
        transportCompanyTv = findViewById(R.id.transport_company_tv)
        transportModelTv = findViewById(R.id.transport_model_tv)
        transportYearTv = findViewById(R.id.transport_year_tv)
        transportDescriptionTv = findViewById(R.id.transport_description_tv)
        fuelCategoryTv = findViewById(R.id.fuel_category_tv)
        otherCategoryTv = findViewById(R.id.other_category_tv)
        frameLayout = R.id.frame_layout
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TransportInfoViewModel::class.java)
        viewModel.initViewModel(baseContext)

        viewModel.thisTransport().observe(this, Observer<Transport?> { item ->
            if (item == null) {
                backPress()
                return@Observer
            }

            setTransportValues(item)
        })
    }

    private fun getCurrentTransport() {
        viewModel.getThisTransport(transportId)
    }

    private fun setTransportValues(transport: Transport?) {
        transport?.name.let {
            transportNameTv.text = it
        }

        transport?.company.let {
            transportCompanyTv.text = it
        }

        transport?.model.let {
            transportModelTv.text = it
        }

        transport?.year.let {
            transportYearTv.text = it.toString()
        }

        transport?.description.let {
            transportDescriptionTv.text = it
        }
    }

    private fun setFuelFragment() {

        val fragment = FuelFragment()
        val bundle = Bundle().apply {
            putString(FuelFragment.TRANSPORT_ID, transportId)
        }
        fragment.arguments = bundle

        fragmentReplace(fragment, FuelFragment.FRAGMENT_TAG)

        setActive(fuelCategoryTv, true)
        setActive(otherCategoryTv, false)
    }

    private fun setOtherFragment() {
        val fragment = OtherFragment()
        val bundle = Bundle().apply {
            putString(OtherFragment.TRANSPORT_ID, transportId)
        }
        fragment.arguments = bundle

        fragmentReplace(fragment, OtherFragment.FRAGMENT_TAG)

        setActive(fuelCategoryTv, false)
        setActive(otherCategoryTv, true)
    }

    private fun setActive(textView: TextView, active: Boolean) {
        if (active) {
            textView.background = getDrawable(R.drawable.style_category_selected)
            textView.setTypeface(null, Typeface.BOLD)
        } else {
            textView.background = getDrawable(R.drawable.style_category_not_selected)
            textView.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun fragmentReplace(fragment: Fragment, fragmentTag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(frameLayout, fragment, fragmentTag).commit()
    }

    fun backPress() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }
}