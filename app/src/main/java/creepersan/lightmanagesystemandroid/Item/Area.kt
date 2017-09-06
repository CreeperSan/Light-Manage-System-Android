package creepersan.lightmanagesystemandroid.Item

import java.io.Serializable

class Area(var name: String, addTime: Long) :Serializable{
    var id:Long = addTime
        private set
    var deviceList = ArrayList<String>()

    fun addDevice(node:String) = deviceList.add(node)
    fun removeDevice(node:String) = deviceList.remove(node)
    fun sizeDevice():Int = deviceList.size
    fun clearDevice() = deviceList.clear()

}