package online.hupeng.quickstransport.event.listener;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.constant.NetWorTypeEnum;
import online.hupeng.quickstransport.network.handler.NetWorkHandler;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyInputListener {

    @SubscribeEvent
    public static void OnKeyInputEvent(InputEvent.KeyInputEvent keyInputEvent) {
        KeyInputMsg keyInputMsg = new KeyInputMsg(NetWorTypeEnum.KEY_INPUT, keyInputEvent.getKey());
        NetWorkHandler.KEY_INPUT.sendMessageToServer(keyInputMsg);
    }
}
