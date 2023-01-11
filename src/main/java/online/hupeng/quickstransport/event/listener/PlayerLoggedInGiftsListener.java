package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.DateUtil;
import online.hupeng.quickstransport.constant.ModConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

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
        int giftsQuantity;
        String tips;
        PlayerEntity player = playerLoggedInEvent.getPlayer();
        boolean newPlayer = !player.getPersistentData().getBoolean(ModConstant.PlayerPersistentData.OLD_PLAYER);
        if (newPlayer) {
            logger.info("该玩家为新玩家, 玩家uuidL {}, 玩家名称: {}", player.getUUID(), player.getName().getString());
            giftsQuantity = ModConstant.NEW_PLAYER_GIFTS_QUANTITY;
            tips = String.format("您已获得新用户奖励, 熟牛肉*%d", giftsQuantity);
        } else {
            giftsQuantity = ModConstant.PLAYER_LOGIN_GIFTS_QUANTITY;
            tips = String.format("您已获得登录奖励, 熟牛肉*%d", giftsQuantity);
        }
        if (haveDailyGifts(player.getPersistentData().getLong(ModConstant.PlayerPersistentData.LAST_LOGIN))) {
            ItemStack cookedBeefItemStack = new ItemStack(Items.COOKED_BEEF, giftsQuantity);
            if (!player.inventory.add(cookedBeefItemStack)) {
                logger.info("无法向玩家背包添加登录奖励，将为玩家生成掉落物");
                player.spawnAtLocation(cookedBeefItemStack);
            }
            logger.info("赠送玩家{}登录奖励成功！", player.getName().getString());
            player.sendMessage(new StringTextComponent(tips), player.getUUID());
        }
        player.getPersistentData().putBoolean(ModConstant.PlayerPersistentData.OLD_PLAYER, true);
        player.getPersistentData().putLong(ModConstant.PlayerPersistentData.LAST_LOGIN, new Date().getTime());
    }

    private static boolean haveDailyGifts(Long lastLoginTime) {
        if (lastLoginTime == null || lastLoginTime == 0) {
            return true;
        }
        Date rewardDate = DateUtil.getNextDay(lastLoginTime);
        return new Date().after(rewardDate);
    }
}
