package creepersan.lightmanagesystemandroid.Fragment

import android.content.Intent
import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import creepersan.lightmanagesystemandroid.Activity.AreaControlActivity
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardItemComponent
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DeviceFragment:BaseCardFragment(){
    private lateinit var normalDeviceCard:CardComponent
    private lateinit var inductionDeviceCard:CardComponent
    private lateinit var otherDeviceCard:CardComponent

    private var normalCardItemComponentList = ArrayList<CardItemComponent>()

    override fun onViewInflated() {
        initCards()
        initSwipeRefreshLayout()
        initFloatingButton()
        postEvent(GetDeviceListEvent(true))
    }

    private fun initCards() {
        normalDeviceCard = CardComponent(activity)
        inductionDeviceCard = CardComponent(activity)
        otherDeviceCard = CardComponent(activity)
        normalDeviceCard.setTitle("控制设备")
        normalDeviceCard.setEmptyHintTextViewText("尚未添加控制设备")
        inductionDeviceCard.setTitle("感应设备")
        inductionDeviceCard.setEmptyHintTextViewText("尚未添加感应设备")
        otherDeviceCard.setTitle("其他设备")
        otherDeviceCard.setEmptyHintTextViewText("尚未添加其他设备")
        addComponent(normalDeviceCard)
        addComponent(inductionDeviceCard)
        addComponent(otherDeviceCard)
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
            setRefreshing(false)
            normalDeviceCard.clearCardItem()
            normalCardItemComponentList.clear()
            if (event.isSuccess){
                log("大小为 : "+event.deviceList.size)
                for (i in 0..event.deviceList.size-1){
                    val device = event.deviceList[i]
                    val cardItemComponent = CardItemComponent(i,activity)
                    cardItemComponent.setDevice(device)
                    cardItemComponent.setTitle(device.name)
                    cardItemComponent.setSubTitle("${device.node} ( ${device.type} )")
                    cardItemComponent.setOnSwitchListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                        toast("改变了")
                    })
                    cardItemComponent.setOnSwitchListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                        cardItemComponent.setSwitchHandleState(true)
                        postEvent(DeviceSwitchEvent(device,b))
                    })
                    normalCardItemComponentList.add(cardItemComponent)
                    normalDeviceCard.addCardItem(cardItemComponent)
                }
                normalDeviceCard.hideEmptyHintTextView()
            }else{
                normalDeviceCard.showEmptyHintTextView()
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

}
