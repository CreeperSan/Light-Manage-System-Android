package creepersan.lightmanagesystemandroid.Helper

import creepersan.lightmanagesystemandroid.Item.Device

object StringHelper{

    fun getDeviceTypeStr(type:Int):String{
        when(type){
            Device.TYPE.SENSOR -> return "传感器"
            Device.TYPE.LIGHT_NORMAL -> return "灯光设备"
            Device.TYPE.LIGHT_COLOR -> return "多彩灯光设备"
            Device.TYPE.SENSOR_GAS -> return "气体传感器"
            Device.TYPE.SENSOR_LIGHT -> return "光传感器"
            Device.TYPE.SENSOR_SOUND -> return "声传感器"
            else -> return "未知设备"
        }
    }

}