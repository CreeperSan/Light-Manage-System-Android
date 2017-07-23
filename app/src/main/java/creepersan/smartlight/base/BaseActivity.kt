package creepersan.smartlight.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

open class BaseActivity:AppCompatActivity(){
    private var TAG  = javaClass.simpleName
    get set
    protected var context = this



    init {
        onInit()
    }

    /**
     *      平常的生命周期
     */
    open override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    /**
     *      新的生命周期
     */
    protected fun onInit(){

    }

    /**
     *      调试用的方法
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
        Toast.makeText(this,content,Toast.LENGTH_SHORT).show()
    }
    protected fun toastLong(content:String){
        Toast.makeText(this,content,Toast.LENGTH_LONG).show()
    }

}