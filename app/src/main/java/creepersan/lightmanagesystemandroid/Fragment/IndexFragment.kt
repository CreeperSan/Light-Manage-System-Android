package creepersan.lightmanagesystemandroid.Fragment

import android.graphics.Color
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Base.BaseFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListResultEvent
import org.greenrobot.eventbus.Subscribe

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
    }

    @Subscribe()
    fun on(event:GetDeviceListResultEvent){
        setRefreshing(false)
        for (i in 0..event.deviceList.size-1){
            val device = event.deviceList[i]

        }
    }


}
