package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * 用户登陆播报事件监听器
 */
@Mod.EventBusSubscriber(Dist.DEDICATED_SERVER)
public class PlayerLoggedInBroadCastListener {

    /**
     * 监听用户登陆事件
     *
     * @param playerLoggedInEvent 用户登陆事件
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        PlayerEntity player = playerLoggedInEvent.getPlayer();
        Style style = Style.EMPTY.withColor(Color.parseColor("light_purple"));
        StringTextComponent textComponent = new StringTextComponent(String.format("欢迎%s进入游戏", player.getName().getString()));
        textComponent.setStyle(style);
        if (!player.getCommandSenderWorld().isClientSide()) {
            ServerWorld serverWorld = (ServerWorld) player.getCommandSenderWorld();
            List<ServerPlayerEntity> serverPlayerList = serverWorld.players();
            serverPlayerList.forEach(serverPlayer -> player.sendMessage(textComponent, serverPlayer.getUUID()));
        }
    }
}
