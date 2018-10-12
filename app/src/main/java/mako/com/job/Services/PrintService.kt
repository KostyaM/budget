package mako.com.job.Services

import java.io.File

interface PrintService {
    fun doPrintJob(PDFFile: File)
}