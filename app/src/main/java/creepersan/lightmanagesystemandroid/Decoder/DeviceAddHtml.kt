package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml

class DeviceAddHtml(srcString: String):BaseHtml(srcString){
    private var isSuccess = false

    override fun decodeString(srcString: String) {

    }

    fun isSuccess():Boolean = isSuccess
}