package creepersan.lightmanagesystemandroid.Item

class Device {
    var name = ""                   //设备名称
    var isOnline = true               //设备状态
    var type = TYPE.UNKNOWN         //设备类型
    var node = ""
        private set                   //节点信息
    var subDeviceList:ArrayList<String>? = null      //绑定的设备的节点集合
    var params = ""                  //各个设备的参数，得首先判断类型才能知道他的含义
    var status = ""                 //对应灯光设备的sw_sta字段
    var subDevice = ""

    constructor(node:String){
        this.node = node
    }

    constructor(node: String,name:String,isOnline:Boolean){
        this.node = node
        this.name = name
        this.isOnline = isOnline
    }

    fun getSubDeviceSize():Int{
        if (subDeviceList==null){
            return -1
        }else{
            return subDeviceList!!.size
        }
    }

    fun addSubDevice(node:String){
        if (node == this.node){
            throw UnsupportedOperationException("不能添加自己")
        }else{
            initList()
            subDeviceList!!.add(node)
        }
    }

    fun removeSubDevice(node:String){
        if (subDeviceList!=null){
            subDeviceList!!.remove(node)
        }
    }

    fun clearSubDeviceList(){
        if (subDeviceList!=null){
            subDeviceList!!.clear()
        }
    }

    private fun initList(){
        if (subDeviceList==null){
            subDeviceList = ArrayList<String>()
        }
    }

    object TYPE{
        val UNKNOWN = -1           //未知设备
        val SENSOR = 0              //传感器
        val SENSOR_LIGHT = 1        //光感设备
        val SENSOR_GAS = 2          //煤气感应设备
        val SENSOR_SOUND = 3        //声音感应设备
        val LIGHT_NORMAL = 100      //普通灯光
        val LIGHT_COLOR = 101       //彩灯（不可调亮度）
    }

    object VALUE{
        val USELESS = "Useless"     //没有用上
    }
}