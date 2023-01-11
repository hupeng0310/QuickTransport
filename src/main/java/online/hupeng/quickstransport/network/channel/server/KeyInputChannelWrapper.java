package online.hupeng.quickstransport.network.channel.server;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import online.hupeng.quickstransport.constant.ModConstant;
import online.hupeng.quickstransport.constant.NetWorkPackageType;
import online.hupeng.quickstransport.network.channel.server.logic.TransportLogic;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;

public class KeyInputChannelWrapper extends SimpleChannelWrapper<KeyInputMsg> {

    private int packageId = 1;

    @Override
    protected int getNetworkPackId() {
        return packageId++;
    }

    @Override
    public void register() {
        this.simpleChannel = NetworkRegistry.newSimpleChannel(new ResourceLocation(this.getChannelName()), () -> ModConstant.NETWORK_VERSION,
                ModConstant.NETWORK_VERSION::equals, ModConstant.NETWORK_VERSION::equals);
        this.simpleChannel.registerMessage(this.getNetworkPackId(), KeyInputMsg.class, (keyInputMsg, packetBuffer) -> {
            packetBuffer.writeInt(keyInputMsg.getNetWorkPackageType().ordinal());
            packetBuffer.writeInt(keyInputMsg.getKeyCode());
        }, packetBuffer -> new KeyInputMsg(NetWorkPackageType.fromOrdinal(packetBuffer.readInt()), packetBuffer.readInt()), new TransportLogic());
    }

    public KeyInputChannelWrapper(String channelName, NetWorkPackageType packageType) {
        super(channelName, packageType);
    }
}
