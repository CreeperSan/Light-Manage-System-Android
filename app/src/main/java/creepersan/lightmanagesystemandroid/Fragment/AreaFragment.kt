package creepersan.lightmanagesystemandroid.Fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardItemComponent
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.Subscribe

class AreaFragment:BaseCardFragment(){
    private lateinit var areaCard:CardComponent
    private var deviceList = ArrayList<Device>()

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
            val areaNameEditText = dialogView.findViewById<EditText>(R.id.dialogAreaAddEditText)
            val deviceListView = dialogView.findViewById<RecyclerView>(R.id.dialogAreaAddRecyclerView)
            deviceListView.layoutManager = GridLayoutManager(activity,2)
            setSelectedToDefault()
            val deviceListAdapter = DeviceAdapter(deviceList)
            deviceListView.adapter = deviceListAdapter
            val runnable = object : Runnable{
                override fun run() {
                    val areaName = areaNameEditText.text.toString()
                    if (areaName == ""){
                        toast("还没输入区域名字噢")
                        return
                    }
                    val selectedDeviceList = ArrayList<Device>()
                    for (device in deviceListAdapter.getData()){
                        if (device.status)
                            selectedDeviceList.add(device)
                    }
                    toast("已发送添加区域请求，设备总数 ${selectedDeviceList.size}")
                    postEvent(AddAreaEvent(Area(areaName,selectedDeviceList)))
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

    private fun setSelectedToDefault(){
        for (device in deviceList){
            device.status = false
        }
    }

    @Subscribe
    fun onGetDeviceListResultEvent(event:GetDeviceListResultEvent){
        if (event.isSuccess){
            deviceList = event.deviceList
        }else{
            deviceList.clear()
        }
        setSelectedToDefault()
    }
    @Subscribe
    fun onGetAreaListResultEvent(event:GetAreaListResultEvent){
        setRefreshing(false)
        areaCard.clearCardItem()
        if (event.isSuccess){
            if (event.areaList.size>0){
                areaCard.hideEmptyHintTextView()
                for (i in 0..event.areaList.size-1){
                    val area = event.areaList[i]
                    val areaComponent = CardItemComponent(i,activity)
                    areaComponent.setTitle(area.areaName)
                    areaComponent.setOnComponentLongClickListener(object :View.OnLongClickListener{

                        override fun onLongClick(p0: View?): Boolean {
                            val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_public_delete,null)
                            val dialogTextView = dialogView.findViewById<TextView>(R.id.dialogPublicDeleteText)
                            dialogTextView.text = "确认删除 ${area.areaName} 吗？此操作不可恢复！"
                            val dialogRunnable = Runnable {
                                postEvent(RemoveAreaEvent(area))
                            }
                            showDialog("删除区域",R.drawable.background_dialog_remove,dialogView,dialogRunnable)
                            return true
                        }

                    })
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
    @Subscribe
    fun onRemoveAreaResultEvent(event: RemoveAreaResultEvent){
        if (event.isDeleted){
            setRefreshing(true)
            toast("区域已删除")
            postEvent(GetAreaListEvent(true))
        }else{
            if (event.isConnected){
                toast("提交区域删除请求失败，请检查设备状态")
            }else{
                toast("提交区域删除请求失败，请检查网络连接")
            }
        }
    }
    @Subscribe
    fun onAddAreaResultEvent(event:AddAreaResultEvent){
        if (event.isAdded){
            setRefreshing(true)
            toast("区域添加成功")
            postEvent(GetAreaListEvent(true))
        }else{
            if (event.isConnected){
                toast("添加失败，请检查设备状况")
            }else{
                toast("添加失败，请检查网络连接")
            }
        }
    }

    inner class DeviceHolder(checkBox: CheckBox) : RecyclerView.ViewHolder(checkBox) {
        val checkBox = itemView as CheckBox

        fun setChecked(isCheck: Boolean){
            checkBox.isChecked = isCheck
        }

        fun setName(name:String){
            checkBox.text = name
        }
    }
    inner class DeviceAdapter(list: ArrayList<Device>) : RecyclerView.Adapter<DeviceHolder>() {
        private var deviceItemList = list

        fun setData(list:ArrayList<Device>){
            this.deviceItemList = deviceItemList
        }
        fun getData():ArrayList<Device>{
            return deviceItemList
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DeviceHolder {
            return DeviceHolder(CheckBox(activity))
        }

        override fun getItemCount(): Int {
            return deviceItemList.size
        }

        override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
            holder.setChecked(deviceItemList[position].status)
            holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
                deviceItemList[position].status = b
            }
            holder.setName(deviceItemList[position].deviceName)
        }

        fun addElement(deviceItem:Device){
            deviceItemList.add(deviceItem)
        }
        fun refreshList(){
            notifyDataSetChanged()
        }
    }

}
