package com.openxu.core.utils;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.util.Log;


public class NetListenerUtils {

    private static NetListenerUtils netListenerUtils = new NetListenerUtils();

    public static NetListenerUtils getInstance() {
        return netListenerUtils;
    }


    /**
     * 网络环境检测
     */
    protected MyPhoneStateListener phoneStateListener;
    protected TelephonyManager mTelephonyManager;
    protected String netStatusStr;
    protected String netStatusResult;

    public NetListenerUtils() {
        //获取telephonyManager
        mTelephonyManager = (TelephonyManager) FUtils.getApp().getSystemService(Context.TELEPHONY_SERVICE);
        phoneStateListener = new MyPhoneStateListener();//监听
        Log.e("TAG", "网络监听开始");
    }


    private class MyPhoneStateListener extends PhoneStateListener {
        //获取信号强度
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            count++;
            //获取网络信号强度
            //获取0-4的5种信号级别，越大信号越好,但是api23开始才能用
            // int level = signalStrength.getLevel();
            try {
                //获取网络类型
                FNetworkUtils.NetworkType netWorkType = FNetworkUtils.getNetworkType();
                FLog.w("当前网络类型：" + netWorkType);
                if (netWorkType == FNetworkUtils.NetworkType.NETWORK_WIFI) {
                    int wifistatus = FNetworkUtils.obtainWifiLevel(5);
                    netStatusStr = wifistatus >= 3 ? "网络还凑合" : "当前网络环境较差";
                    netStatusResult = "wifi网络," + FNetworkUtils.obtainWifiInfo() + netStatusStr;
                } else if (netWorkType == FNetworkUtils.NetworkType.NETWORK_4G) {
                    String[] params = signalStrength.toString().split(" ");
                    //4G网络 最佳范围   >-90dBm 越大越好
                    int Itedbm = Integer.parseInt(params[9]);
                    if (Itedbm > -110) {
                    } else {
                        netStatusStr = "当前网络环境较差";
                    }
                    FLog.w("4G 移动信号：" + signalStrength.toString());
                    netStatusResult = FNetworkUtils.getNetworkOperatorName() + "4G网络（>-90dBm 越大越好）， 信号强度" + Itedbm + netStatusStr;
                } else if (netWorkType == FNetworkUtils.NetworkType.NETWORK_3G) {
                    netStatusStr = "当前网络环境较差";
                    //3G网络最佳范围  >-90dBm  越大越好  ps:中国移动3G获取不到  返回的无效dbm值是正数（85dbm）
                    //在这个范围的已经确定是3G，但不同运营商的3G有不同的获取方法，故在此需做判断 判断运营商与网络类型的工具类在最下方
                    netStatusResult = FNetworkUtils.getNetworkOperatorName();
                    if ("中国移动".equals(netStatusResult)) {
                        netStatusResult += "3G网络 0";  //中国移动3G不可获取，故在此返回0
                    } else if ("中国联通".equals(netStatusResult)) {
                        int cdmaDbm = signalStrength.getCdmaDbm();
                        netStatusResult += "3G网络 " + cdmaDbm;
                    } else if ("中国电信".equals(netStatusResult)) {
                        int evdoDbm = signalStrength.getEvdoDbm();
                        netStatusResult += "3G网络 " + evdoDbm;
                    }
                    netStatusResult += " " + netStatusStr;
                } else if (netWorkType == FNetworkUtils.NetworkType.NETWORK_2G) {
                    // 3G/2G网络信号强度是通过API接口函数完成的。 asu 与 dbm 之间的换算关系是 dbm=-113 + 2*asu
                    netStatusStr = "当前网络环境较差";
                    //2G网络
                    int asu = signalStrength.getGsmSignalStrength();
                    int dbm = -113 + 2 * asu;
                    netStatusResult = FNetworkUtils.getNetworkOperatorName() + "2G网络（最佳范围>-90dBm 越大越好），信号强度 " + dbm + "    " + netStatusStr;
                } else if (netWorkType == FNetworkUtils.NetworkType.NETWORK_NO) {
                    netStatusStr = "网络不可用";
                    netStatusResult = netStatusStr;
                }
                if (netListener != null && count == 1) {
                    mTelephonyManager.listen(phoneStateListener, MyPhoneStateListener.LISTEN_NONE);
                    netListener.onNetListener(netStatusResult);
                    FLog.e("TAG", "网络监听进行中:" + netStatusResult);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private NetListener netListener;

    public interface NetListener {
        void onNetListener(String netStatusResult);
    }

    private int count = 0;

    public void setOnNetListener(NetListener netListener) {
        //监听信号强度
        count = 0;
        if (mTelephonyManager != null && phoneStateListener != null) {
            mTelephonyManager.listen(phoneStateListener, MyPhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
            this.netListener = netListener;
        } else {
            netListener.onNetListener("网络监听失败");
        }
    }


}
