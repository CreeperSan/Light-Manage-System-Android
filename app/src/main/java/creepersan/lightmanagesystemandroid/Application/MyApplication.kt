package creepersan.lightmanagesystemandroid.Application

import android.app.Application
import creepersan.lightmanagesystemandroid.Helper.DatabaseHelper

class MyApplication:Application(){

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.Instance.getInstance(this)
    }

}