package online.hupeng.quickstransport.common.constant;

import org.lwjgl.glfw.GLFW;

/**
 * 监听键盘按键枚举
 */
public enum KeyboardKey {

    B(GLFW.GLFW_KEY_B, "B"),
    C(GLFW.GLFW_KEY_C, "C"),
    V(GLFW.GLFW_KEY_V, "V"),
    X(GLFW.GLFW_KEY_X, "X"),
    Z(GLFW.GLFW_KEY_Z, "Z");

    private final int KeyCode;

    private final String key;

    public int getKeyCode() {
        return KeyCode;
    }

    public String getKey() {
        return key;
    }

    public static KeyboardKey get(int keyCode) {
        for (KeyboardKey keyboardKey : KeyboardKey.values()) {
            if (keyboardKey.KeyCode == keyCode) {
                return keyboardKey;
            }
        }
        return null;
    }

    KeyboardKey(int keyCode, String key) {
        KeyCode = keyCode;
        this.key = key;
    }
}
