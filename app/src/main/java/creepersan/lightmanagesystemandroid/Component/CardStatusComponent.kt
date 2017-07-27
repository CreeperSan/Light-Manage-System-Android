package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent

class CardStatusComponent(context: Context) :BaseComponent(context){
    @BindView(R.id.componentCardStatusTitle)lateinit var titleText:TextView
    @BindView(R.id.componentCardStatusSubTitle)lateinit var subTitleText:TextView
    @BindView(R.id.componentCardStatusImage)lateinit var statusImage:ImageView

    override fun getLayoutID(): Int = R.layout.compoment_card_status

    fun setTitleText(title:String){
        titleText.text = title
    }
    fun setSubTitleText(subTitle:String){
        subTitleText.text = subTitle
    }
    fun setStatusImageRes(resID:Int){
        statusImage.setImageResource(resID)
    }

}