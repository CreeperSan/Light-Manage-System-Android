package creepersan.lightmanagesystemandroid.Service

import creepersan.lightmanagesystemandroid.Base.BaseService
import creepersan.lightmanagesystemandroid.Callback.StringCallback
import creepersan.lightmanagesystemandroid.Decoder.DeviceListHtml
import creepersan.lightmanagesystemandroid.Decoder.DeviceSwitchHtml
import creepersan.lightmanagesystemandroid.Decoder.LoginHtml
import creepersan.lightmanagesystemandroid.Event.*
import creepersan.lightmanagesystemandroid.Helper.DatabaseHelper
import creepersan.lightmanagesystemandroid.Helper.UrlHelper
import okhttp3.*
import org.greenrobot.eventbus.Subscribe
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class NetworkService:BaseService(){
    val httpClient = OkHttpClient.Builder().connectTimeout(5,TimeUnit.SECONDS).readTimeout(5,TimeUnit.SECONDS).build()
    val isDebugging = false  //是否为调试的关键标志

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
//                val data = "{\"sensor\":[ {\"sensor_name\":\"卧室光感\", \"bind_device\":\"2\",\"device_id\":\"1\" }, {\"sensor_name\":\"楼道声感\", \"bind_device\":\"6\",\"device_id\":\"2\" }, {\"sensor_name\":\"客厅光感\", \"bind_device\":\"4\",\"device_id\":\"3\" }, {\"sensor_name\":\"厨房光感\", \"bind_device\":\"7\",\"device_id\":\"60\" }, {\"sensor_name\":\"厨房煤感\", \"bind_device\":\"4\",\"device_id\":\"20\" }] , \"light\":[ {\"light_device\":\"卧室大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"5\"}, {\"light_device\":\"厨房警灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"6\"}, {\"light_device\":\"楼道大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"7\"}, {\"light_device\":\"客厅大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"8\"}, {\"light_device\":\"厨房大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"9\"}, {\"light_device\":\"客厅彩灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"10\"}, {\"light_device\":\"卧室夜灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"11\"}]}"
                val data = "{\"sensor\":[ {\"sensor_name\":\"卧室光感\", \"bind_device\":\"5\",\"device_id\":\"1\" }, {\"sensor_name\":\"楼道声感\", \"bind_device\":\"0\",\"device_id\":\"2\" }, {\"sensor_name\":\"客厅光感\", \"bind_device\":\"4\",\"device_id\":\"3\" }, {\"sensor_name\":\"厨房光感\", \"bind_device\":\"7\",\"device_id\":\"60\" }, {\"sensor_name\":\"厨房煤感\", \"bind_device\":\"11\",\"device_id\":\"20\" }] , \"light\":[ {\"light_device\":\"卧室大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"5\"}, {\"light_device\":\"厨房警灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"6\"}, {\"light_device\":\"楼道大灯\", \"bright\":\"7\", \"sw_sta\":\"0\",\"device_id\":\"7\"}, {\"light_device\":\"客厅大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"8\"}, {\"light_device\":\"厨房大灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"9\"}, {\"light_device\":\"客厅彩灯\", \"bright\":\"0\", \"sw_sta\":\"0\",\"device_id\":\"10\"}, {\"light_device\":\"卧室夜灯\", \"bright\":\"5\", \"sw_sta\":\"0\",\"device_id\":\"11\"}]}"
                val deviceHtml = DeviceListHtml(data)
                return
            }
            //真实的请求
            request(UrlHelper.getDeviceStatusUrl(),object : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    log("onResponse")
                    val deviceListHtml = DeviceListHtml(result)
                    //提交事件交由Decoder处理
//                    postEvent(GetDeviceListResultEvent(deviceListHtml.isSuccess(),true,deviceListHtml.deviceList))
                    log("")
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    log("onFailure")
                    postEvent(GetDeviceListResultEvent(false,false,ArrayList()))
                }
            })
        }
    }
    @Subscribe      //刷新区域请求
    fun onGetAreaListEvent(event:GetAreaListEvent){
        postStickEvent(GetAreaListResultEvent(true,true,DatabaseHelper.Instance.getInstance(this).getAreaList()))
    }
    @Subscribe      //添加设备请求
    fun onAddDeviceEvent(event:AddDeviceEvent){
    }
    @Subscribe      //删除设备请求
    fun onRemoveDeviceEvent(event:RemoveDeviceEvent){
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
        DatabaseHelper.Instance.getInstance(this).insertArea(event.area)
        postEvent(AddAreaResultEvent(true,true))
    }
    @Subscribe      //删除区域请求
    fun onRemoveAreaEvent(event:RemoveAreaEvent){
        loge("删除区域还没有完成")
    }
    @Subscribe
    fun onSetupDeviceStateEvent(event:SetupDeviceStateEvent){
        if (isDebugging){
            postEvent(SetupDeviceStateResultEvent(true))
        }else{
            request(event.url,object  : StringCallback(){
                override fun onResponse(call: Call, response: Response, result: String) {
                    try {
                        val mainJson = JSONObject(result)
                        if (mainJson.optString("statue") == "successs"){
                            postEvent(SetupDeviceStateResultEvent(true))
                        }else{
                            postEvent(SetupDeviceStateResultEvent(false))
                        }
                    } catch (e: Exception) {
                        postEvent(SetupDeviceStateResultEvent(true))
                    }
                }

                override fun onFailure(call: Call?, e: IOException?) {
                    postEvent(SetupDeviceStateResultEvent(false))
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
