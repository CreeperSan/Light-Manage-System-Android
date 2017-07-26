package creepersan.lightmanagesystemandroid.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import creepersan.lightmanagesystemandroid.Activity.R
import creepersan.lightmanagesystemandroid.Base.BaseCardFragment
import creepersan.lightmanagesystemandroid.Component.CardComponent
import creepersan.lightmanagesystemandroid.Component.CardItemComponent

class DeviceFragment:BaseCardFragment(){
    private lateinit var normalDeviceCard:CardComponent
    private lateinit var inductionDeviceCard:CardComponent
    private lateinit var otherDeviceCard:CardComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewInflated() {
        initCards()
        initFloatingButton()
    }


    private fun initCards() {
        normalDeviceCard = CardComponent(activity)
        inductionDeviceCard = CardComponent(activity)
        otherDeviceCard = CardComponent(activity)
        normalDeviceCard.setTitle("控制设备")
        normalDeviceCard.setEmptyHintTextViewText("尚未添加控制设备")
        inductionDeviceCard.setTitle("感应设备")
        inductionDeviceCard.setEmptyHintTextViewText("尚未添加感应设备")
        otherDeviceCard.setTitle("其他设备")
        otherDeviceCard.setEmptyHintTextViewText("尚未添加其他设备")
        addComponent(normalDeviceCard)
        addComponent(inductionDeviceCard)
        addComponent(otherDeviceCard)
    }
    private fun initFloatingButton() {
        setFloatingActionOnClickListener(View.OnClickListener { view ->
            val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_device_add_content,null)
            val dialogRunnable = Runnable {
                val deviceName = dialogView.findViewById<EditText>(R.id.dialogDeviceAddDeviceName).text.toString().trim()
                val deviceType = dialogView.findViewById<EditText>(R.id.dialogDeviceAddDeviceType).text.toString().trim()
                val pointInfo = dialogView.findViewById<EditText>(R.id.dialogDeviceAddPointInfo).text.toString().trim()
                toast("设备名称${deviceName}\n设备类型${deviceType}\n节点信息${pointInfo}")
            }
            showDialog("设备添加", R.drawable.background_dialog_add_device,dialogView,dialogRunnable)
        })
    }

}
