package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class PlayerLoggedInGiftsListener {

    private final static Logger logger = LoggerFactory.getLogger(PlayerLoggedInGiftsListener.class);

    /**
     * 监听用户登陆事件
     *
     * @param playerLoggedInEvent 用户登陆事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        PlayerEntity player = playerLoggedInEvent.getPlayer();
        ItemStack cookedBeefItemStack = new ItemStack(Items.COOKED_BEEF, 5);
        if (!player.inventory.add(cookedBeefItemStack)) {
            logger.info("无法向玩家背包添加登录奖励，将为玩家生成掉落物");
            player.spawnAtLocation(cookedBeefItemStack);
        }
        logger.info("赠送玩家{}登录奖励成功！", player.getName().getString());
        player.sendMessage(new StringTextComponent("您已获得登录奖励，熟牛肉*5"), player.getUUID());
    }
}
