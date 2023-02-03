package online.hupeng.quickstransport.common.constant;

/**
 * 监听键盘按键枚举
 */
public enum KeyboardKey {

    B(66, "B"),
    C(67, "C"),
    V(86, "V"),
    X(88, "X"),
    Z(90, "Z");

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
