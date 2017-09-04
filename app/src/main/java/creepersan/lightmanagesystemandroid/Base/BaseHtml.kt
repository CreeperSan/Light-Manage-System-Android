package creepersan.lightmanagesystemandroid.Base

import android.util.Log

abstract class BaseHtml(val srcString:String){

    init {
        decodeString(srcString)
    }

    abstract fun decodeString(srcString: String)
    protected fun log(content:String) = Log.i(javaClass.simpleName,content)

}