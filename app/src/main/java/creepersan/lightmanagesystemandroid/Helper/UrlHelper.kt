package creepersan.lightmanagesystemandroid.Helper

object UrlHelper {

    fun getLoginUrl(userName:String,password:String):String{
        return "http://192.168.1.248/cgi-bin/login_post.cgi?userName=${userName}&userPassword=${password}"
    }

    /**
     *      设备管理相关
     */
    fun getDeviceStatusUrl():String = "http://192.168.1.248/cgi-bin/dev_add.cgi"
    fun getDeviceOnUrl():String = "http://192.168.1.248/cgi-bin/dev_add_post.cgi"
    fun getDeviceOffUrl():String =  "http://192.168.1.248/cgi-bin/dev_add_post.cgi"
    fun getDeviceAddUrl():String = ""
    fun getDeviceRemoveUrl():String = ""

    /**
     *      区域管理相关
     */
    fun getAreaStatusUrl():String = ""
    fun getAreaOnUrl():String = ""
    fun getAreaOffUrl():String = ""
    fun getAreaAddUrl():String = ""
    fun getAreaRemoveUrl():String = ""

}