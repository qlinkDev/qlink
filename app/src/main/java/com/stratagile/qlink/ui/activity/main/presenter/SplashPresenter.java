package com.stratagile.qlink.ui.activity.main.presenter;
import android.Manifest;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stratagile.qlink.R;
import com.stratagile.qlink.constant.ConstantValue;
import com.stratagile.qlink.db.SwapRecord;
import com.stratagile.qlink.entity.otc.TradePair;
import com.stratagile.qlink.qlinkcom;
import com.stratagile.qlink.utils.FileUtil;
import com.stratagile.qlink.utils.LocalAssetsUtils;
import com.stratagile.qlink.utils.SpUtil;
import com.stratagile.qlink.utils.VersionUtil;
import com.socks.library.KLog;
import com.stratagile.qlink.application.AppConfig;
import com.stratagile.qlink.data.api.HttpAPIWrapper;
import com.stratagile.qlink.ui.activity.main.contract.SplashContract;
import com.stratagile.qlink.ui.activity.main.SplashActivity;
import com.stratagile.qlink.utils.ToastUtil;
import com.stratagile.qlink.utils.eth.ETHWalletUtils;
import com.vondear.rxtools.RxDeviceTool;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.RationaleListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * @author hzp
 * @Package com.stratagile.qlink.ui.activity.main
 * @Description: presenter of SplashActivity
 * @date 2018/01/09 11:24:32
 */
public class SplashPresenter implements SplashContract.SplashContractPresenter{

    HttpAPIWrapper httpAPIWrapper;
    private final SplashContract.View mView;
    private CompositeDisposable mCompositeDisposable;
    private SplashActivity mActivity;

    private static final int JUMPTOMAIN = 0;
    private static final int JUMPTOLOGIN = 1;
    private static final int HASPUDATE = 3;
    private boolean getLastVersionBack = false;
    private static int permissionState = -1;    //-1表示原始状态,0表示允许,1表示拒绝.
    private boolean hasUpdate = false;
    private boolean timeOver = false;
    private int jump = JUMPTOLOGIN;
    private boolean jumpToGuest = false;

