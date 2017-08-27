package creepersan.lightmanagesystemandroid.Service

import android.widget.Toast
import creepersan.lightmanagesystemandroid.Base.BaseService
import creepersan.lightmanagesystemandroid.Callback.StringCallback
import creepersan.lightmanagesystemandroid.Decoder.*
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Helper.UrlHelper
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device
import okhttp3.*
import org.greenrobot.eventbus.Subscribe
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class NetworkService:BaseService(){
    val httpClient = OkHttpClient.Builder().connectTimeout(5,TimeUnit.SECONDS).readTimeout(5,TimeUnit.SECONDS).build()
    val isDebugging = true  //是否为调试的关键标志

    /**
     *      EventBus
     */
    @Subscribe      //登陆请求
    fun onLoginEvent(event: LoginEvent){
        if (isDebugging){
            val stringGet = "<html><title>智能灯光管理系统</title><link href=\"css/noneline.css\" rel=\"stylesheet\" type=\"text/css\"><body TOPMARGIN=\"100\"><div align=\"center\"><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">GET</td></tr></table><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">(null)</td></tr></table><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">userName=admin&userPassword=123456</td></tr></table><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">admin</td></tr></table><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">123456</td></tr></table><table bgcolor=\"#ffffff\" width=382 border=1 align=\"center\" cellpadding=\"0\" cellspacing=\"0\" bordercolor=\"#ff6600\" ><tr><td align=\"center\">登录成功,点击进入主页界面.</td></tr></table><a href=\"/cgi-bin/dev_add.cgi\" style=\"float:right;margin-right:20px;\"><font size=\"2\">进入主页</font></ a></div></body></html> "
            val loginHtml = LoginHtml(stringGet)
            postEventDelay(LoginResultEvent(loginHtml.isSuccess(),true,"", loginHtml),300)
            return
        }
        request(UrlHelper.getLoginUrl(event.userName,event.password),object:StringCallback(){
            override fun onResponse(call: Call, response: Response, result: String) {
                postEvent(StringEvent("返回的消息 : ${result}"))
                val loginHtml = LoginHtml(result)
                if (loginHtml.isSuccess()){
//                    postEventDelay(LoginResultEvent(loginHtml.isSuccess(),true,"", loginHtml),300)
                    postEvent(LoginResultEvent(true,true,"登陆成功", loginHtml))
                }else{
//                    postEventDelay(LoginResultEvent(loginHtml.isSuccess(),true,"", loginHtml),300)
                    postEvent(LoginResultEvent(false,true,"登陆失败，请检查你的用户名或者密码", loginHtml))
                }
            }
            override fun onFailure(call: Call?, e: IOException?) {
                postEvent(StringEvent("返回的消息 : 连不上服务器"))
//                postEventDelay(LoginResultEvent(true,true,"",LoginHtml("")),300)
                postEvent(LoginResultEvent(false,false,"网络连接失败，请检查网络连接以及设备状况", LoginHtml("")))
            }
        })
        postEvent(StringEvent("已发送连接请求"))
    }
    @Subscribe     //刷新设备请求
    fun onGetDeviceListEvent(event:GetDeviceListEvent){
        if (event.isForceRefresh){//假如是要刷新数据，而不是简单的获取数据
            if (isDebugging){
                //模拟数据
                val random = Random()
                val deviceList = ArrayList<Device>()
                for (i in 0..2+random.nextInt(12)){
                    val deviceTemp = Device("大灯 #${random.nextInt(100)}","控制设备","节点 #${random.nextInt(100)}",Math.random()>0.4)
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
    @Subscribe      //刷新区域请求
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
    @Subscribe      //添加设备请求
    fun onAddDeviceEvent(event:AddDeviceEvent){
        if (isDebugging){
            postEventDelay(AddDeviceResultEvent(true,true),500)
        }else{
            request(UrlHelper.getDeviceAddUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    val deviceHtml = DeviceAddHtml(result)
                    if (deviceHtml.isSuccess()){
                        postEvent(AddDeviceResultEvent(true,true))
                    }else{
                        postEvent(AddDeviceResultEvent(false,true))
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(AddDeviceResultEvent(false,false))
                }
            })
        }
    }
    @Subscribe      //删除设备请求
    fun onRemoveDeviceEvent(event:RemoveDeviceEvent){
        if (isDebugging){
            postEventDelay(RemoveDeviceResultEvent(true,true),300)//通知设备删除成功
        }else{
            request(UrlHelper.getDeviceRemoveUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    val deviceRemoveHtml = DeviceRemoveHtml(result)
                    if (deviceRemoveHtml.isSuccess()){
                        postEvent(RemoveDeviceResultEvent(true,true))
                    }else{
                        postEvent(RemoveDeviceResultEvent(false,true))
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(RemoveDeviceResultEvent(false,false))
                }
            })
        }
    }
    @Subscribe
    fun onDeviceSwitchEvent(event:DeviceSwitchEvent){
        if(isDebugging){
            postEventDelay(DeviceSwitchResultEvent(true,true,event.newState,event.device),500)
        }else{
            val device = event.device
            request(UrlHelper.getDeviceOnUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    val deviceSwitchHtml = DeviceSwitchHtml(result)
                    if (deviceSwitchHtml.isSuccess()){
                        postEvent(DeviceSwitchResultEvent(true,true,deviceSwitchHtml.getNewState(),device))
                    }else{
                        postEvent(DeviceSwitchResultEvent(false,true,!event.newState,device))
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(DeviceSwitchResultEvent(false,false,false,device))
                }
            })
        }
    }
    @Subscribe      //添加区域请求
    fun on(event:AddAreaEvent){
        if(isDebugging){
            postEventDelay(AddAreaResultEvent(true,true),800)
        }else{
            request(UrlHelper.getAreaAddUrl(),object : StringCallback(){
                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(AddAreaResultEvent(false,false))
                }
                override fun onResponse(call: Call, response: Response, result: String) {
                    val addAreaHtml = AreaAddHtml(result)
                    if (addAreaHtml.isSuccess()){
                        postEvent(AddAreaResultEvent(true,true))
                    }else{
                        postEvent(AddAreaResultEvent(false,false))
                    }
                }
            })
        }
    }
    @Subscribe      //删除区域请求
    fun onRemoveAreaEvent(event:RemoveAreaEvent){
        if (isDebugging){
            postEventDelay(RemoveAreaResultEvent(true,true),300)
        }else{
            request(UrlHelper.getAreaRemoveUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    val areaRemoveEvent = AreaRemoveHtml(result)
                    if (areaRemoveEvent.isSuccess()){
                        postEvent(RemoveAreaResultEvent(true,true))
                    }else{
                        postEvent(RemoveAreaResultEvent(false,true))
                    }
                }
                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(RemoveAreaResultEvent(false,false))
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
