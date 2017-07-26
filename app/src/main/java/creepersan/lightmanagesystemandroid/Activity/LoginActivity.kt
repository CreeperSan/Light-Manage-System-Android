package creepersan.lightmanagesystemandroid.Activity

import android.os.Bundle
import android.view.View
import android.widget.*
import butterknife.BindView
import com.bumptech.glide.Glide
import creepersan.lightmanagesystemandroid.Base.BaseActivity
import android.view.WindowManager
import android.text.InputType
import android.text.Selection
import android.text.Spannable
import creepersan.lightmanagesystemandroid.Helper.SharePrefHelper


class LoginActivity :BaseActivity(){
    @BindView(R.id.loginBackground)lateinit var backgroundImageView: ImageView
    @BindView(R.id.loginDialogBackground)lateinit var dialogBackgroundImageView: ImageView
    @BindView(R.id.loginUserName)lateinit var userNameEditText:EditText
    @BindView(R.id.loginPassword)lateinit var passwordEditText:EditText
    @BindView(R.id.loginRememberMe)lateinit var rememberCheckBox:CheckBox
    @BindView(R.id.loginForgetPassword)lateinit var forgetPaddwordTextView:TextView
    @BindView(R.id.loginLogin)lateinit var loginButton:Button
    @BindView(R.id.loginPasswordVisible)lateinit var passwordVisibleButton:ImageView

    private var isPasswordVisible = true

    override fun getLayoutID(): Int = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedHelper = SharePrefHelper.getInstance(context)
        if (supportActionBar!=null){
            supportActionBar!!.hide()
        }
        window.attributes.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or window.attributes.flags
        Glide.with(this).load(R.drawable.login_background).into(backgroundImageView)
        Glide.with(this).load(R.drawable.login_dialog_background).into(dialogBackgroundImageView)
        //登陆按钮
        loginButton.setOnClickListener { v: View? ->
            if (userNameEditText.text.toString().trim() == "admin" && passwordEditText.text.toString().trim() == "123456"){
                if(rememberCheckBox.isChecked){
                    sharedHelper.setAccountUserName(userNameEditText.text.toString().trim())
                    sharedHelper.setAccountPassword(passwordEditText.text.toString().trim())
                    sharedHelper.setAccountRememberState(true)
                }
                startActivity(ControlActivity::class.java, true)
            }else{
                toast("用户名或密码错误")
            }
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
            Selection.setSelection((passwordEditText.text as Spannable),passwordEditText.text.length)//光标回到行末
        }
        //初始化信息
        if (sharedHelper.getAccountRememberState()){
            userNameEditText.setText(sharedHelper.getAccountUserName())
            passwordEditText.setText(sharedHelper.getAccountPassword())
            rememberCheckBox.isChecked = true
        }
        //忘记密码
        forgetPaddwordTextView.setOnClickListener {
            toast("忘记密码了？找党员啊")
        }
    }

}
