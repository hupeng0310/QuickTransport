package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.ParallelDispatchEvent;
import online.hupeng.quickstransport.constant.EntityTypeConstant;

/**
 * 监听此事件以添加怪物刷新条件
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ParallelDispatchEventListener {

    @SubscribeEvent
    public static void onParallelDispatchEvent(ParallelDispatchEvent event) {
        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(
                    EntityTypeConstant.UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
            EntitySpawnPlacementRegistry.register(
                    EntityTypeConstant.UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MonsterEntity::checkMonsterSpawnRules);
        });
    }
}
