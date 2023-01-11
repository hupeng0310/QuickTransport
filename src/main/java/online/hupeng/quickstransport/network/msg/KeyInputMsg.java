package online.hupeng.quickstransport.network.msg;

import online.hupeng.quickstransport.constant.NetWorkPackageType;

/**
 * 键盘输入数据包
 */
public class KeyInputMsg {

    /**
     * 网络数据包类型
     */
    NetWorkPackageType netWorkPackageType;

    /**
     * keyCode
     */
    int keyCode;

    public NetWorkPackageType getNetWorkPackageType() {
        return netWorkPackageType;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public KeyInputMsg(NetWorkPackageType netWorkPackageType, int keyCode) {
        this.netWorkPackageType = netWorkPackageType;
        this.keyCode = keyCode;
    }
}
