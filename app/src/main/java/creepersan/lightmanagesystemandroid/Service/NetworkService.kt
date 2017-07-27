package creepersan.lightmanagesystemandroid.Service

import creepersan.lightmanagesystemandroid.Base.BaseService
import creepersan.lightmanagesystemandroid.Callback.StringCallback
import creepersan.lightmanagesystemandroid.Decoder.DeviceListHtml
import creepersan.lightmanagesystemandroid.Decoder.LoginHtml
import creepersan.lightmanagesystemandroid.Event.GetDeviceListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListResultEvent
import creepersan.lightmanagesystemandroid.Event.LoginEvent
import creepersan.lightmanagesystemandroid.Event.LoginResultEvent
import creepersan.lightmanagesystemandroid.Helper.UrlHelper
import creepersan.lightmanagesystemandroid.Item.Device
import okhttp3.*
import org.greenrobot.eventbus.Subscribe
import java.io.IOException

class NetworkService:BaseService(){
    val httpClient = OkHttpClient()
    val isDebugging = true

    /**
     *      EventBus
     */
    @Subscribe()    //登陆请求
    fun onLoginEvent(event: LoginEvent){
        if (isDebugging){
            postEventDelay(LoginResultEvent(true,true,"", LoginHtml("")),300)
            return
        }
        request(UrlHelper.getLoginUrl(event.userName,event.password),object:StringCallback(){
            override fun onResponse(call: Call, response: Response, result: String) {
                val loginHtml = LoginHtml(result)
                if (loginHtml.isSuccess()){
                    postEvent(LoginResultEvent(true,true,"登陆成功", loginHtml))
                }else{
                    postEvent(LoginResultEvent(false,true,"登陆失败，请检查你的用户名或者密码", loginHtml))
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                postEvent(LoginResultEvent(false,false,"网络连接失败，请检查网络连接以及设备状况", LoginHtml("")))
            }
        })
    }
    @Subscribe()    //刷新设备请求
    fun onGetDeviceListEvent(event:GetDeviceListEvent){
        if (event.isForceRefresh){//假如是要刷新数据，而不是简单的获取数据
            if (isDebugging){
                //模拟数据
                val deviceList = ArrayList<Device>()
                for (i in 0..2){
                    val deviceTemp = Device("大灯 #${(Math.random()*10).toInt()}","控制设备","节点 #${(Math.random()*10).toInt()}",Math.random()>0.4)
                    deviceList.add(deviceTemp)
                }
                postEventDelay(GetDeviceListResultEvent(true,true,deviceList),500)
                return
            }
            //真实的请求
            request(UrlHelper.getDeviceStatusUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    val deviceListHtml = DeviceListHtml(result)
                    postEvent(GetDeviceListResultEvent(deviceListHtml.isSuccess(),true,deviceListHtml.getDeviceList()))
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(GetDeviceListResultEvent(false,false,ArrayList()))
                }
            })
        }
    }


    fun request(url: String,callback: Callback){
        request(url,"GET",callback)
    }
    fun request(url:String,method:String,callback:Callback){
        val requestBuilder = Request.Builder()
        requestBuilder.url(url).method(method,null)
        val request = requestBuilder.build()
        val call = httpClient.newCall(request)
        call.enqueue(callback)
    }
}
