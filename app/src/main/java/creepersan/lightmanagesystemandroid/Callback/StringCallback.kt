package creepersan.lightmanagesystemandroid.Callback

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

abstract class StringCallback:Callback{

    override fun onResponse(call: Call, response: Response) {
//        onResponse(call, response, String(response.body()!!.string().toByteArray(Charset.forName("UTF-8"))))
        onResponse(call, response, response.body()!!.string())
    }

    abstract fun onResponse(call: Call,response: Response,result:String)
}
