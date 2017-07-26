package creepersan.lightmanagesystemandroid.Fragment

import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Base.BaseFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent

class IndexFragment :BaseCardFragment(){
    private lateinit var areaStateCard:CardComponent
    private lateinit var deviceStateCard:CardComponent

    override fun onViewInflated() {
        areaStateCard = CardComponent(activity)
        deviceStateCard = CardComponent(activity)
        areaStateCard.setTitle("区域状态")
        areaStateCard.setEmptyHintTextViewText("尚未添加区域")
        deviceStateCard.setTitle("设备状态")
        deviceStateCard.setEmptyHintTextViewText("尚未添加设备")
        addComponent(areaStateCard)
        addComponent(deviceStateCard)
        hideFloatingButton()
    }

}
