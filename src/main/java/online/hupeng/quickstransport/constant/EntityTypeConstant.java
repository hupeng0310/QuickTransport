package online.hupeng.quickstransport.constant;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import online.hupeng.quickstransport.entity.UnSunSensitiveSkeletonEntity;
import online.hupeng.quickstransport.entity.UnSunSensitiveZombieEntity;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 实体类型
 */
@ParametersAreNonnullByDefault
public class EntityTypeConstant {

    /**
     * 不受阳光影响的僵尸
     */
    public final static EntityType<UnSunSensitiveZombieEntity> UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE;

    /**
     * 不受阳光影响的骷髅
     */
    public final static EntityType<UnSunSensitiveSkeletonEntity> UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE;

    static {
        UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE = EntityType.Builder.<UnSunSensitiveZombieEntity>of(UnSunSensitiveZombieEntity::new, EntityClassification.MONSTER)
                .sized(0.6F, 1.95F)
                .clientTrackingRange(8)
                .build("zombie");
        UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE.setRegistryName("quicktransport", "zombie");

        UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE = EntityType.Builder.<UnSunSensitiveSkeletonEntity>of(UnSunSensitiveSkeletonEntity::new, EntityClassification.MONSTER)
                .sized(0.6F, 1.99F)
                .clientTrackingRange(8)
                .build("skeleton");
        UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE.setRegistryName("quicktransport", "skeleton");
    }
}
