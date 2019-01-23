package com.huyingbao.module.kotlin.ui.login.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.huyingbao.core.arch.scope.ActivityScope
import com.huyingbao.core.common.view.CommonRxFragment
import com.huyingbao.module.kotlin.R
import com.huyingbao.module.kotlin.R2
import com.huyingbao.module.kotlin.ui.login.action.LoginActionCreator
import com.huyingbao.module.kotlin.ui.login.store.LoginStore
import javax.inject.Inject

/**
 * Created by liujunfeng on 2019/1/1.
 */
@ActivityScope
class LoginFragment @Inject
constructor() : CommonRxFragment<LoginStore>() {
    @Inject
    internal var mActionCreator: LoginActionCreator? = null

    @BindView(R2.id.et_username)
    internal var mEtUsername: EditText? = null
    @BindView(R2.id.et_password)
    internal var mEtPassword: EditText? = null
    @BindView(R2.id.btn_login)
    internal var mBtnLogin: Button? = null

    override fun getLayoutId(): Int {
        return R.layout.kotlin_fragment_login
    }

    override fun afterCreate(savedInstanceState: Bundle) {
        setTitle(R.string.kotlin_label_login, true)
    }

    @OnClick(R2.id.btn_login)
    fun onViewClicked() {
        val username = mEtUsername!!.text.toString()
        val password = mEtPassword!!.text.toString()
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "请输入密码！", Toast.LENGTH_SHORT).show()
            return
        }
        mActionCreator!!.login(username, password)
    }
}
