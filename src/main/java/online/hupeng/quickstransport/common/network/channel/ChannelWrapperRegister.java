package online.hupeng.quickstransport.common.network.channel;

import online.hupeng.quickstransport.common.constant.ModConstant;
import online.hupeng.quickstransport.common.constant.NetWorkPackageType;
import online.hupeng.quickstransport.common.constant.SimpleChannels;

import java.util.concurrent.ConcurrentHashMap;

/**
 * channelWrapper注册器
 */
public class ChannelWrapperRegister {

    /**
     * 是否已初始化
     */
    private static boolean initialized = false;

    private final static ConcurrentHashMap<SimpleChannels, SimpleChannelWrapper> channelWrapperMap = new ConcurrentHashMap<>();

    static {
        channelWrapperMap.put(SimpleChannels.KEY_INPUT_CHANNEL,
                new KeyInputChannelWrapper(ModConstant.MOD_ID, NetWorkPackageType.KEY_INPUT));
    }

    public static <T> SimpleChannelWrapper<T> get(SimpleChannels channel) {
        return (SimpleChannelWrapper<T>) channelWrapperMap.get(channel);
    }

    public static void init() {
        if (!initialized) {
            channelWrapperMap.values().forEach(SimpleChannelWrapper::register);
            initialized = true;
        }
    }
}
