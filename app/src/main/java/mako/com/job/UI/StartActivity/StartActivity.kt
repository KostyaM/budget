package mako.com.job.UI.StartActivity

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.github.barteksc.pdfviewer.PDFView
import java.io.File
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.Button
import mako.com.job.R
import mako.com.job.Services.PrintService
import mako.com.job.Services.PDFPrintService
import android.widget.LinearLayout
import android.widget.TextView
import com.github.barteksc.pdfviewer.listener.OnDrawListener

import kotlinx.android.synthetic.main.activity_main.*
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions


class StartActivity : AppCompatActivity(), IStart.View, OnDrawListener {
    lateinit var presenter: IStart.Presenter
    lateinit var pdfView: PDFView
    lateinit var printButton: Button
    lateinit var progress: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent = intent
        presenter = StartPresenter(this)
        presenter.startWork(intent.getStringExtra("qr_result"))

    }

    override fun checkPermissions() {
        val permissions = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE)
        Permissions.check(this, permissions, null, null, object : PermissionHandler() {
            override fun onGranted() {
                presenter.permissionsGranted()
            }
        })/*rationale*/
    }

    override fun initViews() {
        printButton = findViewById(R.id.print_button)
        printButton.setOnClickListener {
            presenter.printButtonClicked()
        }
        progress = findViewById(R.id.download_progress)
    }

    override fun loadPDF(PDFFile: File) {
        pdfView = findViewById(R.id.pdfView)
        pdfView.fromFile(PDFFile)
                .enableAnnotationRendering(true)
                .onDraw(this)
                .load();
    }

    override fun presentPrintService(): PrintService {
        return PDFPrintService(this)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun setDownloadProgress() {
        findViewById<TextView>(R.id.progress_text).text = getText(R.string.downloading)
    }

    override fun setLoadProgress() {
        findViewById<TextView>(R.id.progress_text).text = getText(R.string.loading)
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun onLayerDrawn(canvas: Canvas?, pageWidth: Float, pageHeight: Float, displayedPage: Int) {
        presenter.pdfLoadFinished()
    }

    override fun enableButton() {
        print_button.isEnabled = true
    }

    override fun startScan() {
        val intent = Intent(this@StartActivity, mako.com.job.UI.Camera.CameraActivity::class.java)
        startActivity(intent)
    }
}
