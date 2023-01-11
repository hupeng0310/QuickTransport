package online.hupeng.quickstransport.network.channel.server.logic;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import online.hupeng.quickstransport.constant.ModConstant;
import online.hupeng.quickstransport.constant.NetWorkPackageType;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * 客户端处理传送事件逻辑
 */
public class TransportLogic implements BiConsumer<KeyInputMsg, Supplier<NetworkEvent.Context>> {

    private final static Logger logger = LogManager.getLogger(TransportLogic.class);

    @Override
    public void accept(KeyInputMsg keyInputMsg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            logger.info("接收到玩家的按键输入数据包, 数据包类型: {}, 按键code： {}", keyInputMsg.getNetWorkPackageType().name(), keyInputMsg.getKeyCode());
            if (NetWorkPackageType.KEY_INPUT.equals(keyInputMsg.getNetWorkPackageType()) && keyInputMsg.getKeyCode() == 66) {
                ServerPlayerEntity player = context.getSender();
                if (player != null) {
                    logger.info("响应玩家传送请求, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                    BlockPos respawnPosition = player.getRespawnPosition();
                    if (respawnPosition != null) {
                        if (player.totalExperience < ModConstant.TRANSPORT_COST_EXPERIENCE) {
                            logger.info("无法传送玩家, 玩家经验值不足, 玩家uuid: {}, 玩家名称: {}, 玩家经验值: {}",
                                    player.getUUID().toString(), player.getName().getString(), player.totalExperience);
                            player.sendMessage(new StringTextComponent(String.format("您的经验小于%d, 无法传送",
                                    ModConstant.TRANSPORT_COST_EXPERIENCE)), player.getUUID());
                            return;
                        }
                        player.giveExperiencePoints(-ModConstant.TRANSPORT_COST_EXPERIENCE);
                        player.moveTo(respawnPosition.getX(), respawnPosition.getY(), respawnPosition.getZ());
                        logger.info("传送玩家成功, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                        return;
                    }
                    logger.info("无法传送，玩家重生点无效, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                    player.sendMessage(new StringTextComponent("无法传送，您的重生点无效"), player.getUUID());
                    return;
                }
                logger.info("无法获取到玩家实体");
            }
        });
    }
}
