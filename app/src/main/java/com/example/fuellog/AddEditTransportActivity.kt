package com.example.fuellog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fuellog.dialogs.AccessDialog
import com.example.fuellog.interfaces.AccessCanselListener
import com.example.fuellog.models.Transport
import com.example.fuellog.viewModels.AddEditTransportViewModel

class AddEditTransportActivity : AppCompatActivity() {

    companion object {
        val EXTRA_MANE: String = "transport_id"
        val RESULT_UPDATED: Int = -2
        val RESULT_DELETE: Int = -3
    }

    private val TAG: String = "Test_code"

    lateinit var backBtn: ImageView
    lateinit var pageTitleTv: TextView
    lateinit var nameEt: EditText
    lateinit var companyEt: EditText
    lateinit var modelEt: EditText
    lateinit var yearEt: EditText
    lateinit var descriptionEt: EditText
    lateinit var addEditBtn: Button
    lateinit var deleteBtn: Button
    var transportId: String? = null
    var isUpdate: Boolean = false

    lateinit var viewModel: AddEditTransportViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit_transport)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setReferences()
        setViewModel()

        transportId = intent.getStringExtra(EXTRA_MANE)
        Log.d(TAG, "onCreate: ExtraString value: $transportId")
        if (!transportId.equals("") && transportId != null) {
            setValuesToViews()
            isUpdate = true
        }

        backBtn.setOnClickListener { view ->
            setUnSuccessResult()
        }

        addEditBtn.setOnClickListener { view ->
            inputValidation()
        }

        deleteBtn.setOnClickListener { view ->
            openAccessDialog()
        }
    }

    fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
        pageTitleTv = findViewById(R.id.page_title_tv)
        nameEt = findViewById(R.id.name_et)
        companyEt = findViewById(R.id.company_et)
        modelEt = findViewById(R.id.model_et)
        yearEt = findViewById(R.id.year_et)
        descriptionEt = findViewById(R.id.description_et)
        addEditBtn = findViewById(R.id.add_edit_btn)
        deleteBtn = findViewById(R.id.delete_btn)
    }

    fun setViewModel() {
        viewModel = ViewModelProvider(this).get(AddEditTransportViewModel::class.java)

        viewModel.isFocusNameEt().observe(this, Observer<Boolean> { item ->
            if (item) {
                nameEt.requestFocus()
            }
        })
        viewModel.isFocusCompanyEt().observe(this, Observer<Boolean> {
            if (it) {
                companyEt.requestFocus()
            }
        })
        viewModel.isFocusModelEt().observe(this, Observer<Boolean> {
            if (it) {
                modelEt.requestFocus()
            }
        })

        viewModel.isValidThisInput().observe(this, Observer<Transport?> {
            if (it == null) {
                return@Observer
            }

            if (isUpdate) {
                updateTransport(it)
                return@Observer
            }

            addNewTransport(it)
        })

        viewModel.isAddedUpdatedThisNewTransport().observe(this, Observer<Boolean> { item ->
            if (!item) {
                Log.d(TAG, "New transport not added")
                return@Observer
            }

            Log.d(TAG, "setViewModel: ADDED new transport")
            setSuccessResult()
        })

        viewModel.thisTransport().observe(this, Observer<Transport?> { item ->
            if (item == null) {
                Toast.makeText(baseContext, "Transport NOT Founded", Toast.LENGTH_SHORT).show()
                backPress()
                return@Observer
            }

            item.name?.let {
                nameEt.setText(it)
            }
            companyEt.setText(item.company ?: "")
            modelEt.setText(item.model ?: "")
            yearEt.setText(item.year.toString() ?: "")

            item.description?.let {
                descriptionEt.setText(it)
            }
        })

        viewModel.isThisTransportUpdated().observe(this, Observer<Boolean> {
            if (!it) {
                return@Observer
            }

            Log.d(TAG, "setViewModel: Updated")
            setResult(RESULT_UPDATED)
            backPress()
//            finish()
        })

        viewModel.isThisTransportDeleted().observe(this, Observer<Boolean> {
            if (!it) {
                return@Observer
            }

            Log.d(TAG, "setViewModel: Deleted")
            setResult(RESULT_DELETE)
            backPress()
        })


    }

    fun setValuesToViews() {
        Log.d(TAG, "setValuesToViews: Set values of transport ID: $transportId")
        pageTitleTv.text = getString(R.string.edit_transport)
        addEditBtn.text = getString(R.string.update)
        deleteBtn.visibility = View.VISIBLE

        viewModel.getThisTransport(transportId)
    }

    fun inputValidation() {
        val nameTransport = nameEt.text.toString()
        val companyName = companyEt.text.toString()
        val modelTransport = modelEt.text.toString()
        val yearMadeString = yearEt.text.toString()
        val descriptionTransport = descriptionEt.text.toString()

        viewModel.inputValidation(nameTransport, companyName, modelTransport, yearMadeString, descriptionTransport)
    }

    fun addNewTransport(transport: Transport) {
        Log.d(TAG, "addNewTransport: Add new Transport")
        viewModel.addNewTransport(transport)
    }

    fun updateTransport(transport: Transport) {
        Log.d(TAG, "addNewTransport: Update Transport")
        viewModel.updateThisTransport(transportId, transport)
    }

    fun setUnSuccessResult() {
        val resultIntent = Intent()
        resultIntent.putExtra("TEST_VALUE", "Hello from AddEditTransport, result Unsuccess")
        setResult(RESULT_CANCELED, resultIntent)

        backPress()
    }

    fun setSuccessResult() {
        val resultIntent = Intent()
        resultIntent.putExtra("TEST_VALUE", "Hello from AddEditTransport, result Success")
        setResult(RESULT_OK, resultIntent)

        backPress()
    }

    fun openAccessDialog() {
        val dialog = AccessDialog(R.string.are_you_sure_you_want_to_delete_this, object : AccessCanselListener {
            override fun access() {
                viewModel.deleteThisTransport(transportId)
            }

            override fun cansel() {

            }
        })

        dialog.show(supportFragmentManager, AccessDialog.DIALOG_TAG)
    }

    fun backPress() {
        onBackPressedDispatcher.onBackPressed()
        finish()
    }
}