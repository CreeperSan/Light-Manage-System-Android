package creepersan.lightmanagesystemandroid.Base

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife

abstract class BaseActivity:AppCompatActivity(){

    private var TAG = javaClass.simpleName
    get set
    protected val context:Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutID())
        ButterKnife.bind(this)
    }

    protected abstract fun getLayoutID():Int;

    fun log(content:String){
        Log.i(TAG,content)
    }
    fun logv(content:String){
        Log.v(TAG,content)
    }
    fun logd(content:String){
        Log.d(TAG,content)
    }
    fun logw(content:String){
        Log.w(TAG,content)
    }
    fun loge(content:String){
        Log.e(TAG,content)
    }
    fun toast(content:String){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show()
    }
    fun toastLong(content:String){
        Toast.makeText(context,content,Toast.LENGTH_LONG).show()
    }

}