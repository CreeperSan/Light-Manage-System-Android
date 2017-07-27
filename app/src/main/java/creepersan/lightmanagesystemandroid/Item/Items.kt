package creepersan.lightmanagesystemandroid.Item

class Device(var deviceName:String,var deviceType:String,var pointInfo:String,var status:Boolean)
class Area{
    lateinit var deviceName:String
    lateinit var deviceList:ArrayList<Device>

    constructor(deviceName: String){
        this.deviceName = deviceName
    }

    constructor(deviceName: String,deviceList:ArrayList<Device>){
        this.deviceName = deviceName
        this.deviceList = deviceList
    }

    fun addDevice(device:Device){
        deviceList.add(device)
    }
    fun addDevice(device: Device,position:Int){
        deviceList.add(position,device)
    }
    fun removeDevice(device: Device){
        deviceList.remove(device)
    }
    fun removeDevice(position: Int){
        deviceList.removeAt(position)
    }
    fun clearList(){
        deviceList.clear()
    }

}