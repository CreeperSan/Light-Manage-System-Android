package creepersan.lightmanagesystemandroid.Callback

import android.view.View
import android.widget.AdapterView

abstract class SpinnerItemSelectListener : AdapterView.OnItemSelectedListener{
    var isFirstTime = false

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        if (!isFirstTime){
            isFirstTime = true
            return
        }
        onItemPick(p0, p1, p2, p3)
    }

    abstract fun onItemPick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long)

    //TODO 记得处理spinner的默认调用onItemSelected犯方法

}