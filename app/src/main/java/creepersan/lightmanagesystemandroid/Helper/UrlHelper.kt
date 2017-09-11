package creepersan.lightmanagesystemandroid.Helper

import android.util.Log
import creepersan.lightmanagesystemandroid.Item.Device

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

    /**
     *      setupDeviceState
     */
    fun getStuupDeviceUrl(deviceList:ArrayList<Device>):String{
        var bright2 = 0 //卧室大灯亮度
        var sw_sta2 = 0 //id 2
        var bright3b = 0 //卧室夜灯亮度
        var sw_sta3b = 0 //id 11
        var bright4 = 0 //厨房警灯
        var sw_sta4 = 0 //id 4
        var bright6 = 0 //楼道大灯
        var sw_sta6 = 0 //id 6
        var bright7 = 0 //客厅大灯
        var sw_sta7 = 0 //id 7
        var bright8 = 0 //厨房大灯
        var sw_sta8 = 0 //id 8
        var bright3a = 0 //客厅彩灯
        var sw_sta3a = 0 //id 10
        var bind3 = 0 //卧室光感设备 id 3
        var bind5 = 0 //楼道声感设备 id 5
        var bind9 = 0 //客厅光感设备 id 9
        var bind3c = 0 //厨房光感 id 12
        var bind3d = 0  //厨房气体感应 id 13
        for (device in deviceList){
            if (device.node.toString() == "2"){//卧室大灯亮度   调亮度
                bright2 = device.params.toInt()
                if (device.status.toInt()== 0) sw_sta2 = 1 else sw_sta2 = 0
            }else if (device.node.toString() == "11"){//卧室夜灯亮度  调亮度
                bright3b = device.params.toInt()
                if (device.status.toInt()==0) sw_sta3b = 1 else sw_sta3b = 0
            }else if (device.node.toString() == "4"){//厨房警灯 只有开关
                bright4 = device.params.toInt()
                if (device.status=="1") sw_sta4 = 1 else sw_sta4 = 0
            }else if (device.node.toString() == "6"){//楼道大灯  只有开关
                bright6 = device.params.toInt()
                if (device.status=="1") sw_sta6 = 1 else sw_sta6 = 0
            }else if (device.node.toString() == "7"){//客厅大灯 调亮度
                bright7 = device.params.toInt()
                if (device.status=="1") sw_sta7 = 1 else sw_sta7 = 0
            }else if (device.node.toString() == "8"){//厨房大灯  只有开关
                bright8 = device.params.toInt()
                if (device.status=="1") sw_sta8 = 1 else sw_sta8 = 0
            }else if (device.node.toString() == "10"){//客厅彩灯  只有开关
                bright3a = device.params.toInt()
                if (device.status=="1") sw_sta3a = 1 else sw_sta3a = 0
            }else if (device.node.toString() == "3"){//卧室光感设备 id 3
                bind3 = device.subDevice.toInt()
            }else if (device.node.toString() == "5"){//楼道声感设备 id 5
                bind5 = device.subDevice.toInt()
            }else if (device.node.toString() == "9"){//客厅光感设备 id 9
                bind9 = device.subDevice.toInt()
            }else if (device.node.toString() == "12"){//厨房光感 id 12
                bind3c = device.subDevice.toInt()
            }else if (device.node.toString() == "13"){//厨房气体感应 id 13
                bind3d = device.subDevice.toInt()
            }
        }
        val url = "http://192.168.1.248/cgi-bin/dev_con_post.cgi?" +
                "bright2=${bright2}&sw_sta2=${sw_sta2}&" +
                "bright%3B=${bright3b}&sw_sta%3B=${sw_sta3b}&" +
                "bright4=${bright4}&sw_sta4=${sw_sta4}&" +
                "bright6=${bright6}&sw_sta6=${sw_sta6}&" +
                "bright7=${bright7}&sw_sta7=${sw_sta7}&" +
                "bright8=${bright8}&sw_sta8=${sw_sta8}&" +
                "bright%3A=${bright3a}&sw_sta%3A=${sw_sta3a}&" +
                "bind3=${bind3}&" +
                "bind5=${bind5}&" +
                "bind9=${bind9}&" +
                "bind%3C=${bind3c}&" +
                "bind%3D=${bind3d}"
        Log.i("tag","请求的网址为 $url")
        return url
    }

}