package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml
import creepersan.lightmanagesystemandroid.Item.Device

class DeviceListHtml(srcString: String):BaseHtml(srcString){
    private val deviceList = ArrayList<Device>()
    private var isSuccess = false

    override fun decodeString(srcString: String) {

    }

    fun getDeviceList():ArrayList<Device> = deviceList
    fun isSuccess():Boolean = isSuccess

}
