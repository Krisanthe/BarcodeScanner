package krisanthe.task.barcodescanner.screens

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.zxing.Result
import kotlinx.coroutines.launch
import krisanthe.task.barcodescanner.R
import krisanthe.task.barcodescanner.application.BarcodeScannerApplication
import krisanthe.task.barcodescanner.model.Item
import krisanthe.task.barcodescanner.screens.dagger.DaggerMainComponent
import krisanthe.task.barcodescanner.screens.dagger.MainModule
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST = 1
    }

    private var scannerView: ZXingScannerView? = null
    private var recyclerView: RecyclerView? = null

    @Inject
    lateinit var viewModel: MainViewModel

    private val itemsAdapter = ItemsAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponent()
        initViews()
        checkPermission()
        updateAdapter()
    }

    private fun initComponent() {
        DaggerMainComponent.builder()
            .appComponent(BarcodeScannerApplication.appComponent)
            .mainModule(MainModule(this))
            .build()
            .inject(this)
    }

    private fun initRecyclerView() {
        val itemsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView?.apply {
            adapter = itemsAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, VERTICAL))
            layoutManager = itemsLayoutManager
        }
    }

    private fun initViews() {
        scannerView = findViewById(R.id.scanner_view)
        recyclerView = findViewById(R.id.recycler_view)
        initRecyclerView()
    }

    private fun checkPermission() {
        val permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startCamera()
        } else {
            requestCameraPermissions()
        }
    }

    private fun requestCameraPermissions() {
        val permission = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(this, permission, CAMERA_PERMISSION_REQUEST)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            startCamera()
        } else {
            showDialog("Camera permission is required.")
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun updateAdapter() {
        lifecycleScope.launch {
            val firstTwo = viewModel.getFirstTwoRecords()
            val lastTwo = viewModel.getLastTwoRecords()
            itemsAdapter.update(firstTwo, lastTwo)
        }
    }

    private fun showDialog(message: String, okCallback: () -> Unit = {}) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setOnCancelListener { okCallback() }
            .setPositiveButton("OK") { dialog, _ ->
                okCallback()
                dialog.dismiss()
            }
            .show()
    }

    private fun startCamera() {
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun onResume() {
        super.onResume()
        startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun handleResult(result: Result?) {
        val message = result?.text?.also {
            viewModel.saveResult(Item(it, System.currentTimeMillis()))
        } ?: "Coś poszło nie tak"
        showDialog(message) {
            updateAdapter()
        }
        startCamera()
    }
}