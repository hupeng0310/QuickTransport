package online.hupeng.quickstransport.network.handler;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import online.hupeng.quickstransport.constant.ModConstant;
import online.hupeng.quickstransport.constant.NetWorTypeEnum;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum NetWorkHandler {

    KEY_INPUT;

    private static final Logger logger = LogManager.getLogger();

    private final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation(ModConstant.MOD_ID), () -> ModConstant.NETWORK_VERSION,
            ModConstant.NETWORK_VERSION::equals, ModConstant.NETWORK_VERSION::equals);

    /**
     * 数据包自增id
     */
    private int id = 1;

    public void sendMessageToServer(KeyInputMsg keyInputMsg) {
        channel.sendToServer(keyInputMsg);
    }

    public void register() {
        this.channel.registerMessage(id++, KeyInputMsg.class, (keyInputMsg, packetBuffer) -> {
            packetBuffer.writeInt(keyInputMsg.getNetWorType().ordinal());
            packetBuffer.writeInt(keyInputMsg.getKeyCode());
        }, packetBuffer -> new KeyInputMsg(NetWorTypeEnum.fromOrdinal(packetBuffer.readInt()), packetBuffer.readInt()), (keyInputMsg, contextSupplier) -> {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> {
                logger.info("接收到玩家的按键输入数据包, 数据包类型: {}, 按键code： {}", keyInputMsg.getNetWorType().name(), keyInputMsg.getKeyCode());
                if (NetWorTypeEnum.KEY_INPUT.equals(keyInputMsg.getNetWorType()) && keyInputMsg.getKeyCode() == 66) {
                    ServerPlayerEntity player = context.getSender();
                    if (player != null) {
                        logger.info("响应玩家传送请求, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                        BlockPos respawnPosition = player.getRespawnPosition();
                        if (respawnPosition != null) {
                            player.setPos(respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ());
                            logger.info("传送玩家成功, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                        } else {
                            logger.info("无法传送，玩家出生点无效, 玩家uuid: {}, 玩家名称: {}",  player.getUUID().toString(), player.getName().getString());
                            player.sendMessage(new StringTextComponent("无法传送，您的出生点无效"), player.getUUID());
                        }
                    } else {
                        logger.info("无法获取到玩家实体");
                    }
                }
            });
        });
    }
}
