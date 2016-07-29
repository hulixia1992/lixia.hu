package com.example.drum.hulixia.presenter;

import android.os.Handler;

import com.example.drum.hulixia.model.UserModel;
import com.example.drum.hulixia.model.inter.IUser;
import com.example.drum.hulixia.presenter.inter.ILoginPresenter;
import com.example.drum.hulixia.view.inter.ILoginView;


/**
 * Created by hulixia on 2016/7/1.
 *
 */
public class LoginPresenterImpl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterImpl(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwad) {
        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name, passwad);
        if (code != 0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 3000);
    }

    @Override
    public void setProgressbarVisiblity() {

    }


    private void initUser() {
        user = new UserModel("mvp", "mvp");
    }
}