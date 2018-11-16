package com.stratagile.qlink.ui.activity.eth;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.socks.library.KLog;
import com.stratagile.qlink.R;
import com.stratagile.qlink.application.AppConfig;
import com.stratagile.qlink.base.BaseActivity;
import com.stratagile.qlink.db.EthWallet;
import com.stratagile.qlink.db.Wallet;
import com.stratagile.qlink.entity.AllWallet;
import com.stratagile.qlink.ui.activity.eth.component.DaggerEthWalletDetailComponent;
import com.stratagile.qlink.ui.activity.eth.contract.EthWalletDetailContract;
import com.stratagile.qlink.ui.activity.eth.module.EthWalletDetailModule;
import com.stratagile.qlink.ui.activity.eth.presenter.EthWalletDetailPresenter;
import com.stratagile.qlink.ui.activity.wallet.ExportEthKeyStoreActivity;
import com.stratagile.qlink.ui.activity.wallet.VerifyWalletPasswordActivity;
import com.stratagile.qlink.utils.ToastUtil;
import com.stratagile.qlink.utils.eth.ETHWalletUtils;
import com.stratagile.qlink.view.PopWindowUtil;
import com.stratagile.qlink.view.SweetAlertDialog;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hzp
 * @Package com.stratagile.qlink.ui.activity.eth
 * @Description: $description
 * @date 2018/10/25 15:02:11
 */

public class EthWalletDetailActivity extends BaseActivity implements EthWalletDetailContract.View {

    @Inject
    EthWalletDetailPresenter mPresenter;
    @BindView(R.id.ivWalletAvatar)
    ImageView ivWalletAvatar;
    @BindView(R.id.tvWalletName)
    TextView tvWalletName;
    @BindView(R.id.tvWalletAddress)
    TextView tvWalletAddress;
    @BindView(R.id.tvWalletAsset)
    TextView tvWalletAsset;
    @BindView(R.id.llAbucoins)
    LinearLayout llAbucoins;
    @BindView(R.id.llExportKeystore)
    LinearLayout llExportKeystore;
    @BindView(R.id.llExportPrivateKey)
    LinearLayout llExportPrivateKey;
    @BindView(R.id.llExportNeoEncryptedKey)
    LinearLayout llExportNeoEncryptedKey;
    @BindView(R.id.llExportNeoPrivateKey)
    LinearLayout llExportNeoPrivateKey;
    @BindView(R.id.tvDeleteWallet)
    TextView tvDeleteWallet;

    private EthWallet ethWallet;

    private Wallet wallet;

