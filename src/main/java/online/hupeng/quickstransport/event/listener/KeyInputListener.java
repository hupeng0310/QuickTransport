package online.hupeng.quickstransport.event.listener;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.constant.NetWorkPackageType;
import online.hupeng.quickstransport.constant.SimpleChannels;
import online.hupeng.quickstransport.network.channel.ChannelWrapperRegister;
import online.hupeng.quickstransport.network.channel.server.SimpleChannelWrapper;
import online.hupeng.quickstransport.network.msg.KeyInputMsg;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyInputListener {

    @SubscribeEvent
    public static void OnKeyInputEvent(InputEvent.KeyInputEvent keyInputEvent) {
        if (keyInputEvent.getKey() == 66 && keyInputEvent.getAction() == GLFW.GLFW_PRESS) {
            KeyInputMsg keyInputMsg = new KeyInputMsg(NetWorkPackageType.KEY_INPUT, keyInputEvent.getKey());
            SimpleChannelWrapper<KeyInputMsg> simpleChannelWrapper = ChannelWrapperRegister.get(SimpleChannels.KEY_INPUT_CHANNEL);
            simpleChannelWrapper.sendToServer(keyInputMsg);
        }
    }
}
