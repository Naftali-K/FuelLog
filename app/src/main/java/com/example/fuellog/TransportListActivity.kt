package com.example.fuellog

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.fuellog.adapters.TransportRecyclerViewAdapter
import com.example.fuellog.dialogs.AccessDialog
import com.example.fuellog.dialogs.AdapterActionsBottomSheetDialog
import com.example.fuellog.interfaces.AccessCanselListener
import com.example.fuellog.interfaces.AdapterActionListener
import com.example.fuellog.interfaces.AdapterActionMenuListener
import com.example.fuellog.models.Transport
import com.example.fuellog.viewModels.TransportListViewModel

class TransportListActivity : AppCompatActivity(), AdapterActionListener {

    private val TAG: String = "Test_code"

    lateinit var viewModel: TransportListViewModel

    lateinit var backBtn: ImageView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var transportRecyclerView: RecyclerView
    lateinit var addBtn: ImageView
    lateinit var adapter: TransportRecyclerViewAdapter
    lateinit var adapterActionsBottomSheetDialog: AdapterActionsBottomSheetDialog

    val addEditTransportActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "Activity Result: Update successful, add new transport")
            Toast.makeText(baseContext, "Successful added new transport", Toast.LENGTH_SHORT).show()
            viewModel.getTransportList()
            return@registerForActivityResult
        }

        if (result.resultCode == AddEditTransportActivity.RESULT_UPDATED) {
            Toast.makeText(baseContext, "Successful update information of transport", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }

        if (result.resultCode == AddEditTransportActivity.RESULT_DELETE) {
            Toast.makeText(baseContext, "Successful DELETE transport", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        }

        if (result.resultCode == RESULT_CANCELED) {
            Log.d(TAG, "Activity Result: NOT updates")

            val data: Intent? = result.data
            val resultMessage = data?.getStringExtra("TEST_VALUE")
            Log.d(TAG, "Activity Result: Result message: $resultMessage")
            return@registerForActivityResult
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_transport_list)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
        setViewModel()
        setReferences()
        setAdapter()

        backBtn.setOnClickListener { view ->
            backPress()
        }

        swipeRefreshLayout.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            viewModel.getTransportList()
        })

        addBtn.setOnClickListener { view ->
//            startActivity(Intent(this, AddEditTransportActivity::class.java))

            addEditTransportActivityResultLauncher.launch(Intent(baseContext, AddEditTransportActivity::class.java))
        }
    }

    fun setViewModel() {
        viewModel = ViewModelProvider(this).get(TransportListViewModel::class.java)
        viewModel.initViewModel(this)

        viewModel.thisTransportList().observe(this, Observer<List<Transport>> {

            swipeRefreshLayout.isRefreshing = false

            if (it == null) {
                return@Observer
            }

            Log.d(TAG, "setViewModel: Transport List: $it")

            adapter.setTransportList(it)
            adapter.notifyDataSetChanged()
        })

        viewModel.isThisTransportDeleted().observe(this, Observer<Boolean> {
            if (!it) {
                return@Observer
            }

            Toast.makeText(baseContext, "Successful DELETE transport", Toast.LENGTH_SHORT).show()
            adapter.notifyDataSetChanged()
        })
    }

    fun setReferences() {
        backBtn = findViewById(R.id.back_btn)
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        transportRecyclerView = findViewById(R.id.transport_recycler_view)
        addBtn = findViewById(R.id.add_btn)
    }

    fun setAdapter() {
        adapter = TransportRecyclerViewAdapter(baseContext, this)

        transportRecyclerView.adapter = adapter

        viewModel.getTransportList()
    }

    fun openAccessDialog(id: Int) {

        val dialog = AccessDialog(R.string.are_you_sure_you_want_to_delete_this, object : AccessCanselListener {
            override fun access() {
                viewModel.deleteThisTransport(id.toString())
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



    // TODO: Overrides -----------------------------------------------------------------------------
    override fun openItemIdInt(id: Int) {
        Log.d(TAG, "openItemIdInt: Open transport in Index: $id")
        val intent = Intent(baseContext, TransportInfoActivity::class.java)
            intent.putExtra(TransportInfoActivity.EXTRA_MANE, id.toString())

        startActivity(intent)
    }

    override fun openItemIdString(id: String) {
//        TODO("Not yet implemented")
    }

    override fun openItemIntBottomSheetDialog(id: Int) {
        Log.d(TAG, "openItemIntBottomSheetDialog: Open transport action menu for Index: $id")

        adapterActionsBottomSheetDialog = AdapterActionsBottomSheetDialog(object : AdapterActionMenuListener {
            override fun editItemId() {
                Log.d(TAG, "editItemIdInt: Edit item ID: $id")
                val intent = Intent(baseContext, AddEditTransportActivity::class.java)
                    intent.putExtra(AddEditTransportActivity.EXTRA_MANE, id.toString())

                addEditTransportActivityResultLauncher.launch(intent)

//                startActivity(intent)
            }

            override fun deleteItemId() {

                Log.d(TAG, "deleteItemIdInt: Delete Item ID: $id")

                openAccessDialog(id)
            }
        })

        adapterActionsBottomSheetDialog.show(supportFragmentManager, AdapterActionsBottomSheetDialog.DIALOG_TAG)
    }

    override fun openItemStringBottomSheetDialog(id: String) {
//        TODO("Not yet implemented")
    }
}