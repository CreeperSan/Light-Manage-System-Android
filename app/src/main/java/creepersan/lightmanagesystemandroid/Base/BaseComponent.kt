package creepersan.lightmanagesystemandroid.Base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import creepersan.lightmanagesystemandroid.Activity.R

abstract class BaseComponent(private val context:Context){
    var TAG = javaClass.simpleName
    set get
    var rootView:View
    get
    protected lateinit var layoutInflater:LayoutInflater

    init {
        try {
            layoutInflater = LayoutInflater.from(context)
            rootView = layoutInflater.inflate(getLayoutID(),null)
            ButterKnife.bind(this,rootView)
        } catch(e: Exception){
            throw RuntimeException("传进去的Context必须来自Activity或者Fragment！")
        }
    }

    protected abstract fun getLayoutID():Int

    protected fun log(content:String){
        Log.i(TAG,content)
    }
    protected fun logv(content:String){
        Log.v(TAG,content)
    }
    protected fun logd(content:String){
        Log.d(TAG,content)
    }
    protected fun logw(content:String){
        Log.w(TAG,content)
    }
    protected fun loge(content:String){
        Log.e(TAG,content)
    }
}
