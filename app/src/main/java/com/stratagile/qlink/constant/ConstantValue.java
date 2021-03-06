package com.stratagile.qlink.constant;

import android.net.wifi.WifiInfo;

import com.stratagile.qlink.db.RecordSave;
import com.stratagile.qlink.db.TransactionRecord;
import com.stratagile.qlink.db.UserAccount;
import com.stratagile.qlink.entity.Balance;
import com.stratagile.qlink.entity.CurrencyBean;
import com.stratagile.qlink.entity.MainAddress;
import com.stratagile.qlink.entity.im.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huzhipeng on 2018/1/9.
 */

public class ConstantValue {
    public static String ENVIRONMENT = "environment";
    public static String LOCALVERSIONCODE = "localversioncode";
    //升级到这个版本是否需要显示guest页面
    public static int gasPrice = 30;
    public static boolean thisVersionShouldShowGuest = true;
    public static String P2PID = "p2pid";
    public static String uuid2 = "uuid2";
    public static String topUpP2pId = "topUpP2pId";
    public static String Language = "language";
    public static boolean isConnectToP2p = false;
    public static WifiInfo connectedWifiInfo;
    public static float mLatitude;
    public static float mLongitude;
    public static String longcountry = "";
    public static String shortcountry;
    public static String unlockTime = "unlocktime";
    public static Balance mBalance;
    public static boolean isLoadingImg = false;

    public static String userAll = "all";
    public static String userLend = "debit";
    public static int jpushOpreateCount = 1;
    //测试 http://47.103.40.20:19735
    //正式 http://wrpc.qlcchain.org:9735

    public static String miningQLC = "";

    public static String wrapperNode = "http://47.244.138.61:19746";

    //qlc正式网节点
    public static String qlcNode = "http://wrpc.qlcchain.org:9735";
    //qlc测试网节点
//    public static String qlcNode = "http://rpc-test.qlcchain.online";

    //创建多重签名地址的hash正式服
    public static String createMultiSigHash = "02c6e68c61480003ed163f72b41cbb50ded29d79e513fd299d2cb844318b1b8ad5";
    //创建多重签名地址的hash测试服
//    public static String createMultiSigHash = "0292a55eb2f213d087d71cf0e2e4b047762b6eccc6a6993d7bbea39e7379661afb";

    //qlc链抵押正式节点
    public static String qlcStakeNode = "https://nep5.qlcchain.online";
    //qlc抵押测试节点
//    public static String qlcStakeNode = "http://47.103.54.171:19740";

    public static String qlcWorkNode = "http://pow1.qlcchain.org/work";

    public static String neoNode = "http://seed2.ngd.network:20332";

    public static String qlcHubEndPoint = "https://hub-test.qlcchain.online";

    public static String qlchash = "0d821bd7b6d53f5c2b40e217c6defc8bbe896cf5";

    public static int currentChainId = 4;

    //neo链正式网查交易记录
    public static String neoTranscationNode = "https://api.neoscan.io/api/main_net/v1/get_transaction/";
    //neo测试网查交易记录
//    public static String neoTranscationNode = "https://api.neoscan.io/api/test_net/v1/get_transaction/";

    //https://mainnet.infura.io/v3/dc2243ed5aa5488d9fcf794149f56fc2
    public static String ethNodeUrl = "https://mainnet.infura.io/v3/dc2243ed5aa5488d9fcf794149f56fc2";
//    public static String qlcNode = "http://47.103.40.20:19735";

    public static String guoneiEpidemic = "https://news.qq.com/zt2020/page/feiyan.htm#/?nojump=1";
    public static String haiwaiEpidemic = "https://google.com/covid19-map/?hl=en";

    public static UserAccount currentUser;
    /**
     * 拥有的免费连接次数
     */
    public static int freeNum = 0;
    //统一收钱的钱包地址
//    public static String mainAddress = "AQC7Bod2LxaRxmLewRrwCA1Nt6AQMWSm28";
    public static String mainAddress = "";
    public static String ethMainAddress = "";

    public static boolean canClickWallet = true;

    public static UserAccount lastLoginOut;

    public static String EXTRA_BUNDLE = "bundle";

    public static String isShowMiningAct = "isShowMiningAct";
    public static String currentMiningActId = "currentMiningActId";
    public static String currentBurnQgasActId = "currentBurnQgasActId";

    public static float qgasToQlcPrice = 4.5F;

    /**
     * eth的bnb契约地址
     */
    public static String ethContractAddress = "0xb8c77482e45f1f44de1745f52c74426c631bdd52";

    public static float bnbDecimal = 1000000000000000000f;

    /**
     * 是否开启指纹解锁
     */
    public static String fingerprintUnLock = "fingerprintUnLock";

    public static String currentEthWallet = "currentethwallet";

    public static String nickName;
    /**
     * 记录p2p网络是否上线过
     */
    public static boolean isLogin = false;

    public static String myAvaterUpdateTime = "avaterUpdateTime";

    public static String currentWallet = "currentWallet";

    /**
     * 是否展示处在测试网的标记
     */
    public static String showTestFlag = "showTestFlag";
    public static String currentMainWallet = "currentMainWallet";
    /**
     * 是否在主网
     */
    public static String isMainNet = "isMainNet";
    public static boolean isCloseRegisterAssetsInMain = true; //是否暂时关闭主网的注册资产
    public static String lastRemoveImageAvaterTime = "lastRemoveImageAvaterTime";

