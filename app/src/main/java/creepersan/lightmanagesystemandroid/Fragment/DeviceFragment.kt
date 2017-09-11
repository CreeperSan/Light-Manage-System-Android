package creepersan.lightmanagesystemandroid.Fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Callback.SpinnerItemSelectListener
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardItemComponent
import creepersan.lightmanagesystemandroid.Component.CardLightLevelComponent
import creepersan.lightmanagesystemandroid.Component.CardSpinnerComponent
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Helper.StringHelper
import creepersan.lightmanagesystemandroid.Helper.UrlHelper
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DeviceFragment:BaseCardFragment(){
    private lateinit var normalDeviceCard:CardComponent
    private lateinit var inductionDeviceCard:CardComponent

    var deviceList = ArrayList<Device>()
    var lightBindDeviceList  = ArrayList<Device>()

    private var normalCardItemComponentList = ArrayList<CardItemComponent>()
    private var colorCardItemComponentList = ArrayList<CardLightLevelComponent>()
    private var sensorCardItemComponentList = ArrayList<CardSpinnerComponent>()

    override fun onViewInflated() {
        initCards()
        initSwipeRefreshLayout()
        initFloatingButton()
        postEvent(GetDeviceListEvent(true))
    }

    private fun initCards() {
        normalDeviceCard = CardComponent(activity)
        inductionDeviceCard = CardComponent(activity)
        normalDeviceCard.setTitle("灯光设备")
        normalDeviceCard.setEmptyHintTextViewText("尚未添加控制设备")
        inductionDeviceCard.setTitle("感应设备")
        inductionDeviceCard.setEmptyHintTextViewText("尚未添加感应设备")
        addComponent(normalDeviceCard)
        addComponent(inductionDeviceCard)
    }
    private fun initFloatingButton() {
        hideFloatingButton()
    }
    private fun initSwipeRefreshLayout() {
        setRefreshColor(Color.parseColor("#9C27B0"))
        setRefreshing(true)
        setRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            postEvent(GetDeviceListEvent(true))
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDeviceListResultEvent(event:GetDeviceListResultEvent){
            val strArray = activity.resources.getStringArray(R.array.lightLevel)
            setRefreshing(false)
            normalDeviceCard.clearCardItem()
            inductionDeviceCard.clearCardItem()
            normalCardItemComponentList.clear()
            colorCardItemComponentList.clear()
            sensorCardItemComponentList.clear()
            deviceList = event.deviceList
            if (event.isSuccess){
                //往下为准备灯光设备，用于给感应设备绑定
                lightBindDeviceList = ArrayList<Device>()
                for (device in event.deviceList){
                    if((device.type != Device.TYPE.SENSOR) and
                            (device.type != Device.TYPE.SENSOR_SOUND) and
                            (device.type != Device.TYPE.SENSOR_LIGHT) and
                            (device.type != Device.TYPE.SENSOR_GAS) ){
                        lightBindDeviceList.add(device)
                    }
                }
                //往下为添加设备
                for (i in 0..event.deviceList.size-1){
                    val device = event.deviceList[i]
                    if (device.type == Device.TYPE.LIGHT_COLOR){//全彩灯，只能开关
                        val cardItemComponent = CardItemComponent(i,activity)
                        cardItemComponent.setDevice(device)
                        cardItemComponent.setTitle(device.name)
                        cardItemComponent.setSubTitle("编号:${device.node} ( ${StringHelper.getDeviceTypeStr(device.type)} )")
                        cardItemComponent.setSwitch(device.status.toInt() == 1)
                        cardItemComponent.setOnSwitchListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
//                            toast("点击了开关")
                            val index = deviceList.indexOf(device)
//                            log("前面的Status ${deviceList[index].status}")
//                            log("收到的 index 为 $index")
                            if (deviceList[index].status == "1"){
                                deviceList[index].status = "0"
//                                log("status设置变成了0")
                            }else{
                                deviceList[index].status = "1"
//                                log("status设置变成了1")
                            }
//                            log("后面的Status ${deviceList[index].status}")
                            postEvent(SetupDeviceStateEvent(UrlHelper.getStuupDeviceUrl(deviceList)))
//                            log("发送URL  =  ${UrlHelper.getStuupDeviceUrl(deviceList)}")
                        })
                        normalCardItemComponentList.add(cardItemComponent)
                        normalDeviceCard.addCardItem(cardItemComponent)
                        normalDeviceCard.hideEmptyHintTextView()
                    }else if (device.type == Device.TYPE.LIGHT_NORMAL){//单色能，能调节亮度
                        val cardItemComponent = CardLightLevelComponent(activity)
                        cardItemComponent.titleTextView.text = device.name
                        cardItemComponent.subTitleTextView.text = "编号:${device.node} ( ${StringHelper.getDeviceTypeStr(device.type)} )"
                        try {//设置当前亮度
                            cardItemComponent.setSpinnerSelection(device.params.toInt())
                        } catch (e: Exception) {
                            cardItemComponent.setSpinnerSelection(0)
                        }
                        cardItemComponent.setSpinnerListener(object : SpinnerItemSelectListener(){
                            override fun onItemPick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                                toast("选上了 第 $p2 个")
                                deviceList[i].params = p2.toString()
                                postEvent(SetupDeviceStateEvent(UrlHelper.getStuupDeviceUrl(deviceList)))
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {}
                        })
                        normalDeviceCard.addCardItem(cardItemComponent)
                        colorCardItemComponentList.add(cardItemComponent)
                        normalDeviceCard.hideEmptyHintTextView()
                    }else if ((device.type == Device.TYPE.SENSOR)       //传感器，能绑定设备
                            or (device.type == Device.TYPE.SENSOR_GAS)
                            or (device.type == Device.TYPE.SENSOR_LIGHT)
                            or (device.type == Device.TYPE.SENSOR_SOUND)){
                        val cardItemComponent = CardSpinnerComponent(activity)
                        cardItemComponent.title.text = device.name
                        cardItemComponent.subTitle.text = "编号:${device.node} ( ${StringHelper.getDeviceTypeStr(device.type)} )"
                        cardItemComponent.setData(lightBindDeviceList,device,object : SpinnerItemSelectListener(){
                            override fun onItemPick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                                toastLong("旧的${deviceList[i].subDevice}  新的${lightBindDeviceList[p2].node}")
                                if (p2 >= lightBindDeviceList.size){
                                    log("点击了未绑定")
                                    deviceList[i].subDevice = "0"
                                }else{
                                    deviceList[i].subDevice = lightBindDeviceList[p2].node
                                    toastLong("对象为 : ${deviceList[i].name}    目标为 : ${lightBindDeviceList[p2].name}")
                                }
                                postEvent(SetupDeviceStateEvent(UrlHelper.getStuupDeviceUrl(deviceList)))
//                                toast("选择了 第 $p2 项")
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                toast("选择了 空")
                            }

                        })
                        sensorCardItemComponentList.add(cardItemComponent)
                        inductionDeviceCard.addCardItem(cardItemComponent)
                        inductionDeviceCard.hideEmptyHintTextView()
                    }
                }
            }else{
                normalDeviceCard.showEmptyHintTextView()
                inductionDeviceCard.showEmptyHintTextView()
                if (event.isConnected){
                    toast("获取设备列表失败，请检查网络连接")
                }else{
                    toast("获取设备列表超时，请检查你的网络连接并重试")
                }
            }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeviceRemoveResultEvent(event: RemoveDeviceResultEvent){
        setRefreshing(true)
        toast("设备删除成功")
        postEvent(GetDeviceListEvent(true))
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddDeviceResultEvent(event:AddDeviceResultEvent){
        if (event.isAdded){
            setRefreshing(true)
            postEvent(GetDeviceListEvent(true))
            toast("设备添加成功")
        }else{
            if (event.isConnected){
                toast("设备添加失败，请检查设备状况")
            }else{
                toast("设备添加失败，请检查网络连接")
            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeviceSwitchResultEvent(event:DeviceSwitchResultEvent){
        val deviceEvent = event.device
        if (event.isSuccess){
            for (cardItem in normalCardItemComponentList){
                if (cardItem.getDevice().node == deviceEvent.node){
                    cardItem.setSwitchHandleState(false)
                    cardItem.setSwitch(event.newState)
                }
            }
        }else{
            for (cardItem in normalCardItemComponentList){
                if (cardItem.getDevice().node == deviceEvent.node){
                    cardItem.setSwitchHandleState(false)
                    cardItem.setSwitch(!event.newState)
                }
            }
            if (event.isConnected){
                toast("设置失败，请检查设备状态")
            }else{
                toast("设置失败，请检查网络连接")

            }
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSetupDeviceStateResultEvent(event:SetupDeviceStateResultEvent){
        if (event.result){
            toast("设置成功")
        }else{
            toast("设置失败，请重试")
        }
    }
}
