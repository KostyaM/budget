package mako.com.job.UI.Camera

interface ICamera {
    interface View{
        fun configQRCodeReader()
        fun initViews()
        fun sendResult(text:String)
        fun tourchOff()
        fun tourchOn()
    }
    interface Presenter{
        fun startWork()
        fun onQrDetected(text:String)
        fun tourchButtonClicked()
    }
}