package creepersan.lightmanagesystemandroid.Activity

import android.os.Bundle
import android.widget.*
import butterknife.BindView
import com.bumptech.glide.Glide
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import creepersan.lightmanagesystemandroid.Activity.R
import android.view.WindowManager



class LoginActivity :BaseActivity(){
    @BindView(R.id.loginBackground)lateinit var backgroundImageView: ImageView
    @BindView(R.id.loginDialogBackground)lateinit var dialogBackgroundImageView: ImageView
    @BindView(R.id.loginUserName)lateinit var userNameEditText:EditText
    @BindView(R.id.loginPassword)lateinit var paddwordEditText:EditText
    @BindView(R.id.loginRememberMe)lateinit var rememberCheckBox:CheckBox
    @BindView(R.id.loginForgetPassword)lateinit var forgetPaddwordTextView:TextView
    @BindView(R.id.loginLogin)lateinit var loginButton:Button

    override fun getLayoutID(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar!=null){
            supportActionBar!!.hide()
        }
        window.attributes.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or window.attributes.flags
        Glide.with(this).load(R.drawable.login_background).into(backgroundImageView)
        Glide.with(this).load(R.drawable.login_dialog_background).into(dialogBackgroundImageView)
    }

}
