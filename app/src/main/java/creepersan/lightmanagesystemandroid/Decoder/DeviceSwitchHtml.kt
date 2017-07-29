package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml

class DeviceSwitchHtml(srcString: String):BaseHtml(srcString){
    private var isSuccess = false
    private var newState = false

    override fun decodeString(srcString: String) {

    }

    fun isSuccess():Boolean = isSuccess
    fun getNewState():Boolean = newState
}