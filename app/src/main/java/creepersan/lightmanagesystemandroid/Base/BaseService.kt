package creepersan.lightmanagesystemandroid.Base

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.util.Log
import creepersan.lightmanagesystemandroid.Helper.CommandHelper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

open class BaseService:Service(){
    private var TAG = javaClass.simpleName
    get set
    private val handler = Handler()

    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

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

    @Subscribe
    fun onStringCmdEvent(command:String){
        if (command == CommandHelper.EXIT){
            stopSelf()
        }
    }
    fun postEvent(event:Any){
        EventBus.getDefault().post(event)
    }
    fun postStickEvent(event: Any){
        EventBus.getDefault().postSticky(event)
    }
    fun postEventDelay(event: Any,delay:Long){
        handler.postDelayed({
            postEvent(event)
        },delay)
    }
}