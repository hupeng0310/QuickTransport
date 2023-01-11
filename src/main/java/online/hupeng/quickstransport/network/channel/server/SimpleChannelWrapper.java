package online.hupeng.quickstransport.network.channel.server;

import net.minecraftforge.fml.network.simple.SimpleChannel;
import online.hupeng.quickstransport.constant.NetWorkPackageType;

/**
 * simpleChannelWrapper包装类
 */
public abstract class SimpleChannelWrapper<MSG> {

    /**
     * channel名称（唯一标识）
     */
    private final String channelName;

    /**
     * 持有的simpleChannel引用
     */
    protected SimpleChannel simpleChannel;

    /**
     * 数据包类型
     */
    protected NetWorkPackageType packageType;

    /**
     * 数据包id生成方法
     * <p>当你每次发送数据包时，应为数据包指定一个不能重复的id</p>
     */
    protected abstract int getNetworkPackId();

    /**
     * 暴露simpleChannel注册方法
     */
    public abstract void register();

    /**
     * 向服务端发送数据包方法
     *
     * @param msg 自定义数据类
     */
    public void sendToServer(MSG msg) {
        simpleChannel.sendToServer(msg);
    }

    public String getChannelName() {
        return this.channelName;
    }

    public SimpleChannelWrapper(String channelName, NetWorkPackageType packageType) {
        this.channelName = channelName;
        this.packageType = packageType;
    }

}
