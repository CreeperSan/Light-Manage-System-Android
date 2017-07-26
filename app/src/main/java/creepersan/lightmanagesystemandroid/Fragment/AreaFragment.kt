package creepersan.lightmanagesystemandroid.Fragment

import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent

class AreaFragment:BaseCardFragment(){
    private lateinit var areaCard:CardComponent

    override fun onViewInflated() {
        areaCard = CardComponent(activity)
        areaCard.setTitle("区域管理")
        areaCard.setEmptyHintTextViewText("尚未添加区域")
        addComponent(areaCard)
    }

}
