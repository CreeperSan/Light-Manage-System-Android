package creepersan.lightmanagesystemandroid.Service

import creepersan.lightmanagesystemandroid.Base.BaseService
import creepersan.lightmanagesystemandroid.Callback.StringCallback
import creepersan.lightmanagesystemandroid.Decoder.AreaListHtml
import creepersan.lightmanagesystemandroid.Decoder.DeviceListHtml
import creepersan.lightmanagesystemandroid.Decoder.LoginHtml
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Helper.UrlHelper
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device
import okhttp3.*
import org.greenrobot.eventbus.Subscribe
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class NetworkService:BaseService(){
    val httpClient = OkHttpClient()
    val isDebugging = false  //是否为调试的关键标志

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
    @Subscribe()    //刷新区域请求
    fun onGetAreaListEvent(event:GetAreaListEvent){
        if(event.isForceRefresh){//如果是强制刷新
            if (isDebugging){//模拟数据
                val random = Random()
                val areaList = ArrayList<Area>()
                for (i in 0..random.nextInt(3)+1){
                    val deviceList = ArrayList<Device>()
                    for (j in 0..random.nextInt(5)){
                        val device = Device("灯光设备 #${random.nextInt(99)}","控制设备","节点 #${random.nextInt(50)}",random.nextBoolean())
                        deviceList.add(device)
                    }
                    val area = Area("区域 #${random.nextInt(20)}",deviceList,random.nextInt(3))
                    areaList.add(area)
                }
                postEventDelay(GetAreaListResultEvent(true,true,areaList),500)
            }else{//真实请求
                request(UrlHelper.getAreaStatusUrl(),object : StringCallback(){
                    override fun onResponse(call: Call, response: Response, result: String) {
                        val areaHtml = AreaListHtml(result)
                        postEvent(GetAreaListResultEvent(areaHtml.isSuccess(),true,areaHtml.getAreaList()))
                    }
                    override fun onFailure(call: Call?, e: IOException?) {
                        postEvent(GetAreaListResultEvent(false,false, ArrayList()))
                    }
                })
            }
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
