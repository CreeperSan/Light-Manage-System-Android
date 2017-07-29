package creepersan.lightmanagesystemandroid.Activity

import android.os.Bundle
import android.view.View
import android.widget.*
import butterknife.BindView
import com.bumptech.glide.Glide
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import android.view.WindowManager
import android.text.InputType
import creepersan.lightmanagesystemandroid.Event.LoginEvent
import creepersan.lightmanagesystemandroid.Event.LoginResultEvent
import creepersan.lightmanagesystemandroid.Event.StringEvent
import creepersan.lightmanagesystemandroid.Helper.SharePrefHelper
import creepersan.lightmanagesystemandroid.Service.NetworkService
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class LoginActivity :BaseActivity(){
    @BindView(R.id.loginBackground)lateinit var backgroundImageView: ImageView
    @BindView(R.id.loginDialogBackground)lateinit var dialogBackgroundImageView: ImageView
    @BindView(R.id.loginUserName)lateinit var userNameEditText:EditText
    @BindView(R.id.loginPassword)lateinit var passwordEditText:EditText
    @BindView(R.id.loginRememberMe)lateinit var rememberCheckBox:CheckBox
    @BindView(R.id.loginForgetPassword)lateinit var forgetPasswordTextView:TextView
    @BindView(R.id.loginLogin)lateinit var loginButton:Button
    @BindView(R.id.loginPasswordVisible)lateinit var passwordVisibleButton:ImageView
    @BindView(R.id.loginProgressBar)lateinit var progressBar:ProgressBar

    private var isPasswordVisible = true

    override fun getLayoutID(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(NetworkService::class.java)
        val sharedHelper = SharePrefHelper.getInstance(context)
        if (supportActionBar!=null){
            supportActionBar!!.hide()
        }
        window.attributes.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or window.attributes.flags
        Glide.with(this).load(R.drawable.login_background).into(backgroundImageView)
        Glide.with(this).load(R.drawable.login_dialog_background).into(dialogBackgroundImageView)
        //登陆按钮
        loginButton.setOnClickListener { v: View? ->
            postEvent(LoginEvent(userNameEditText.text.toString().trim(),passwordEditText.text.toString().trim()))
            showProgressBar()
        }
        //记住我按钮
        rememberCheckBox.setOnCheckedChangeListener { compoundButton, b ->
            if (!b){
                sharedHelper.setAccountRememberState(false)
                sharedHelper.setAccountUserName("")
                sharedHelper.setAccountPassword("")
            }
        }
        //切换密码可见
        passwordVisibleButton.setOnClickListener {
            if (isPasswordVisible){
                isPasswordVisible = false
                passwordEditText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                passwordVisibleButton.setImageResource(R.drawable.ic_visibility_off_black)
            }else{
                isPasswordVisible = true
                passwordEditText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordVisibleButton.setImageResource(R.drawable.ic_visibility_black)
            }
        }
        //初始化信息
        if (sharedHelper.getAccountRememberState()){
            userNameEditText.setText(sharedHelper.getAccountUserName())
            passwordEditText.setText(sharedHelper.getAccountPassword())
            rememberCheckBox.isChecked = true
        }
        //忘记密码
        forgetPasswordTextView.setOnClickListener {
            toast("忘记密码了？找党员啊")
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun on(event:StringEvent){
        userNameEditText.setText(event.string)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginResultEvent(event:LoginResultEvent){
        if (event.isConnected){
            if (event.isSuccess){
                if(rememberCheckBox.isChecked){
                    val sharedHelper = SharePrefHelper.getInstance(this)
                    sharedHelper.setAccountUserName(userNameEditText.text.toString().trim())
                    sharedHelper.setAccountPassword(passwordEditText.text.toString().trim())
                    sharedHelper.setAccountRememberState(true)
                    toast("登陆成功")
                    startActivity(ControlActivity::class.java,true)
                }
            }else{
                toast("用户名或者密码错误")
                hideProgressBar()
            }
        }else{
            toast("网络连接失败，请检查网络设置以及设备状况")
            hideProgressBar()
        }
    }

    fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
        loginButton.visibility = View.GONE
    }
    fun hideProgressBar(){
        progressBar.visibility = View.GONE
        loginButton.visibility = View.VISIBLE
    }

}
