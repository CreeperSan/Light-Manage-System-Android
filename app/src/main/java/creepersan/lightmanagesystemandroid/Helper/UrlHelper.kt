package creepersan.lightmanagesystemandroid.Helper

object UrlHelper {

    fun getLoginUrl(userName:String,password:String):String{
        return "http://192.168.1.248/cgi-bin/login_post.cgi?userName=${userName}&userPassword=${password}"
    }

    fun getDeviceStatusUrl():String{
        return "http://192.168.1.248/cgi-bin/dev_add.cgi"
    }

    fun getDeviceConnectUrl():String{
        return "http://192.168.1.248/cgi-bin/dev_add_post.cgi"
    }

    fun getDeviceDisconnectUrl():String{
        return "http://192.168.1.248/cgi-bin/dev_add_post.cgi"
    }

}