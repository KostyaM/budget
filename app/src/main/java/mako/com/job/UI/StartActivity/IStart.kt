package mako.com.job.UI.StartActivity

import mako.com.job.Services.FileDownloadService
import mako.com.job.Services.PrintService
import java.io.File

interface IStart {
    interface View {
        fun checkPermissions()
        fun initViews()
        fun loadPDF(PDFFile: File)
        fun presentPrintService(): PrintService
        fun showProgress()
        fun hideProgress()
        fun enableButton()
        fun setDownloadProgress()
        fun setLoadProgress()
        fun startScan()
    }

    interface Presenter {
        fun permissionsGranted()
        fun printButtonClicked()
        fun downloadFinished(PDFFile: File)
        fun pdfLoadFinished()

        fun startWork(result: String?)
    }
}