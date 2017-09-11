package creepersan.lightmanagesystemandroid.Activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import creepersan.lightmanagesystemandroid.Event.GetAreaListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListResultEvent
import creepersan.lightmanagesystemandroid.Item.Area
import creepersan.lightmanagesystemandroid.Item.Device
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class AreaControlActivity : BaseActivity(){
    @BindView(R.id.areaControlRecyclerView)lateinit var recyclerView:RecyclerView

    private lateinit var area:Area
    private var deviceList:ArrayList<Device> = ArrayList()
    private var bindDeviceList = ArrayList<Device>()
    private var adapter:RecyclerAdapter? = null

    override fun getLayoutID(): Int  = R.layout.activity_area_control

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initActionBar()
        initRecyclerView()
    }



    private fun initActionBar() {
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        //初始化名字
        try {
            val name = area.name
            setTitle(name)
        } catch (e: Exception) {
            setTitle("未知区域")
        }
    }
    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
    private fun initData() {
        area = intent.getSerializableExtra(INTENT_KEY.AREA) as Area
        if (area == null){
            toast("无法找到区域，请重试")
            postEvent(GetAreaListEvent(true))
            finish()
            return
        }
        postEvent(GetDeviceListEvent(true))

    }
    private fun initLater() {
        for (deviceStr in area.deviceList){
            for (deviceTemp in deviceList){
                if (deviceTemp.node == deviceStr){
                    bindDeviceList.add(deviceTemp)
                }
            }
        }
        //初始化列表数据
        if (adapter==null){
            adapter = RecyclerAdapter()
            recyclerView.adapter = adapter
        }else{
            adapter!!.notifyDataSetChanged()
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onGetDeviceListResultEvent(event:GetDeviceListResultEvent){
        deviceList = event.deviceList
        initLater()
    }


    inner class RecyclerHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var deviceTypeImage:ImageView = itemView.findViewById<ImageView>(R.id.itemDeviceControlTypeImage)
        var deviceName:TextView = itemView.findViewById<TextView>(R.id.itemDeviceControlTitle)
        var deviceDetail:TextView = itemView.findViewById<TextView>(R.id.itemDeviceControlSubtitle)
        var brightness:ImageView = itemView.findViewById<ImageView>(R.id.itemDeviceControlBrightness)
        var bind:ImageView = itemView.findViewById<ImageView>(R.id.itemDeviceControlBind)
        var switch:Switch = itemView.findViewById<Switch>(R.id.itemDeviceControlSwitch)

    }
    inner class RecyclerAdapter:RecyclerView.Adapter<RecyclerHolder>(){
        override fun onBindViewHolder(holder: RecyclerHolder, position: Int) {
            val deviceTemp = bindDeviceList[position]
            when(deviceTemp.type){
                Device.TYPE.SENSOR -> {
                    holder.deviceTypeImage.setImageResource(R.drawable.ic_sensor)
                    holder.brightness.visibility = View.GONE
                    holder.bind.visibility = View.VISIBLE
                }
                Device.TYPE.LIGHT_NORMAL -> {
                    holder.deviceTypeImage.setImageResource(R.drawable.ic_light_normal)
                    holder.brightness.visibility = View.VISIBLE
                    holder.bind.visibility = View.GONE

                }
                Device.TYPE.LIGHT_COLOR -> {
                    holder.deviceTypeImage.setImageResource(R.drawable.ic_light_colorful)
                    holder.brightness.visibility = View.GONE
                    holder.bind.visibility = View.GONE

                }
                else ->{
                    holder.deviceTypeImage.setImageResource(R.drawable.ic_unknown)
                    holder.brightness.visibility = View.GONE
                    holder.bind.visibility = View.GONE

                }
            }
            holder.deviceName.text = deviceTemp.name
        }

        override fun getItemCount(): Int = bindDeviceList.size

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerHolder =
                RecyclerHolder(layoutInflater.inflate(R.layout.item_device_control,parent,false))

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    object INTENT_KEY{
        val TITLE = "Title"
        val ID = "ID"
        val AREA = "Area"
    }

}