package online.hupeng.quickstransport.server.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 玩家登出事件监听器
 */
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class PlayerLoggedOutSavingListener {

    private final static Logger logger = LogManager.getLogger(PlayerLoggedOutSavingListener.class);

    @SubscribeEvent
    public static void onPlayerLoggedOutEvent(PlayerEvent.PlayerLoggedOutEvent playerLoggedOutEvent) {
        PlayerEntity player = playerLoggedOutEvent.getPlayer();
        logger.info("响应玩家登出事件保存世界存储信息, 玩家uuid: {}, 玩家名称: {}", player.getUUID(), player.getName().getString());
        DimensionSavedDataManager dimensionSavedDataManager = player.getCommandSenderWorld().getServer().overworld().getDataStorage();
        dimensionSavedDataManager.save();
    }
}
