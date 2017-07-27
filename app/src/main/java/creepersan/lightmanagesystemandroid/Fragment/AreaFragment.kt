package creepersan.lightmanagesystemandroid.Fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardItemComponent
import creepersan.lightmanagesystemandroid.Event.GetAreaListEvent
import creepersan.lightmanagesystemandroid.Event.GetAreaListResultEvent
import creepersan.lightmanagesystemandroid.Item.Area
import org.greenrobot.eventbus.Subscribe

class AreaFragment:BaseCardFragment(){
    private lateinit var areaCard:CardComponent

    override fun onViewInflated() {
        initCards()
        initFloatingButton()
        initSwipeRefreshLayout()
    }


    private fun initCards(){
        areaCard = CardComponent(activity)
        areaCard.setTitle("区域管理")
        areaCard.setEmptyHintTextViewText("尚未添加区域")
        addComponent(areaCard)
    }
    private fun initFloatingButton() {
        setFloatingActionOnClickListener(View.OnClickListener { view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_area_add_content,null)
            val areaName = dialogView.findViewById<EditText>(R.id.dialogAreaAddEditText)
            val deviceList = dialogView.findViewById<RecyclerView>(R.id.dialogAreaAddRecyclerView)
            deviceList.layoutManager = LinearLayoutManager(activity)
            val runnable = object : Runnable{
                override fun run() {
                    toast("区域名字")
                }
            }
            showDialog("添加区域",R.drawable.background_dialog_add_area,dialogView,runnable)
        })
    }
    private fun initSwipeRefreshLayout(){
        setRefreshColor(Color.parseColor("#5677FC"))
        setRefreshing(true)
        setRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            postEvent(GetAreaListEvent(true))
        })
    }

    @Subscribe()
    fun onGetAreaListResultEvent(event:GetAreaListResultEvent){
        setRefreshing(false)
        areaCard.clearCardItem()
        if (event.isSuccess){
            if (event.areaList.size>0){
                areaCard.hideEmptyHintTextView()
                for (i in 0..event.areaList.size-1){
                    val area = event.areaList[i]
                    val areaComponent = CardItemComponent(activity)
                    areaComponent.setTitle(area.areaName)
                    when(area.areaStatus){
                        Area.ON ->{
                            areaComponent.setSubTitle("已开启")
                            areaComponent.setSwitch(true)
                        }
                        Area.OFF -> {
                            areaComponent.setSubTitle("已关闭")
                            areaComponent.setSwitch(false)
                        }
                        Area.PARTLY_ON -> {
                            areaComponent.setSubTitle("部分开启")
                            areaComponent.setSwitch(true)
                        }
                    }
                    areaCard.addCardItem(areaComponent)
                }
            }else{
                areaCard.showEmptyHintTextView()
            }
        }else{
            areaCard.showEmptyHintTextView()
            if (event.isConnected){
                toast("连接超时，请检查你的网络连接以及设备状况")
            }else{
                toast("连接失败，请检查你的网络连接")
            }
        }
    }

    class DeviceItem(val deviceName:String,val deviceType:String,val pointInfo:String,val isChecck:Boolean)
    inner class DeviceHolder(checkBox: CheckBox) : RecyclerView.ViewHolder(checkBox) {
        val checkBox = itemView as CheckBox

        fun setChecked(isCheck: Boolean){
            checkBox.isChecked = isCheck
        }

        fun setName(name:String){
            checkBox.text = name
        }
    }
    inner class DeviceAdapter : RecyclerView.Adapter<DeviceHolder>() {
        private val deviceItemList = ArrayList<DeviceItem>()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceHolder {
            return DeviceHolder(CheckBox(activity))
        }

        override fun getItemCount(): Int {
            return deviceItemList.size
        }

        override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
            holder.setChecked(deviceItemList[position].isChecck)
            holder.setName(deviceItemList[position].deviceName)
        }

        fun addElement(deviceItem:DeviceItem){
            deviceItemList.add(deviceItem)
        }
        fun refreshList(){
            notifyDataSetChanged()
        }
    }

}
