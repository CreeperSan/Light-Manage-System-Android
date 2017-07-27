package creepersan.lightmanagesystemandroid.Activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.widget.LinearLayout
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import creepersan.lightmanagesystemandroid.Event.GetAreaListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListEvent
import creepersan.lightmanagesystemandroid.Fragment.AreaFragment
import creepersan.lightmanagesystemandroid.Fragment.DeviceFragment
import creepersan.lightmanagesystemandroid.Fragment.IndexFragment

class ControlActivity:BaseActivity(){
    @BindView(R.id.controlBottomNavigationView)lateinit var navigationView:BottomNavigationView
    @BindView(R.id.controlFragmentLayout)lateinit var fragmentLayout:LinearLayout

    private var currentFragmentPage = FRAGMENT_NAME.INDEX
    private lateinit var indexFragment:IndexFragment
    private lateinit var deviceFragment:DeviceFragment
    private lateinit var areaFragment:AreaFragment

    override fun getLayoutID(): Int = R.layout.activity_control

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
        initBottomNavigationView()
        initFragmentLayout()
        initFinal()
    }



    /**
     *      初始化
     */
    private fun initFragment() {
        indexFragment = IndexFragment()
        deviceFragment = DeviceFragment()
        areaFragment = AreaFragment()
    }
    private fun initBottomNavigationView(){
        navigationView.setOnNavigationItemSelectedListener { it ->
            var newFragmentPageName = FRAGMENT_NAME.INDEX
            when(it.itemId){
                R.id.menuControlBottomIndex ->{
                    newFragmentPageName = FRAGMENT_NAME.INDEX
                }
                R.id.menuControlBottomDevice -> {
                    newFragmentPageName = FRAGMENT_NAME.DEVICE
                }
                R.id.menuControlBottomArea -> {
                    newFragmentPageName = FRAGMENT_NAME.AREA
                }
            }
            if (newFragmentPageName != currentFragmentPage){
                currentFragmentPage = newFragmentPageName
                when(currentFragmentPage){
                    FRAGMENT_NAME.INDEX -> {
                        showIndexFragment()
                    }
                    FRAGMENT_NAME.DEVICE -> {
                        showDeviceFragment()
                    }
                    FRAGMENT_NAME.AREA -> {
                        showAreaFragment()
                    }
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
    private fun initFragmentLayout() {
        supportFragmentManager.beginTransaction()
                .add(fragmentLayout.id,indexFragment)
                .add(fragmentLayout.id,deviceFragment)
                .add(fragmentLayout.id,areaFragment)
                .commit()
    }
    private fun initFinal() {
        navigationView.bringToFront()
        //发送请求
        postEvent(GetDeviceListEvent(true))
        postEvent(GetAreaListEvent(true))
    }

    /**
     *      快捷操作
     */
    private fun showIndexFragment(){
        supportFragmentManager.beginTransaction()
                .show(indexFragment)
                .hide(deviceFragment)
                .hide(areaFragment)
                .commit()
    }
    private fun showDeviceFragment(){
        supportFragmentManager.beginTransaction()
                .hide(indexFragment)
                .show(deviceFragment)
                .hide(areaFragment)
                .commit()
    }
    private fun showAreaFragment(){
        supportFragmentManager.beginTransaction()
                .hide(indexFragment)
                .hide(deviceFragment)
                .show(areaFragment)
                .commit()
    }

    /**
     *      标志位
     */
    companion object FRAGMENT_NAME {
        val INDEX = 1
        val DEVICE = 2
        val AREA = 3
    }
}