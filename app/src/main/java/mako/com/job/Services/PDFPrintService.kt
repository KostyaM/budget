package mako.com.job.Services

import android.app.Activity
import android.content.Context

import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.*
import android.print.PageRange

import android.print.PrintDocumentInfo
import android.print.PrintAttributes

import java.io.*


class PDFPrintService(val activity: Activity) : PrintService {
    override fun doPrintJob(PDFFile:File) {

        activity.also { context ->
            // Get a PrintManager instance
            val printManager = context.getSystemService(Context.PRINT_SERVICE) as PrintManager
            // Set job name, which will be displayed in the print queue
            val jobName = "Printing credentials document"
            // Start a print job, passing in a PrintDocumentAdapter implementation
            // to handle the generation of a print document
            printManager.print(jobName, MyPrintDocumentAdapter(PDFFile), null)
        }




    }


    class MyPrintDocumentAdapter(val PDFFile:File) : PrintDocumentAdapter() {



        override fun onLayout(printAttributes: PrintAttributes, printAttributes1: PrintAttributes, cancellationSignal: CancellationSignal, layoutResultCallback: PrintDocumentAdapter.LayoutResultCallback, bundle: Bundle) {
            if (cancellationSignal.isCanceled) {
                layoutResultCallback.onLayoutCancelled()
            } else {
                val builder = PrintDocumentInfo.Builder(" file name")
                builder.setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                        .setPageCount(PrintDocumentInfo.PAGE_COUNT_UNKNOWN)
                        .build()
                layoutResultCallback.onLayoutFinished(builder.build(),
                        printAttributes1 != printAttributes)
            }
        }

        override fun onWrite(pageRanges: Array<out PageRange>?,
                             parcelFileDescriptor: ParcelFileDescriptor?,
                             cancellationSignal: CancellationSignal?,
                             writeResultCallback: WriteResultCallback?) {
            lateinit var istream: InputStream
            lateinit var ostream: OutputStream


            istream = FileInputStream(PDFFile)

            ostream = FileOutputStream(parcelFileDescriptor!!.fileDescriptor)

            val buf = ByteArray(16384)
            var size: Int

            while ( !cancellationSignal!!.isCanceled()) {

                size = istream.read(buf)
                if(size>0) ostream.write(buf, 0, size)else break
            }

            if (cancellationSignal!!.isCanceled()) {
                writeResultCallback!!.onWriteCancelled();
            } else {
                writeResultCallback!!.onWriteFinished(arrayOf(PageRange.ALL_PAGES));
            }
        }


    }


}