    @Inject
    public SplashPresenter(@NonNull HttpAPIWrapper httpAPIWrapper, @NonNull SplashContract.View view, SplashActivity activity) {
        mView = view;
        this.httpAPIWrapper = httpAPIWrapper;
        mCompositeDisposable = new CompositeDisposable();
        this.mActivity = activity;
    }
    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        if (!mCompositeDisposable.isDisposed()) {
             mCompositeDisposable.dispose();
        }
    }

    @Override
    public void observeJump() {
        timeOver = true;
        KLog.i("时间到，开始跳转");
        if (permissionState != 0) {
            return;
        }
        if (jumpToGuest) {
            mView.jumpToGuest();
            return;
        }
        if (jump == JUMPTOMAIN) {
            mView.loginSuccees();
        } else if (jump == JUMPTOLOGIN) {
            mView.jumpToLogin();
        }
//        Observable.interval(0, 1, TimeUnit.SECONDS).take(3)
//                .map(new Function<Long, Long>() {
//                    @Override
//                    public Long apply(@NonNull Long aLong) throws Exception {
//                        return 1 - aLong;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())//发射用的是observeOn
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(@NonNull Disposable disposable) throws Exception {
//                        KLog.i("1");
//                    }
//                })
//                .subscribeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        KLog.i("2");
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Long remainTime) {
//                        KLog.i("剩余时间" + remainTime);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        KLog.i("4");
//                    }
//
//                    @Override
//                    public void onComplete() {
////                        jump = JUMPTOGUEST;
//                        timeOver = true;
//                        KLog.i("时间到，开始跳转");
//                        if (permissionState != 0) {
//                            return;
//                        }
//                        if (jumpToGuest) {
//                            mView.jumpToGuest();
//                            return;
//                        }
//                        if (jump == JUMPTOMAIN) {
//                            mView.loginSuccees();
//                        } else if (jump == JUMPTOLOGIN) {
//                            mView.jumpToLogin();
//                        }
//                    }
//                });
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = (requestCode, rationale) -> AlertDialog.newBuilder(mActivity)
            .setTitle(AppConfig.getInstance().getResources().getString(R.string.Permission_Requeset))
            .setMessage(AppConfig.getInstance().getResources().getString(R.string.We_Need_Some_Permission_to_continue))
            .setPositiveButton(AppConfig.getInstance().getResources().getString(R.string.Agree), (dialog, which) -> {
                rationale.resume();
            })
            .setNegativeButton(AppConfig.getInstance().getResources().getString(R.string.Reject), (dialog, which) -> {
                rationale.cancel();
            }).show();

    @Override
    public void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            AndPermission.with(mActivity)
                    .requestCode(101)
                    .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //Manifest.permission.READ_PHONE_STATE
                    .rationale(rationaleListener)
                    .callback(permission)
                    .start();
        } else {
            AndPermission.with(mActivity)
                    .requestCode(101)
                    .permission(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE)
                    //Manifest.permission.READ_PHONE_STATE
                    .rationale(rationaleListener)
                    .callback(permission)
                    .start();
        }
    }

    @Override
    public void doAutoLogin() {

    }

    @Override
    public void getLastVersion() {
        hasUpdate = false;
        //如果本地记录的应用版本和获取的应用版本不一致,就需要跳转到欢迎页面
        if (SpUtil.getInt(AppConfig.getInstance(), ConstantValue.LOCALVERSIONCODE, 0) < 62) {
            String password = SpUtil.getString(AppConfig.getInstance(), ConstantValue.walletPassWord, "");
            if (!"".equals(password)) {
                String encode = ETHWalletUtils.encryption(password);
                SpUtil.putString(AppConfig.getInstance(), ConstantValue.walletPassWord, encode);
            }
        }
        if (SpUtil.getInt(AppConfig.getInstance(), ConstantValue.LOCALVERSIONCODE, 0) != VersionUtil.getAppVersionCode(AppConfig.getInstance())) {
            KLog.i("需要跳转到guest.........................");
            KLog.i(SpUtil.getInt(AppConfig.getInstance(), ConstantValue.LOCALVERSIONCODE, 0));
            KLog.i(VersionUtil.getAppVersionCode(AppConfig.getInstance()));
            jumpToGuest = true;
        }
    }

    private PermissionListener permission = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            KLog.i("权限申请成功回调。");
            if (requestCode == 101) {
                KLog.i("初始化sd卡");
                qlinkcom.init();
                permissionState = 0;
                try {
                    String topUpP2pId = SpUtil.getString(AppConfig.getInstance(), ConstantValue.topUpP2pId, "");
                    if ("".equals(topUpP2pId)) {
                        String saveP2pId = FileUtil.readData("/Qwallet/p2pId.txt");
                        if ("".equals(saveP2pId)) {
                            UUID uuid = UUID.randomUUID();
                            String p2pId = "";
                            p2pId += uuid.toString().replace("-", "");
                            topUpP2pId = p2pId;
                            SpUtil.putString(AppConfig.getInstance(), ConstantValue.topUpP2pId, p2pId);

                            File file = new File(Environment.getExternalStorageDirectory().toString() + "/Qwallet/p2pId.txt");
                            if (file.exists()) {
                                FileUtil.savaData("/Qwallet/p2pId.txt", topUpP2pId);
                            } else {
                                file.createNewFile();
                                FileUtil.savaData("/Qwallet/p2pId.txt", topUpP2pId);
                            }

                        } else {
                            topUpP2pId = saveP2pId;
                            SpUtil.putString(AppConfig.getInstance(), ConstantValue.topUpP2pId, topUpP2pId);
                        }
                    }
                    List<SwapRecord> records = AppConfig.getInstance().getDaoSession().getSwapRecordDao().loadAll();
                    if (records == null || (records.size() == 0)) {
                        String local = FileUtil.getStrDataFromFile(new File(Environment.getExternalStorageDirectory() + "/Qwallet/swapRecord.json"));
                        if (!"".equals(local)) {
                            ArrayList<SwapRecord> localSwapRecord = new Gson().fromJson(local, new TypeToken<ArrayList<SwapRecord>>() {}.getType());
                            AppConfig.getInstance().getDaoSession().getSwapRecordDao().insertInTx(localSwapRecord);
                        }
                    }
                } catch (Exception e) {
                    if (timeOver) {
                        if (jumpToGuest) {
                            mView.jumpToGuest();
                            return;
                        }
                        if (jump == JUMPTOMAIN) {
                            mView.loginSuccees();
                        } else if (jump == JUMPTOLOGIN) {
                            mView.jumpToLogin();
                        }
                    }
                }
                if (timeOver) {
                    if (jumpToGuest) {
                        mView.jumpToGuest();
                        return;
                    }
                    if (jump == JUMPTOMAIN) {
                        mView.loginSuccees();
                    } else if (jump == JUMPTOLOGIN) {
                        mView.jumpToLogin();
                    }
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == 101) {
                KLog.i("权限申请失败");
                permissionState = 0;
                ToastUtil.show(mActivity, AppConfig.getInstance().getResources().getString(R.string.permission_denied));
                if (timeOver) {
                    if (jumpToGuest) {
                        mView.jumpToGuest();
                        return;
                    }
                    if (jump == JUMPTOMAIN) {
                        mView.loginSuccees();
                    } else if (jump == JUMPTOLOGIN) {
                        mView.jumpToLogin();
                    }
                }
            }
        }
    };
}