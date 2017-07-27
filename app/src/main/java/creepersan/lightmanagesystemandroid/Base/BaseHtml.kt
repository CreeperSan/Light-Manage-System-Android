package creepersan.lightmanagesystemandroid.Base

abstract class BaseHtml(val srcString:String){

    init {
        decodeString(srcString)
    }

    abstract fun decodeString(srcString: String)

}