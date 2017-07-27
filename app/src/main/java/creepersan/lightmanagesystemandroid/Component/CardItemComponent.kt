package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent

class CardItemComponent(context: Context):BaseComponent(context){
    @BindView(R.id.cardItemComponentTitle)lateinit var titleTextView:TextView
    @BindView(R.id.cardItemComponentSubTitle)lateinit var subTitleTextView:TextView
    @BindView(R.id.cardItemComponentSwitch)lateinit var statusSwitch:Switch

    override fun getLayoutID(): Int = R.layout.component_card_item

    fun setTitle(title:String){
        titleTextView.text = title
    }
    fun setSubTitle(subTitle:String){
        subTitleTextView.text = subTitle
    }
    fun setSwitch(status:Boolean){
        statusSwitch.isChecked = status
    }

}
