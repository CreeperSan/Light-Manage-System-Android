package creepersan.lightmanagesystemandroid.Decoder

import creepersan.lightmanagesystemandroid.Base.BaseHtml

class LoginHtml(srcString:String):BaseHtml(srcString){

    override fun decodeString(srcString: String) {
//        if (srcString.contains("admin") && srcString.contains("123456")){
//            isSuccess = true
//        }
        if (srcString.contains("登录成功,点击进入主页界面")){
            isSuccess = true
        }
    }

    private var isSuccess = false

    fun isSuccess():Boolean = isSuccess

}
