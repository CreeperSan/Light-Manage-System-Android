package creepersan.lightmanagesystemandroid.Activity

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.MenuItem
import creepersan.lightmanagesystemandroid.Base.BaseActivity

class SettingActivity:BaseActivity(){

    override fun getLayoutID(): Int = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initActionBar()
        initFinal()
    }

    private fun initActionBar() {
        val actionBar = supportActionBar as ActionBar
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
    private fun initFinal() {
        title = "设置"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}