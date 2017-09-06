package creepersan.lightmanagesystemandroid.Fragment

import android.graphics.Color
import android.support.v4.widget.SwipeRefreshLayout
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Base.BaseFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardStatusComponent
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Item.Area
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class IndexFragment :BaseCardFragment(){
    private lateinit var areaStateCard:CardComponent
    private lateinit var deviceStateCard:CardComponent

    override fun onViewInflated() {
        initCard()
        hideFloatingButton()
        initSwipeRefreshLayout()
    }

    private fun initCard(){
        areaStateCard = CardComponent(activity)
        deviceStateCard = CardComponent(activity)
        areaStateCard.setTitle("区域状态")
        areaStateCard.setEmptyHintTextViewText("尚未添加区域")
        deviceStateCard.setTitle("设备状态")
        deviceStateCard.setEmptyHintTextViewText("尚未添加设备")
        addComponent(areaStateCard)
        addComponent(deviceStateCard)
    }
    private fun initSwipeRefreshLayout(){
        setRefreshing(true)
        setRefreshColor(Color.parseColor("#E51C23"))
        setRefreshListener(SwipeRefreshLayout.OnRefreshListener {
            postEvent(GetDeviceListEvent(true))
            postEvent(GetAreaListEvent(true))
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDeviceListResultEvent(event:GetDeviceListResultEvent){
        setRefreshing(false)
        deviceStateCard.clearCardItem()
        if (event.deviceList.size>0){
            deviceStateCard.hideEmptyHintTextView()
        }else{
            deviceStateCard.showEmptyHintTextView()
        }
        for (i in 0..event.deviceList.size-1){
            val device = event.deviceList[i]
            val cardStatusItem = CardStatusComponent(activity)
            cardStatusItem.setTitleText(device.name)
            cardStatusItem.setSubTitleText("${device.node} ( ${device.type} )")
            if (true){
                cardStatusItem.setStatusImageRes(R.drawable.ic_status_online)
            }else{
                cardStatusItem.setStatusImageRes(R.drawable.ic_status_offline)
            }
            deviceStateCard.addCardItem(cardStatusItem)
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetAreaListEvent(event:GetAreaListResultEvent){
        areaStateCard.clearCardItem()
        if (event.isSuccess){
            if (event.areaList.size > 0){
                for (i in 0..event.areaList.size-1){
                    val area = event.areaList[i]
                    val areaStatusComponent = CardStatusComponent(activity)
                    areaStatusComponent.setTitleText(area.name)
                    areaStateCard.addCardItem(areaStatusComponent)
                }
            }else{
                areaStateCard.showEmptyHintTextView()
            }
        }else{
            areaStateCard.showEmptyHintTextView()
            if (event.isConnected){
                toast("连接超时,请检查你的网络连接于设备状况")
            }else{
                toast("连接失败，请检查你的网络状况")
            }
        }
    }


}
