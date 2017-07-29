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
class AddDeviceResultEvent(val isAdded:Boolean,val isConnected: Boolean)
class RemoveDeviceEvent(val device: Device)
class RemoveDeviceResultEvent(val isDeleted:Boolean,var isConnected: Boolean)
class DeviceSwitchEvent(val device: Device,val newState:Boolean)
class DeviceSwitchResultEvent(val isSuccess: Boolean, val isConnected: Boolean, val newState: Boolean, val device: Device)
/**
 *      区域相关事件
 */
class GetAreaListEvent(val isForceRefresh: Boolean)
class GetAreaListResultEvent(val isSuccess:Boolean,val isConnected:Boolean,val areaList:ArrayList<Area>)
class AddAreaEvent(val area: Area)
class AddAreaResultEvent(val isAdded:Boolean,val isConnected: Boolean)
class RemoveAreaEvent(val area: Area)
class RemoveAreaResultEvent(val isDeleted:Boolean,var isConnected: Boolean)
class AreaSwitchEvent(val area: Area,val newState:Boolean)
class AreaSwitchResultEvent(val isSuccess: Boolean,val isConnected: Boolean,val current: Boolean,val area: Area)


class StringEvent(val string: String)