    public static String selectLanguage = "selectLanguage";//选择的语言
    public static String myAvatarPath = "myAvatarPath";

    public static int myStatus = 0;

    public static String logStr = "";
    public static boolean showLog = false;

    public static boolean isDeleteFreinds = false;//是否删除过好友

    public static String windowsVpnName = ""; //电脑资产名称

    public static boolean isWindows = false;//当前是否注册电脑资产

    public static String getWindowsVpnPath = "";//电脑资产路径

    /**
     * im消息的存储
     */
    public static Map<Integer, ArrayList<Message>> messageMap = new HashMap<>();

    /**
     * 上一次删除好友的时间
     */
    public static String lastDeleteFreindTime = "lastDeleteFreindTime";

    /**
     * 未上报给提供端的记录
     */
    public static ArrayList<TransactionRecord> unReportRecord = new ArrayList<>();

    /**
     * 记录WiFi连接后的一些数据，随着app的关闭而销毁
     */
    public static RecordSave connectRecordSave;

    public static String walletPassWord = "walletpassword";//数字密码
    public static String fingerPassWord = "fingerPassWord";//指纹密码
    public static boolean isShouldShowVertifyPassword = true;

    public static boolean isConnectVpn = false;
    public static boolean isConnectWifi = false;

    /**
     * 上否连接上了vpn的标志，在2018.08.06加上，为了避免在vpn连接上，然后马上又断开，导致
     * 上报为连接不成功的错误上报。
     */
    public static boolean isConnectedVpn = false;

    public static MainAddress.DataBean mainAddressData;

    public static String blackHoldAddress = "qlc_1111111111111111111111111111111111111111111111111111hifc8npp";

    public static String lastRestart = "lastRestart";//上一次自动重启时间

    public static String ETH_JAXX_TYPE = "m/44'/60'/0'/0/0";
    public static String ETH_LEDGER_TYPE = "m/44'/60'/0'/0";
    public static String ETH_CUSTOM_TYPE = "m/44'/60'/1'/0/0";

    public static String currencyUnit = "currencyUnit";

    public static CurrencyBean currencyBean;

    ///**********************************************************///

    /**
     * 发送文件的请求，这个可以不要回应
     */
    public static final String sendFileRequest = "sendFileRequest";


    /**
     * 发送vpn配置文件的请求
     */
    public static final String sendVpnFileRequest = "sendVpnFileRequest";

    /**
     * vpn私钥的请求而
     */
    public static final String vpnPrivateKeyReq = "vpnPrivateKeyReq";

    /**
     * vpn私钥的返回
     */
    public static final String vpnPrivateKeyRsp = "vpnPrivateKeyRsp";

    /**
     * vpn账号和密码的请求
     */
    public static final String vpnUserAndPasswordReq = "vpnUserAndPasswordReq";

    /**
     * vpn账号和密码的返回
     */
    public static final String vpnUserAndPasswordRsp = "vpnUserAndPasswordRsp";

    /**
     * vpn或者wifi连接成功后，告诉提供端做连接记录的请求
     */
    public static final String recordSaveReq = "recordSaveReq";
    /**
     * vpn或者wifi连接成功后，告诉提供端做连接记录的返回
     */
    public static final String recordSaveRsp = "recordSaveRsp";

    /**
     * 申请加入群聊的请求
     */
    public static final String joinGroupChatReq = "joinGroupChatReq";

    /**
     * 申请加入群聊的回复
     */
    public static final String joinGroupChatRsp = "joinGroupChatRsp";

    /**
     * 检查是否连接正常的请求，即发一个空消息过去，看对方是否会回消息
     */
    public static final String checkConnectReq = "checkConnectReq";


    /**
     * 检查是否连接正常的回复
     */
    public static final String checkConnectRsp = "checkConnectRsp";

    /**
     * 默认的返回，就是因为版本更新，当前版本没有定义这个类型，就把这个type作为内容原路返回回去
     */
    public static final String defaultRsp = "defaultRsp";

    /**
     * 获取vpn用户名、密码、私钥的请求
     */
    public static final String vpnUserPassAndPrivateKeyReq = "vpnUserPassAndPrivateKeyReq";

    /**
     * 获取vpn用户名、密码、私钥的返回
     */
    public static final String vpnUserPassAndPrivateKeyRsp = "vpnUserPassAndPrivateKeyRsp";

    /**
     * 拿server 所有文件传输完成
     */
    public static final String sendVpnFileRsp = "sendVpnFileRsp";


    public static final String sendVpnFileListReq = "sendVpnFileListReq";
    public static final String sendVpnFileListRsp = "sendVpnFileListRsp";

    public static final String sendVpnFileNewReq = "sendVpnFileNewReq";
    public static final String sendVpnFileNewRsp = "sendVpnFileNewRsp";

    public static final String vpnRegisterSuccessNotify = "vpnRegisterSuccessNotify";

    public static final String userPhone = "userPhone";
    public static final String userPassword = "userPassword";
    public static final String userEmail = "userEmail";
    public static final String userRsaPubKey = "userRsaPubKey";
    public static final String isUserLogin = "isUserLogin";

    public static final String orderTypeBuy = "BUY";
    public static final String orderTypeSell = "SELL";

    public static final String ignoreVersion = "ignoreVersion";

    public static final String showedEpidemic = "showedEpidemic";

}
