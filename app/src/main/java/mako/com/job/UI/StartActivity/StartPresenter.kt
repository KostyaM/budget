package mako.com.job.UI.StartActivity

import android.os.Handler
import mako.com.job.Services.FileDownloadServiceImpl
import java.io.File
import android.support.v4.os.HandlerCompat.postDelayed



class StartPresenter(val view: IStart.View) : IStart.Presenter {
    private lateinit var PDFFile: File

    override fun startWork(result: String?) {
        if (result != null) {
            qrScanSuccess(result)
        } else {
            view.checkPermissions()
        }
    }


    override fun permissionsGranted() {
        view.startScan()
    }

    override fun printButtonClicked() {
        view.presentPrintService().doPrintJob(PDFFile)
    }

    override fun downloadFinished(PDFFile: File) {
        view.showProgress()
        view.setLoadProgress()
        this.PDFFile = PDFFile
        view.loadPDF(PDFFile)
    }

    override fun pdfLoadFinished() {
        view.hideProgress()
        view.enableButton()
    }

    private fun qrScanSuccess(result: String) {
        view.initViews()
        val downloadService = FileDownloadServiceImpl(this)
        view.showProgress()
        view.setDownloadProgress()
        downloadService.downloadPDF("http://lokobasket.stage.sebbia.pro/api/v1/admin/accreditations/7313/getBadgePdf")
    }

    override fun networkProblem() {
        view.hideProgress()
        view.setNetworkError()
        val handler = Handler()
        handler.postDelayed({
           view.goBAck()
        }, 200)
    }
}