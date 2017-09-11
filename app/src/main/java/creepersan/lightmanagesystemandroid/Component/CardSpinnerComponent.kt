package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent
import creepersan.lightmanagesystemandroid.Item.Device
import java.util.*

class CardSpinnerComponent(context: Context) : BaseComponent(context){
    @BindView(R.id.cardItemSensorComponentSpinner)lateinit var spinner:Spinner
    @BindView(R.id.cardItemSensorComponentTitle)lateinit var title:TextView
    @BindView(R.id.cardItemSensorComponentSubTitle)lateinit var subTitle:TextView

    var sensorDeviceList = ArrayList<Device>()
    var sensorStrList = ArrayList<String>()
    var adapter = ArrayAdapter<String>(context,android.R.layout.simple_spinner_item,sensorStrList)

    override fun getLayoutID(): Int = R.layout.component_card_item_sensor

    fun setData(deviceList:ArrayList<Device>,currentDevice: Device,listener:AdapterView.OnItemSelectedListener){

        val strList = ArrayList<String>()
        for (device in deviceList){
            strList.add(device.name)
        }
        strList.add("未绑定")
        adapter = ArrayAdapter(spinner.context,android.R.layout.simple_spinner_item,strList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = listener
        var count = 0
        for (tempDevice in deviceList){
            if (tempDevice.node.toInt() == currentDevice.subDevice.toInt()){
                spinner.setSelection(count)
                return
            }
            count ++
        }
        spinner.setSelection(strList.size-1)
    }




}