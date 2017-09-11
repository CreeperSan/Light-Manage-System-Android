package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.RelativeLayout
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device

class CardItemComponent(private var position:Int,context: Context):BaseComponent(context){
    @BindView(R.id.cardItemComponentTitle)lateinit var titleTextView:TextView
    @BindView(R.id.cardItemComponentSubTitle)lateinit var subTitleTextView:TextView
    @BindView(R.id.cardItemComponentSwitch)lateinit var statusSwitch:Switch
    @BindView(R.id.cardItemComponentRoot)lateinit var cardView:RelativeLayout

    override fun getLayoutID(): Int = R.layout.component_card_item
    private var device = Device("","",true)
    private var area = Area("",0)

    fun getDevice():Device = device
    fun getArea():Area = area
    fun setDevice(device: Device){
        this.device = device
    }
    fun setArea(area: Area){
        this.area = area
    }

    fun setTitle(title:String){
        titleTextView.text = title
    }
    fun setSubTitle(subTitle:String){
        subTitleTextView.text = subTitle
    }
    fun setSwitch(status:Boolean){
        statusSwitch.isChecked = status
    }
    fun setSwitchHandleState(isHandling:Boolean){
        if (isHandling){
            statusSwitch.alpha = 0.2f
            statusSwitch.isClickable = false
        }else{
            statusSwitch.alpha = 1f
            statusSwitch.isClickable = true
        }
    }
    fun getPosition():Int = position

    fun setOnComponentLongClickListener(listener: View.OnLongClickListener){
        cardView.setOnLongClickListener(listener)
    }
    fun setOnSwitchListener(listener:CompoundButton.OnCheckedChangeListener){
        statusSwitch.setOnCheckedChangeListener(listener)
    }
}
