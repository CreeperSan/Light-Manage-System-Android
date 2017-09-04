package creepersan.lightmanagesystemandroid.Item

class Area1(var name: String, addTime: Long) {
    var id:Long = addTime
        private set
    var deviceList = ArrayList<String>()

    fun addDevice(node:String) = deviceList.add(node)
    fun removeDevice(node:String) = deviceList.remove(node)
    fun sizeDevice():Int = deviceList.size
    fun clearDevice() = deviceList.clear()

}