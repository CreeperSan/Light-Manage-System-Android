package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml
import creepersan.lightmanagesystemandroid.Event.GetDeviceListResultEvent
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import java.nio.charset.Charset

class DeviceListHtml(srcString: String):BaseHtml(srcString){
    var deviceList = ArrayList<Device>()
    private var isSuccess = false

    override fun decodeString(srcString: String) {
//        log("收到的消息为\n$srcString")
        deviceList = ArrayList<Device>()
        val newString = String(srcString.toByteArray(), Charset.forName("UTF-8"))
        try {
            val mainJsonObject = JSONObject(srcString)//主要的JSON
            val sensorJsonArray = mainJsonObject.optJSONArray(KEY.SENSOR)//传感器JSON array
            for ( i in 0..sensorJsonArray.length()-1){
                val sensorJson = sensorJsonArray.optJSONObject(i)
                val name = sensorJson.optString(KEY.SENSOR_NAME)
                val subDevice = sensorJson.optString(KEY.SENSOR_BIND_DEVICE)
                val deviceID = sensorJson.optString(KEY.SENSOR_DEVICE_ID)
                val device = Device(deviceID,name, Device.VALUE.USELESS)
                device.addSubDevice(subDevice)
                device.type = Device.TYPE.SENSOR
                deviceList.add(device)
            }
            val lightJsonArray = mainJsonObject.optJSONArray(KEY.LIGHT)//灯光JSON array
            for ( i in 0..lightJsonArray.length()-1){
                val lightJson = lightJsonArray.optJSONObject(i)
                if (lightJson==null){
                    log("Light #$i 解析失败")
                    continue
                }
                val name = lightJson.optString(KEY.LIGHT_LIGHT_DEVICE)
                val bright = lightJson.optString(KEY.LIGHT_BRIGHT)
                val swSta = lightJson.optString(KEY.LIGHT_SW_STA)
                val deviceID = lightJson.optString(KEY.LIGHT_DEVICE_ID)
                val device = Device(deviceID,name, Device.VALUE.USELESS)
                if (deviceID=="2" || deviceID=="7" || deviceID=="11"){
                    device.type = Device.TYPE.LIGHT_NORMAL
                }else{
                    device.type = Device.TYPE.LIGHT_COLOR
                }
                device.params = bright
                device.status = swSta
                deviceList.add(device)
            }
            isSuccess = true
        } catch (e: Exception) {
            isSuccess = false
            e.printStackTrace()
        }
        log("解析完毕，总共有"+deviceList.size+"个设备加入列表")
        log("是否成功：$isSuccess")
        EventBus.getDefault().post(GetDeviceListResultEvent(this.isSuccess(),true,deviceList))//解析完成提交事件
    }

    fun isSuccess():Boolean = isSuccess

    object KEY {
        val SENSOR = "sensor"
        val SENSOR_NAME = "sensor_name"//传感器名字
        val SENSOR_BIND_DEVICE = "bind_device"//绑定的设备ID
        val SENSOR_DEVICE_ID = "device_id"//设备ID
        val LIGHT = "light"
        val LIGHT_LIGHT_DEVICE = "light_device"
        val LIGHT_BRIGHT = "bright"
        val LIGHT_DEVICE_ID = "device_id"//设备ID
        val LIGHT_SW_STA = "sw_sta"
    }

    //id为2 7 11的灯是可以调节亮度的，其他的都不可以
}
