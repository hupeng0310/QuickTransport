package online.hupeng.quickstransport.network.msg;

import online.hupeng.quickstransport.constant.NetWorTypeEnum;

/**
 * 键盘输入数据包
 */
public class KeyInputMsg {

    /**
     * 网络数据包类型
     */
    NetWorTypeEnum netWorType;

    /**
     * keyCode
     */
    int keyCode;

    public NetWorTypeEnum getNetWorType() {
        return netWorType;
    }

    public KeyInputMsg setNetWorType(NetWorTypeEnum netWorType) {
        this.netWorType = netWorType;
        return this;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public KeyInputMsg setKeyCode(int keyCode) {
        this.keyCode = keyCode;
        return this;
    }

    public KeyInputMsg(NetWorTypeEnum netWorType, int keyCode) {
        this.netWorType = netWorType;
        this.keyCode = keyCode;
    }
}
