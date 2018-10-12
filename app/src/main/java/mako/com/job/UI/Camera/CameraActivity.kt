package mako.com.job.UI.Camera

import android.content.Intent
import android.graphics.PointF
import android.os.Build
import android.support.v7.app.AppCompatActivity

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import mako.com.job.UI.StartActivity.StartActivity
import com.dlazaro66.qrcodereaderview.QRCodeReaderView
import mako.com.job.R


class CameraActivity : AppCompatActivity(), QRCodeReaderView.OnQRCodeReadListener,ICamera.View {
    private lateinit var presenter: CameraPresenter
    private lateinit var  qrCodeReaderView: QRCodeReaderView
    private lateinit var pointsOverlayView: PointsOverlayView
    lateinit var torch_button:ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        presenter= CameraPresenter(this)
        presenter.startWork()
    }

    override fun onQRCodeRead(text: String?, points: Array<PointF>) {
        pointsOverlayView.setPoints(points);
        presenter.onQrDetected(text!!)
    }

    override fun sendResult(text:String) {
        val intent = Intent(this@CameraActivity, StartActivity::class.java)
        intent.putExtra("qr_result",text)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        qrCodeReaderView.startCamera()
        pointsOverlayView.cleareCanvas()
    }

    override fun onPause() {
        super.onPause()
        qrCodeReaderView.stopCamera()
    }

    override fun configQRCodeReader() {
        qrCodeReaderView.setOnQRCodeReadListener(this)
        qrCodeReaderView.setQRDecodingEnabled(true)
        qrCodeReaderView.setAutofocusInterval(2000L)
        qrCodeReaderView.setBackCamera()
    }

    override fun initViews() {
        changeStatusBar()
        pointsOverlayView = findViewById(R.id.points_overlay_view)
        qrCodeReaderView =  findViewById(R.id.qrdecoderview)
        torch_button=findViewById(R.id.flashlight)
        torch_button.setOnClickListener{
            presenter.tourchButtonClicked()
        }
    }

    override fun tourchOn() {
        qrCodeReaderView.setTorchEnabled(true)
        torch_button.setBackgroundResource(R.drawable.torch_on)
    }

    override fun tourchOff() {
        qrCodeReaderView.setTorchEnabled(false)
        torch_button.setBackgroundResource(R.drawable.torch_off)
    }
    private fun changeStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            //window.statusBarColor = Color.argb(0, 255, 255, 255)

        }
    }

}