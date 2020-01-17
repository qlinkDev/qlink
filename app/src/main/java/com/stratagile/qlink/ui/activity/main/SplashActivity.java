package com.stratagile.qlink.ui.activity.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.socks.library.KLog;
import com.stratagile.qlink.R;
import com.stratagile.qlink.application.AppConfig;
import com.stratagile.qlink.base.BaseActivity;
import com.stratagile.qlink.constant.ConstantValue;
import com.stratagile.qlink.ui.activity.main.component.DaggerSplashComponent;
import com.stratagile.qlink.ui.activity.main.contract.SplashContract;
import com.stratagile.qlink.ui.activity.main.module.SplashModule;
import com.stratagile.qlink.ui.activity.main.presenter.SplashPresenter;
import com.stratagile.qlink.ui.activity.wallet.VerifyWalletPasswordActivity;
import com.stratagile.qlink.utils.FireBaseUtils;
import com.stratagile.qlink.utils.LocalAssetsUtils;
import com.stratagile.qlink.utils.LocalWalletUtil;
import com.stratagile.qlink.utils.SpUtil;
import com.stratagile.qlink.utils.VersionUtil;
import com.stratagile.qlink.utils.eth.ETHWalletUtils;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author hzp
 * @Package com.stratagile.qlink.ui.activity.main
 * @Description: $description
 * @date 2018/01/09 11:24:32
 */

public class SplashActivity extends BaseActivity implements SplashContract.View {

    @Inject
    SplashPresenter mPresenter;
    @BindView(R.id.activity_splash_ImageViewLogo)
    ImageView activitySplashImageViewLogo;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        needFront = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "startApp");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "startApp");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "startApp");
//        mFirebaseAnalytics.logEvent("startApp", bundle);
        FireBaseUtils.logEvent(this, FireBaseUtils.eventStartApp);
        SpUtil.putLong(AppConfig.getInstance(),ConstantValue.lastRestart, Calendar.getInstance().getTimeInMillis());
        mPresenter.getLastVersion();
        mPresenter.getPermission();
        mPresenter.observeJump();
        KLog.i(VersionUtil.getAppVersionCode(this));
    }

    @Override
    protected void setupActivityComponent() {
        DaggerSplashComponent
                .builder()
                .appComponent(((AppConfig) getApplication()).getApplicationComponent())
                .splashModule(new SplashModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(SplashContract.SplashContractPresenter presenter) {
        mPresenter = (SplashPresenter) presenter;
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        progressDialog.hide();
    }

    @Override
    public void loginSuccees() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("flag", "splash");
        if(getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE) != null){
            intent.putExtra(ConstantValue.EXTRA_BUNDLE, getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE));
        }
        startActivity(intent);
        overridePendingTransition(R.anim.main_activity_in, R.anim.splash_activity_out);
        finish();
    }

    @Override
    public void jumpToLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("flag", "splash");
        if(getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE) != null){
            intent.putExtra(ConstantValue.EXTRA_BUNDLE,
                    getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE));
        }
        startActivity(intent);
        overridePendingTransition(R.anim.main_activity_in, R.anim.splash_activity_out);
        finish();
    }

    @Override
    public void jumpToGuest() {
        if (ConstantValue.thisVersionShouldShowGuest) {
            Intent intent = new Intent(this, GuestActivity.class);
            intent.putExtra("flag", "splash");
            if(getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE) != null){
                intent.putExtra(ConstantValue.EXTRA_BUNDLE, getIntent().getBundleExtra(ConstantValue.EXTRA_BUNDLE));
            }
            startActivity(intent);
        } else {
            startActivity(VerifyWalletPasswordActivity.class);
        }
        finish();
    }

}