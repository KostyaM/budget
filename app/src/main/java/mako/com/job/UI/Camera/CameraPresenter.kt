package mako.com.job.UI.Camera
class CameraPresenter(val view:ICamera.View):ICamera.Presenter {
    var tourchTrigger=false
    override fun startWork() {
        view.initViews()
        view.configQRCodeReader()
    }

    override fun onQrDetected(text:String) {
        view.sendResult(text)
    }

    override fun tourchButtonClicked() {
        tourchTrigger = !tourchTrigger
        if (tourchTrigger) view.tourchOn() else view.tourchOff()
    }

    }

