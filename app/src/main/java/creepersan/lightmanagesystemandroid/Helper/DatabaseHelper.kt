package creepersan.lightmanagesystemandroid.Helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import creepersan.lightmanagesystemandroid.Item.Area1
import creepersan.lightmanagesystemandroid.Item.Device

class DatabaseHelper {
    private lateinit var database:SQLiteDatabase

    private constructor(context: Context){
        database = context.openOrCreateDatabase(KEY.DATABASE_NAME,Context.MODE_PRIVATE,null)
        initTable()
    }

    fun initTable(){
        //此处见了3个表，表的结构参考 KEY
        database.execSQL("CREATE TABLE IF NOT EXISTS ${KEY.TABLE_DEVICE}(${KEY.KEY_DEVICE_NAME} TEXT NOT NULL," +
                "${KEY.KEY_DEVICE_TYPE} INTEGER NOT NULL,${KEY.KEY_DEVICE_NODE} TEXT NOT NULL,${KEY.KEY_DEVICE_PARAMS} INTEGER )")
        database.execSQL("CREATE TABLE IF NOT EXISTS ${KEY.TABLE_AREA}(${KEY.KEY_AREA_ID} TEXT NOT NULL," +
                "${KEY.KEY_AREA_NAME} TEXT NOT NULL)")
        database.execSQL("CREATE TABLE IF NOT EXISTS ${KEY.TABLE_AREA_DEVICE}(${KEY.KEY_AREA_DEVICE_AREA_ID} TEXT NOT NULL," +
                "${KEY.KEY_AREA_DEVICE_DEVICE_NODE} TEXT NOT NULL")
        database.execSQL("CREATE TABLE IF NOT EXISTS ${KEY.TABLE_DEVICE_SUB_DEVICE}(${KEY.KEY_DEVICE_SUB_DEVICE_DEVICE} TEXT NOT NULL," +
                "${KEY.KEY_DEVICE_SUB_DEVICE_SUB_DEVICE} TEXT NOT NULL")
    }

    fun insertDevice(device1: Device){
        insertDevice(device1.name,device1.type,device1.node,device1.params)
    }
    fun insertDevice(name:String,type:Int,node:String,params:String?){
        val contentValue = ContentValues()
        contentValue.put(KEY.KEY_DEVICE_NAME,name)
        contentValue.put(KEY.KEY_DEVICE_TYPE,type)
        contentValue.put(KEY.KEY_DEVICE_NODE,node)
        contentValue.put(KEY.KEY_DEVICE_NODE,params)
        database.insert(KEY.TABLE_DEVICE,null,contentValue)
    }
    fun deleteDevice(device1: Device){
        deleteDevice(device1.node)
    }
    fun deleteDevice(node:String){
        database.delete(KEY.TABLE_DEVICE,"${KEY.KEY_DEVICE_NODE}=?", arrayOf(node))
        database.delete(KEY.TABLE_AREA_DEVICE,"${KEY.KEY_AREA_DEVICE_DEVICE_NODE}=?", arrayOf(node))
    }
    fun updateDevice(device1: Device){
        updateDevice(device1.node,device1.name,device1.type,device1.params)
    }
    fun updateDevice(node: String,name: String,type: Int,params: String?){
        val contentValue = ContentValues()
        contentValue.put(KEY.KEY_DEVICE_TYPE,type)
        contentValue.put(KEY.KEY_DEVICE_NAME,name)
        contentValue.put(KEY.KEY_DEVICE_PARAMS,params)
        database.update(KEY.TABLE_DEVICE,contentValue,"${KEY.KEY_DEVICE_NODE}=?", arrayOf(node))
    }

    fun insertArea(id:Long,name:String){
        val contentValue = ContentValues()
        contentValue.put(KEY.KEY_AREA_ID,id)
        contentValue.put(KEY.KEY_AREA_NAME,name)
        database.insert(KEY.TABLE_AREA,null,contentValue)
    }
    fun insertArea(area: Area1){
        insertArea(area.id,area.name)
    }
    fun deleteArea(area:Area1){
        deleteArea(area.name)
    }
    fun deleteArea(id:String){

    }
    fun updateArea(area:Area1){
        updateArea(area.id,area.name)
    }
    fun updateArea(id:Long,name:String){

    }

    //todo 还得完善区域数据库的删改除查

    //todo 完善区域设备表的增删改除查

    //TODO 完善设备绑定信息表的增删改查

    private object Instance{
        var databaseHelper:DatabaseHelper? = null

        fun getInstance(context:Context):DatabaseHelper{
            if (databaseHelper==null){
                databaseHelper = DatabaseHelper(context)
            }
            return databaseHelper as DatabaseHelper
        }
    }


    object KEY{
        val DATABASE_NAME = "Area.db"

        val TABLE_AREA_DEVICE = "AreaDeviceInfo"
        val TABLE_DEVICE = "Device"
        val TABLE_AREA = "Area"
        val TABLE_DEVICE_SUB_DEVICE = "SubDevice"

        val KEY_AREA_NAME = "Name"
        val KEY_AREA_ID = "ID"      //就是时间戳吧

        val KEY_AREA_DEVICE_AREA_ID = "Area"
        val KEY_AREA_DEVICE_DEVICE_NODE = "Device"  //其实就是设备的节点信息

        val KEY_DEVICE_SUB_DEVICE_DEVICE = "Device"
        val KEY_DEVICE_SUB_DEVICE_SUB_DEVICE = "SubDevice"

        val KEY_DEVICE_NAME = "Name"
        val KEY_DEVICE_TYPE = "Type"
        val KEY_DEVICE_NODE = "Node"
        val KEY_DEVICE_PARAMS = "Params" //保存这个是因为想要保存临时的状态，以便下次打开的时候可已快速载入
    }
}