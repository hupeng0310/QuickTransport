package online.hupeng.quickstransport.event.listener;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.constant.EntityTypeConstant;
import online.hupeng.quickstransport.entity.UnSunSensitiveSkeletonEntity;
import online.hupeng.quickstransport.entity.UnSunSensitiveZombieEntity;


/**
 * 为实体注册能力
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributeAddListener {

    @SubscribeEvent
    public static void onEntityAttributeCreationEvent(EntityAttributeCreationEvent event) {
        event.put(EntityTypeConstant.UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE, UnSunSensitiveZombieEntity.createAttributes().build());
        event.put(EntityTypeConstant.UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE, UnSunSensitiveSkeletonEntity.createAttributes().build());
    }
}
