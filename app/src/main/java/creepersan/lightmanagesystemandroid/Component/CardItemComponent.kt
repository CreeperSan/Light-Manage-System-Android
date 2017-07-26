package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent

class CardItemComponent(context: Context):BaseComponent(context){
    @BindView(R.id.cardItemComponentTitle)lateinit var titleText:TextView
    @BindView(R.id.cardItemComponentSubTitle)lateinit var subTitleText:TextView
    @BindView(R.id.cardItemComponentSwitch)lateinit var statusSwitch:Switch

    override fun getLayoutID(): Int = R.layout.component_card_item

    fun setTitleText(content:String){
        titleText.text = content
    }

    fun setSubTitleText(content: String){
        subTitleText.text = content
    }

    fun setSwitchStatus(status:Boolean){
        statusSwitch.isChecked = status
    }

    fun getSwitchStatus():Boolean{
        return statusSwitch.isChecked
    }
}