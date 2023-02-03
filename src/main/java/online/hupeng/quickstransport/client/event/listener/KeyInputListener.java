package online.hupeng.quickstransport.client.event.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.common.constant.KeyboardKey;
import online.hupeng.quickstransport.common.constant.NetWorkPackageType;
import online.hupeng.quickstransport.common.constant.SimpleChannels;
import online.hupeng.quickstransport.common.network.channel.ChannelWrapperRegister;
import online.hupeng.quickstransport.common.network.channel.SimpleChannelWrapper;
import online.hupeng.quickstransport.common.network.msg.KeyInputMsg;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class KeyInputListener {

    private final static Logger LOGGER = LogManager.getLogger(KeyInputListener.class);

    @SubscribeEvent
    public static void OnKeyInputEvent(InputEvent.Key keyEvent) {
        /*
        仅监听按键按下事件切聊天窗口处于隐藏状态
         */
        int keyCode = keyEvent.getKey();
        if (keyEvent.getAction() == GLFW.GLFW_PRESS && !(Minecraft.getInstance().screen instanceof ChatScreen)) {
            //仅监听CTR+Z, CTR+X, CTR+C, CTR+V, B快捷键
            if (keyEvent.getModifiers() == GLFW.GLFW_MOD_CONTROL &&
                    (keyCode == GLFW.GLFW_KEY_Z || keyCode == GLFW.GLFW_KEY_X || keyCode == GLFW.GLFW_KEY_C || keyCode == GLFW.GLFW_KEY_V) || keyCode == GLFW.GLFW_KEY_B) {
                LOGGER.info("用户触发传送快捷键: {}", KeyboardKey.get(keyCode).getKey());
                KeyInputMsg keyInputMsg = new KeyInputMsg(NetWorkPackageType.KEY_INPUT, keyEvent.getKey());
                SimpleChannelWrapper<KeyInputMsg> simpleChannelWrapper = ChannelWrapperRegister.get(SimpleChannels.KEY_INPUT_CHANNEL);
                simpleChannelWrapper.sendToServer(keyInputMsg);
            }
        }
    }
}
