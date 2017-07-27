package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml
import creepersan.lightmanagesystemandroid.Item.Area

class AreaListHtml(srcString: String):BaseHtml(srcString){
    private val areaList = ArrayList<Area>()
    private val isSuccess = false

    override fun decodeString(srcString: String) {

    }

    fun getAreaList():ArrayList<Area> = areaList
    fun isSuccess():Boolean = isSuccess
}