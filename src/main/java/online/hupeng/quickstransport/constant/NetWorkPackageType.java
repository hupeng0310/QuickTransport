package online.hupeng.quickstransport.constant;

public enum NetWorkPackageType {

    KEY_INPUT;

    public static NetWorkPackageType fromOrdinal(int ordinal) {
        for (NetWorkPackageType netWorTypeEnum : NetWorkPackageType.values()) {
            if (netWorTypeEnum.ordinal() == ordinal) {
                return netWorTypeEnum;
            }
        }
        return null;
    }
}
