package online.hupeng.quickstransport.constant;

/**
 * mod基础常量类
 */
public class ModConstant {

    /**
     * 模组id
     */
    public final static String MOD_ID = "quicktransport";

    /**
     * 网络包版本
     */
    public final static String NETWORK_VERSION = "1.0";

    /**
     * 新玩家登录赠送礼物数量
     */
    public final static int NEW_PLAYER_GIFTS_QUANTITY = 20;

    /**
     * 玩家日常登录赠送礼物数量
     */
    public final static int PLAYER_LOGIN_GIFTS_QUANTITY = 5;

    /**
     * 传送花费经验值
     */
    public final static int TRANSPORT_COST_EXPERIENCE = 50;

    /**
     * 玩家特殊NBT标签
     */
    public static class PlayerPersistentData {

        /**
         * 是否为老玩家
         */
        public final static String OLD_PLAYER = "OldPlayer";

        /**
         * 玩家上次登录时间
         */
        public final static String LAST_LOGIN = "LastLogin";
    }
}
