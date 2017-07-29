package creepersan.lightmanagesystemandroid.Base

import android.app.AlertDialog
import android.content.DialogInterface
import android.support.annotation.ColorInt
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import butterknife.BindView
import com.bumptech.glide.Glide
import creepersan.lightmanagesystemandroid.Activity.R

abstract class BaseCardFragment:BaseFragment(){

    @BindView(R.id.cardBaseSwipeRefreshLayout)lateinit var swipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.cardBaseScrollView)lateinit var scrollView: ScrollView
    @BindView(R.id.cardBaseFloatingActionButton)lateinit var floatingButton: FloatingActionButton
    @BindView(R.id.cardBaseCardListLayout)lateinit var linearLayout: LinearLayout

    override fun getLayoutID(): Int = R.layout.fragment_card_base

    private val componentList = ArrayList<BaseComponent>()

    protected fun addComponent(component: BaseComponent){
        componentList.add(component)
        linearLayout.addView(component.rootView)
    }
    protected fun addComponent(component: BaseComponent,position:Int){
        componentList.add(position,component)
        linearLayout.addView(component.rootView,position)
    }
    protected fun getComponent(position: Int):BaseComponent{
        return componentList.get(position)
    }
    protected fun removeComponent(component: BaseComponent){
        componentList.remove(component)
        linearLayout.removeView(component.rootView)
    }
    protected fun removeComponent(position: Int){
        componentList.remove(getComponent(position))
        linearLayout.removeViewAt(position)
    }
    protected fun clearComponent(){
        componentList.clear()
        linearLayout.removeAllViews()
    }

    fun hideFloatingButton(){
        floatingButton.visibility = View.GONE
    }
    fun showFloatingButton(){
        floatingButton.visibility = View.VISIBLE
    }
    fun setFloatingActionOnClickListener(listener:View.OnClickListener){
        floatingButton.setOnClickListener(listener)
    }

    protected fun showDialog(title:String,backgroundImgID:Int,view:View,runnable: Runnable){
        val dialogBuilder = AlertDialog.Builder(activity)
        val baseView = LayoutInflater.from(activity).inflate(R.layout.dialog_base_add,null)
        val dialogBackground = baseView.findViewById<ImageView>(R.id.dialogBaseAddBackground)
        val dialogViewGroup = baseView.findViewById<LinearLayout>(R.id.dialogBaseAddViewGroup)
        val dialogTitle = baseView.findViewById<TextView>(R.id.dialogBaseAddTitle)
        dialogTitle.text = title
        Glide.with(activity).load(backgroundImgID).into(dialogBackground)
        dialogViewGroup.addView(view)
        dialogBuilder.setView(baseView)
        dialogBuilder.setNegativeButton("取消",null)
        dialogBuilder.setPositiveButton("确定", DialogInterface.OnClickListener { dialogInterface, i -> runnable.run() })
        dialogBuilder.show()
    }

    fun setRefreshing(status:Boolean){
        swipeRefreshLayout.isRefreshing = status
    }
    fun setRefreshListener(listener:SwipeRefreshLayout.OnRefreshListener){
        swipeRefreshLayout.setOnRefreshListener(listener)
    }
    fun setRefreshColor(vararg colors: Int){
        swipeRefreshLayout.setColorSchemeColors(*colors)
    }

}
