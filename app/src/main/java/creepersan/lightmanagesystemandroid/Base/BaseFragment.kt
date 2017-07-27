package creepersan.lightmanagesystemandroid.Base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseFragment:Fragment(){
    private var TAG = javaClass.simpleName
    get set
    private lateinit var rootView:View

    protected abstract fun getLayoutID():Int;

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = activity.layoutInflater.inflate(getLayoutID(),container,false)
        ButterKnife.bind(this,rootView)
        onViewInflated()
        return rootView
    }

    protected open fun onViewInflated(){}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    /**
     *      调试
     */
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
    protected fun toast(content: String){
        Toast.makeText(activity,content,Toast.LENGTH_SHORT).show()
    }
    protected fun toastLong(content: String){
        Toast.makeText(activity,content,Toast.LENGTH_LONG).show()
    }

    /**
     *      EventBus
     */
    @Subscribe
    fun onStringCmdEvent(command:String){}
    fun postEvent(event:Any){
        EventBus.getDefault().post(event)
    }
}