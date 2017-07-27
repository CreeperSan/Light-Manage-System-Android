package creepersan.lightmanagesystemandroid.Event

import creepersan.lightmanagesystemandroid.Decoder.LoginHtml
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device

/**
 *      登陆相关事件
 */
class LoginEvent(val userName:String,val password:String)
class LoginResultEvent(val isSuccess:Boolean, val isConnected:Boolean, val reason:String, loginHtml: LoginHtml)
/**
 *      设备相关事件
 */
class GetDeviceListEvent(val isForceRefresh: Boolean)
class GetDeviceListResultEvent(val isSuccess:Boolean,val isConnected:Boolean,val deviceList:ArrayList<Device>)
class AddDeviceEvent(val device: Device)
class RemoveDeviceEvent(val device: Device)
/**
 *      区域相关事件
 */
class GetAreaListEvent(val isForceRefresh: Boolean)
class GetAreaListResultEvent(val isSuccess:Boolean,val isConnected:Boolean,val areaList:ArrayList<Area>)
class AddAreaEvent(val area: Area)
class RemoveAreaEvent(val area: Area)
