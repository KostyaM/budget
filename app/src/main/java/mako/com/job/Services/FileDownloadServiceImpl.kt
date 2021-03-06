package mako.com.job.Services

import android.os.AsyncTask
import mako.com.job.UI.StartActivity.IStart
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import android.os.Environment.getExternalStorageDirectory
import java.lang.Exception


class FileDownloadServiceImpl(val presenter: IStart.Presenter) : FileDownloadService {

    lateinit var PDFFile: File
    override fun downloadPDF(url: String) {
        PDFFile = File("/storage/emulated/0/Download/budget.pdf")
        if (!PDFFile.exists()) PDFFile.createNewFile()
        Downloader(url).execute()

    }

    inner class Downloader(val url: String) : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String? {
            try {
                val input = URL(url).openStream()
                val output = FileOutputStream(PDFFile)
                input.use { _ ->
                    output.use { _ ->
                        input.copyTo(output)
                    }
                }
            }catch (e:Exception){
                return "fail"
            }

            return "done"
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            if(result.equals("done")) presenter.downloadFinished(PDFFile) else presenter.networkProblem()

        }
    }


}