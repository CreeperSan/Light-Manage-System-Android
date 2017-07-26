package creepersan.lightmanagesystemandroid.Helper

import android.content.Context
import android.content.SharedPreferences

class SharePrefHelper private constructor(context: Context) {

    /**
     *      获取账户配置
     */
    fun getAccountUserName():String{
        return accountPref!!.getString(ACCOUNT_KEY_USERNAME,"")
    }
    fun setAccountUserName(userName:String){
        accountPref!!.edit().putString(ACCOUNT_KEY_USERNAME,userName).commit()
    }
    fun getAccountPassword():String{
        return accountPref!!.getString(ACCOUNT_KEY_PASSWORD,"")
    }
    fun setAccountPassword(password:String){
        accountPref!!.edit().putString(ACCOUNT_KEY_PASSWORD,password).commit()
    }
    fun getAccountRememberState():Boolean{
        return accountPref!!.getBoolean(ACCOUNT_KEY_REMEMBER_STATE,false)
    }
    fun setAccountRememberState(state:Boolean){
        accountPref!!.edit().putBoolean(ACCOUNT_KEY_REMEMBER_STATE,state).commit()
    }


    init {
        accountPref = context.applicationContext.getSharedPreferences(ACCOUNT_PREF_NAME,Context.MODE_PRIVATE)
    }


    companion object {
        private val ACCOUNT_PREF_NAME = "Account"
        private val ACCOUNT_KEY_USERNAME = "UserName"
        private val ACCOUNT_KEY_PASSWORD = "Password"
        private val ACCOUNT_KEY_REMEMBER_STATE = "RememberState"

        private var mInstance:SharePrefHelper? = null
        private var accountPref:SharedPreferences? = null

        fun getInstance(context: Context):SharePrefHelper{
            if (mInstance == null){
                mInstance = SharePrefHelper(context)
            }
            return mInstance as SharePrefHelper
        }

    }
}