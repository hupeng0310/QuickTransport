package online.hupeng.quickstransport.constant;

public enum NetWorTypeEnum {

    KEY_INPUT;

    public static NetWorTypeEnum fromOrdinal(int ordinal) {
        for (NetWorTypeEnum netWorTypeEnum : NetWorTypeEnum.values()) {
            if (netWorTypeEnum.ordinal() == ordinal) {
                return netWorTypeEnum;
            }
        }
        return null;
    }
}
