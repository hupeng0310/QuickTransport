package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.constant.KeyboardKey;
import online.hupeng.quickstransport.extra.ExtraWorldSaveData;
import online.hupeng.quickstransport.util.MinecraftUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class PlayerSetSpawnListener {

    private final static Logger logger = LogManager.getLogger(PlayerSetSpawnListener.class);

    @SubscribeEvent
    public static void onPlayerSetSpawnEvent(PlayerSetSpawnEvent playerSetSpawnEvent) {
        PlayerEntity player = playerSetSpawnEvent.getPlayer();
        BlockPos newSpawn = playerSetSpawnEvent.getNewSpawn();
        if (newSpawn != null) {
            ExtraWorldSaveData extraWorldSaveData = ExtraWorldSaveData.get(player.getCommandSenderWorld());
            logger.info("保存玩家重生点, 玩家uuid: {}, 出生点坐标: {}", player.getUUID(), MinecraftUtil.blockPosToString(newSpawn));
            extraWorldSaveData.putPlayerKeyPos(player.getUUID(), KeyboardKey.B.getKey(),
                    //记录高度比实际高度高一格，防止卡墙
                    Vector3d.upFromBottomCenterOf(MinecraftUtil.vector3i(newSpawn), 1));
        }
    }
}
