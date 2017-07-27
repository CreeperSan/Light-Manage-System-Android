package creepersan.lightmanagesystemandroid.Service

import creepersan.lightmanagesystemandroid.Base.BaseService
import creepersan.lightmanagesystemandroid.Event.GetDeviceListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListResultEvent
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.Subscribe

class InfoService:BaseService(){
    private var deviceList = ArrayList<Device>()
    private var isDeviceSuccess = false
    private var isDeviceConnected = false
    private var isDeviceFromSelf = false
    private var isAreaFromSelf = false

    @Subscribe
    fun onGetDeviceListEvent(event:GetDeviceListEvent){
        if (!event.isForceRefresh){//假如不是强制刷新，只是信息的获取
            isDeviceFromSelf = true
            postEvent(GetDeviceListResultEvent(isDeviceSuccess,isDeviceConnected,deviceList))
        }
    }
    @Subscribe
    fun onGetDeviceListResultEvent(event:GetDeviceListResultEvent){//刷新信息
        if (isDeviceFromSelf){
            isDeviceFromSelf = false
            return
        }
        deviceList = event.deviceList
        isDeviceConnected = event.isConnected
        isDeviceSuccess = event.isSuccess
    }



}
