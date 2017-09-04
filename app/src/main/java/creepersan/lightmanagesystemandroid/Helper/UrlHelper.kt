package creepersan.lightmanagesystemandroid.Helper

object UrlHelper {

    fun getLoginUrl(userName:String,password:String):String = "http://192.168.1.248/cgi-bin/login_post.cgi?userName=admin&userPassword=123456"

    /**
     *      设备管理相关
     */
    fun getDeviceStatusUrl():String = URL.URL_MANAGE
    fun getDeviceOnUrl():String = URL.URL_MANAGE
    fun getDeviceOffUrl():String =  URL.URL_MANAGE
    fun getDeviceAddUrl():String = URL.URL_MANAGE
    fun getDeviceRemoveUrl():String = URL.URL_MANAGE

    /**
     *      区域管理相关
     */
    fun getAreaStatusUrl():String = URL.URL_MANAGE
    fun getAreaOnUrl():String = URL.URL_MANAGE
    fun getAreaOffUrl():String = URL.URL_MANAGE
    fun getAreaAddUrl():String = URL.URL_MANAGE
    fun getAreaRemoveUrl():String = URL.URL_MANAGE

    object URL{
        val URL_MANAGE = "http://192.168.1.248/cgi-bin/dev_con.cgi"
    }

}