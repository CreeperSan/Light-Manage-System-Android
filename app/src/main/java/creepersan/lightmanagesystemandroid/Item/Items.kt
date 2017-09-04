package creepersan.lightmanagesystemandroid.Item

class Area{
    lateinit var areaName:String
    lateinit var deviceList:ArrayList<Device>
    var areaStatus = OFF

    constructor(deviceName: String){
        this.areaName = deviceName
    }

    constructor(deviceName: String,deviceList:ArrayList<Device>){
        this.areaName = deviceName
        this.deviceList = deviceList
    }

    constructor(deviceName: String,deviceList:ArrayList<Device>,areaStatus:Int){
        this.areaName = deviceName
        this.deviceList = deviceList
        this.areaStatus = areaStatus
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

    companion object STATUS {
        val ON = 0
        val OFF = 1
        val PARTLY_ON = 2
    }

}