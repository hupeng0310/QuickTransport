package online.hupeng.quickstransport.network.channel.server.logic;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;
import online.hupeng.quickstransport.constant.KeyboardKey;
import online.hupeng.quickstransport.constant.ModConstant;
import online.hupeng.quickstransport.constant.NetWorkPackageType;
import online.hupeng.quickstransport.extra.ExtraWorldSaveData;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;
import online.hupeng.quickstransport.util.MinecraftUtil;
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
        int key = keyInputMsg.getKeyCode();
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> {
            logger.info("接收到玩家的按键输入数据包, 数据包类型: {}, 按键code： {}", keyInputMsg.getNetWorkPackageType().name(), key);
            if (!NetWorkPackageType.KEY_INPUT.equals(keyInputMsg.getNetWorkPackageType())) {
                logger.info("客户端发送键盘时间数据包无效, 客户端请求数据包类型: {}",
                        keyInputMsg.getNetWorkPackageType() != null ? keyInputMsg.getNetWorkPackageType().name() : "null");
                return;
            }
            KeyboardKey keyboardKey = KeyboardKey.get(key);
            if (keyboardKey == null) {
                logger.info("服务端接收到未知按键, 按键keyCode: {}", key);
                return;
            }
            ServerPlayerEntity player = context.getSender();
            if (player == null) {
                logger.info("无法获取发起事件玩家实体");
                return;
            }
            logger.info("服务端响应玩家键盘监听事件, 玩家uuid: {}, 玩家名称: {}, keyCode: {}", player.getUUID(), player.getName().getString(), key);
            ExtraWorldSaveData extraWorldSaveData = ExtraWorldSaveData.get(player.getCommandSenderWorld());
            switch (keyboardKey) {
                case B:
                    logger.info("响应玩家传送请求, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                    Vector3d respawnPosition = extraWorldSaveData.getPlayerKeyPos(player.getUUID(), KeyboardKey.B.getKey());
                    if (respawnPosition == null) {
                        logger.info("无法传送，玩家重生点无效, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
                        player.sendMessage(new StringTextComponent("无法传送，您的重生点无效"), player.getUUID());
                        return;
                    }
                    transportPlayer(player, respawnPosition);
                    break;
                case C:
                    Vector3d zPosition = extraWorldSaveData.getPlayerKeyPos(player.getUUID(), KeyboardKey.Z.getKey());
                    if (zPosition == null) {
                        logger.info("无法传送，玩家未设置{}键坐标, 玩家uuid: {}, 玩家名称: {}", KeyboardKey.Z.getKey(), player.getUUID().toString(), player.getName().getString());
                        player.sendMessage(new StringTextComponent("无法传送，您未设置Z点坐标，请按键盘Z设置Z点坐标"), player.getUUID());
                        return;
                    }
                    transportPlayer(player, zPosition);
                    break;
                case V:
                    Vector3d vPosition = extraWorldSaveData.getPlayerKeyPos(player.getUUID(), KeyboardKey.X.getKey());
                    if (vPosition == null) {
                        logger.info("无法传送，玩家未设置{}键坐标, 玩家uuid: {}, 玩家名称: {}", KeyboardKey.V.getKey(), player.getUUID().toString(), player.getName().getString());
                        player.sendMessage(new StringTextComponent("无法传送，您未设置X点坐标，请按键盘X设置X点坐标"), player.getUUID());
                        return;
                    }
                    transportPlayer(player, vPosition);
                    break;
                case X:
                case Z:
                    addPlayerPos(player, keyboardKey);
                    player.sendMessage(new StringTextComponent(String.format("已为您设置%s键坐标点%s", keyboardKey.getKey(),
                            MinecraftUtil.v3dToString(player.position()))), player.getUUID());
                    break;
            }
        });
    }

    private void addPlayerPos(PlayerEntity player, KeyboardKey keyboardKey) {
        ExtraWorldSaveData extraWorldSaveData = ExtraWorldSaveData.get(player.getCommandSenderWorld());
        extraWorldSaveData.putPlayerKeyPos(player.getUUID(), keyboardKey.getKey(), player.position());
    }



    private void transportPlayer(PlayerEntity player, Vector3d pos) {
        if (player.totalExperience < ModConstant.TRANSPORT_COST_EXPERIENCE) {
            logger.info("无法传送玩家, 玩家经验值不足, 玩家uuid: {}, 玩家名称: {}, 玩家经验值: {}",
                    player.getUUID().toString(), player.getName().getString(), player.totalExperience);
            player.sendMessage(new StringTextComponent(String.format("您的经验值为%d，小于%d, 无法传送",
                    player.totalExperience, ModConstant.TRANSPORT_COST_EXPERIENCE)), player.getUUID());
            return;
        }
        player.giveExperiencePoints(-ModConstant.TRANSPORT_COST_EXPERIENCE);
        player.moveTo(pos);
        logger.info("传送玩家成功, 玩家uuid: {}, 玩家名称: {}", player.getUUID().toString(), player.getName().getString());
    }
}
