package mako.com.job.UI.Camera

import android.content.Intent
import android.graphics.PointF
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import mako.com.job.UI.StartActivity.StartActivity
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import mako.com.job.R


class CameraActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener {


    private lateinit var  qrCodeReaderView: QRCodeReaderView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)

        qrCodeReaderView =  findViewById(R.id.qrdecoderview);
        qrCodeReaderView.setOnQRCodeReadListener(this);
        qrCodeReaderView.setQRDecodingEnabled(true);
        qrCodeReaderView.setAutofocusInterval(2000L);
        qrCodeReaderView.setTorchEnabled(true);
        qrCodeReaderView.setBackCamera();

    }

    override fun onQRCodeRead(text: String?, points: Array<out PointF>?) {
        val intent = Intent(this@CameraActivity, StartActivity::class.java)
        intent.putExtra("qr_result",text)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        qrCodeReaderView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView.stopCamera()
    }
}