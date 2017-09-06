package creepersan.lightmanagesystemandroid.Activity

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.Menu
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import butterknife.BindView
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import creepersan.lightmanagesystemandroid.Event.GetAreaListEvent
import creepersan.lightmanagesystemandroid.Event.GetDeviceListEvent
import creepersan.lightmanagesystemandroid.Fragment.AreaFragment
import creepersan.lightmanagesystemandroid.Fragment.DeviceFragment
import creepersan.lightmanagesystemandroid.Fragment.IndexFragment
import creepersan.lightmanagesystemandroid.Helper.CommandHelper

class ControlActivity:BaseActivity(){
    @BindView(R.id.controlBottomNavigationView)lateinit var navigationView:BottomNavigationView
    @BindView(R.id.controlFragmentLayout)lateinit var fragmentLayout:LinearLayout

    private var currentFragmentPage = FRAGMENT_NAME.INDEX
    private lateinit var indexFragment:IndexFragment
    private lateinit var deviceFragment:DeviceFragment
    private lateinit var areaFragment:AreaFragment
    private lateinit var fragmentSwitchAnimation:Animation

    override fun getLayoutID(): Int = R.layout.activity_control

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initFragment()
        initAnimation()
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
    private fun initAnimation() {
        fragmentSwitchAnimation = AnimationUtils.loadAnimation(context,R.anim.control_fragment_switch)
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
                fragmentLayout.startAnimation(fragmentSwitchAnimation)//动画
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
        title = "智能灯光控制"
    }

    /**
     *      菜单
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.control_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuControlSetting ->{
                startActivity(SettingActivity::class.java)
            }
            R.id.menuControlAbout ->{
                startActivity(AboutActivity::class.java)
            }
            R.id.menuControlExit ->{
                postEvent(CommandHelper.EXIT)
            }
        }
        return super.onOptionsItemSelected(item)
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