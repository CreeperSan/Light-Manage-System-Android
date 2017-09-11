package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent

class CardLightLevelComponent(context: Context) : BaseComponent(context){
    @BindView(R.id.cardItemLevelComponentSpinner)lateinit var spinner:Spinner
    @BindView(R.id.cardItemLevelComponentTitle)lateinit var titleTextView: TextView
    @BindView(R.id.cardItemLevelComponentSubTitle)lateinit var subTitleTextView: TextView

    override fun getLayoutID(): Int = R.layout.component_card_item_light_level

    fun setSpinnerListener(listener:AdapterView.OnItemSelectedListener){
        spinner.onItemSelectedListener = listener
    }

    fun setSpinnerSelection(pos:Int){
        spinner.setSelection(pos)
    }

}