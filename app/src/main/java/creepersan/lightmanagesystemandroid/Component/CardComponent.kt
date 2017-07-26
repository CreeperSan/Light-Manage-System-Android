package creepersan.lightmanagesystemandroid.Component

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseComponent

class CardComponent(context: Context):BaseComponent(context) {
    @BindView(R.id.componentCardContent)lateinit var contentLayout:LinearLayout
    @BindView(R.id.componentCardDiver)lateinit var diver:ImageView
    @BindView(R.id.componentCardEmptyHintText)lateinit var emptyContentHintText:TextView
    @BindView(R.id.componentCardTitle)lateinit var titleText:TextView

    private val cardItemList = ArrayList<BaseComponent>()

    override fun getLayoutID(): Int = R.layout.component_card

    fun setTitle(title:String){
        titleText.text = title
    }
    fun setTitleColor(color:Int){
        titleText.setTextColor(color)
    }
    fun setTitleSize(size:Float){
        titleText.textSize = size
    }

    fun setEmptyHintTextViewText(hint:String){
        emptyContentHintText.text = hint
    }
    fun setEmptyHintTextViewColor(color:Int){
        emptyContentHintText.setTextColor(color)
    }
    fun setEmptyHintTextViewSize(size:Float){
        emptyContentHintText.textSize = size
    }
    fun hideEmptyHintTextView(){
        emptyContentHintText.visibility = View.GONE
    }
    fun showEmptyHintTextView(){
        emptyContentHintText.visibility = View.VISIBLE
    }

    fun addCardItem(cardItem:BaseComponent){
        cardItemList.add(cardItem)
        contentLayout.addView(cardItem.rootView)
    }
    fun addCardItem(cardItem:BaseComponent,position:Int){
        cardItemList.add(position,cardItem)
        contentLayout.addView(cardItem.rootView,position)
    }
    fun removeCardItem(cardItem:BaseComponent){
        cardItemList.remove(cardItem)
        contentLayout.removeView(cardItem.rootView)
    }
    fun removeCardItem(position: Int){
        removeCardItem(getCardItem(position))
        contentLayout.removeViewAt(position)
    }
    fun getCardItem(position: Int):BaseComponent{
        return cardItemList.get(position)
    }
    fun clearCardItem(){
        cardItemList.clear()
        contentLayout.removeAllViews()
    }
    fun getCardItemList():ArrayList<BaseComponent>{
        return cardItemList
    }

}
