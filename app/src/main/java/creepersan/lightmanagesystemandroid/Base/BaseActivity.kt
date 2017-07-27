package creepersan.lightmanagesystemandroid.Base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

abstract class BaseActivity:AppCompatActivity(){

    private var TAG = javaClass.simpleName
    get set
    protected val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
        EventBus.getDefault().register(this)
        ButterKnife.bind(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    protected abstract fun getLayoutID():Int;

    /**
     *      调试方法
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
    protected fun toast(content:String){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
    }
    protected fun toastLong(content:String){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show()
    }

    /**
     *      开启新的Activity
     */

    protected fun <T> startActivity(cls:Class<T>,isFinish:Boolean){
        val intent = Intent(this,cls)
        startActivity(intent)
        if (isFinish) finish()
    }
    protected fun <T> startActivity(cls:Class<T>){
        startActivity(cls,false)
    }
    protected fun <T> startService(clazz: Class<T>){
        startService(Intent(this,clazz))
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