    private AllWallet.WalletType walletType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mainColor = R.color.white;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_eth_wallet_detail);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getIntent().getIntExtra("walletType", 0) == AllWallet.WalletType.EthWallet.ordinal()) {
            ethWallet = getIntent().getParcelableExtra("ethwallet");
            walletType = AllWallet.WalletType.EthWallet;

            ivWalletAvatar.setImageDrawable(getResources().getDrawable(R.mipmap.icons_eth_wallet));
            tvWalletName.setText(ethWallet.getName());
            tvWalletAddress.setText(ethWallet.getAddress());

            llExportNeoEncryptedKey.setVisibility(View.GONE);
            llExportNeoPrivateKey.setVisibility(View.GONE);
        } else if (getIntent().getIntExtra("walletType", 0) == AllWallet.WalletType.NeoWallet.ordinal()) {
            walletType = AllWallet.WalletType.NeoWallet;
            wallet = getIntent().getParcelableExtra("neowallet");
            ivWalletAvatar.setImageDrawable(getResources().getDrawable(R.mipmap.icons_neo_wallet));
            tvWalletName.setText(wallet.getAddress());
            tvWalletAddress.setText(wallet.getAddress());
            llAbucoins.setVisibility(View.GONE);
            llExportKeystore.setVisibility(View.GONE);
            llExportPrivateKey.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initData() {
        setTitle("Manage");
    }

    @Override
    protected void setupActivityComponent() {
        DaggerEthWalletDetailComponent
                .builder()
                .appComponent(((AppConfig) getApplication()).getApplicationComponent())
                .ethWalletDetailModule(new EthWalletDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    public void setPresenter(EthWalletDetailContract.EthWalletDetailContractPresenter presenter) {
        mPresenter = (EthWalletDetailPresenter) presenter;
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    @Override
    public void closeProgressDialog() {
        progressDialog.hide();
    }

    @OnClick({R.id.llAbucoins, R.id.llExportKeystore, R.id.llExportPrivateKey, R.id.llExportNeoEncryptedKey, R.id.llExportNeoPrivateKey, R.id.tvDeleteWallet})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llAbucoins:
                if (ethWallet.getMnemonic() == null) {
                    cannotShowMnemonic();
                } else {
                    exportMnemonic();
                }
                break;
            case R.id.llExportKeystore:
                startActivity(new Intent(this, ExportEthKeyStoreActivity.class).putExtra("wallet", ethWallet));
                break;
            case R.id.llExportPrivateKey:
                ExportPrivateKey();
                break;
            case R.id.llExportNeoEncryptedKey:
                ExportNeoEncryptedKey();
                break;
            case R.id.llExportNeoPrivateKey:
                ExportNeoPrivateKey();
                break;
            case R.id.tvDeleteWallet:
                deleteWallet();
                break;
            default:
                break;
        }
    }

    private void cannotShowMnemonic() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog, null, false);
        TextView tvContent = view.findViewById(R.id.tvContent);
        tvContent.setText("Wallet can not be seen through the private key and keystore import wallet.");
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        ImageView ivTitle = view.findViewById(R.id.ivTitle);
        ivTitle.setImageDrawable(getResources().getDrawable(R.mipmap.careful_1));
        TextView tvOk = view.findViewById(R.id.tvOpreate);
        ivClose.setVisibility(View.INVISIBLE);
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sweetAlertDialog.cancel();
            }
        });
        tvOk.setText(getResources().getString(R.string.ok));
        sweetAlertDialog.setView(view);
        sweetAlertDialog.show();
    }

    private void exportMnemonic() {
        startActivity(new Intent(this, EthMnemonicShowActivity.class).putExtra("wallet", ethWallet));
    }

    private void deleteWallet() {
        startActivityForResult(new Intent(this, VerifyWalletPasswordActivity.class).putExtra("flag", ""), 0);
        overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (walletType == AllWallet.WalletType.NeoWallet) {
                AppConfig.getInstance().getDaoSession().getWalletDao().delete(wallet);
            } else if (walletType == AllWallet.WalletType.EthWallet) {
                AppConfig.getInstance().getDaoSession().getEthWalletDao().delete(ethWallet);
            }
            showTestDialog();
        }
    }

    private void showTestDialog() {
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_tip, null, false);
        TextView tvContent = view.findViewById(R.id.tvContent);
        ImageView imageView = view.findViewById(R.id.ivTitle);
        imageView.setImageDrawable(getResources().getDrawable(R.mipmap.op_success));
        tvContent.setText("delete wallet success");
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this);
        sweetAlertDialog.setView(view);
        sweetAlertDialog.show();
        tvDeleteWallet.postDelayed(new Runnable() {
            @Override
            public void run() {
                sweetAlertDialog.cancel();
                tvDeleteWallet.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setResult(RESULT_OK);
                        onBackPressed();
                    }
                }, 200);
            }
        }, 2000);
    }

    private void ExportPrivateKey() {
        String privateKey = ETHWalletUtils.derivePrivateKey(ethWallet.getId());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_export_privatekey_layout, null);
        builder.setView(view);
        builder.setCancelable(true);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);//输入内容
        ImageView ivClose = view.findViewById(R.id.ivClose);
        TextView tvCopy = view.findViewById(R.id.tvCopy);//取消按钮
        tvContent.setText(privateKey);
        //取消或确定按钮监听事件处l
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setPrimaryClip(ClipData.newPlainText("", tvContent.getText().toString()));
                ToastUtil.displayShortToast(getResources().getString(R.string.copy_success));
                dialog.cancel();
            }
        });
    }

    private void ExportNeoPrivateKey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_export_privatekey_layout, null);
        builder.setView(view);
        builder.setCancelable(true);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);//输入内容
        TextView tv_warn = (TextView) view.findViewById(R.id.tv_warn);//输入内容
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);//输入内容
        tv_warn.setVisibility(View.GONE);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        TextView tvCopy = view.findViewById(R.id.tvCopy);//取消按钮
        tvContent.setText(wallet.getPrivateKey());
        //取消或确定按钮监听事件处l
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setPrimaryClip(ClipData.newPlainText("", tvContent.getText().toString()));
                ToastUtil.displayShortToast(getResources().getString(R.string.copy_success));
                dialog.cancel();
            }
        });
    }

    private void ExportNeoEncryptedKey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.dialog_export_privatekey_layout, null);
        builder.setView(view);
        builder.setCancelable(true);
        TextView tvContent = (TextView) view.findViewById(R.id.tv_content);//输入内容
        TextView tv_warn = (TextView) view.findViewById(R.id.tv_warn);//输入内容
        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);//输入内容
        tvTitle.setText("Export Encrypted Key");
        tv_warn.setVisibility(View.GONE);
        ImageView ivClose = view.findViewById(R.id.ivClose);
        TextView tvCopy = view.findViewById(R.id.tvCopy);//取消按钮
        tvContent.setText(wallet.getWif());
        //取消或确定按钮监听事件处l
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.show();
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setPrimaryClip(ClipData.newPlainText("", tvContent.getText().toString()));
                ToastUtil.displayShortToast(getResources().getString(R.string.copy_success));
                dialog.cancel();
            }
        });
    }
}