package online.hupeng.quickstransport.event.listener;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import online.hupeng.quickstransport.constant.EntityTypeConstant;

import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EntitySpawnListener {

    //监听区块加载事件
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void entitySpawn(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NONE) {
            MobSpawnInfoBuilder spawnInfoBuilder = event.getSpawns();
            List<MobSpawnInfo.Spawners> monsterList = spawnInfoBuilder.getSpawner(EntityClassification.MONSTER);
            int zombieIndex = -1, skeletonIndex = -1;
            for (int i = 0; i < monsterList.size(); i++) {
                MobSpawnInfo.Spawners spawner = monsterList.get(i);
                if (spawner.type == EntityType.SKELETON) {
                    skeletonIndex = i;
                }
                if (spawner.type == EntityType.ZOMBIE) {
                    zombieIndex = i;
                }
            }
            if (skeletonIndex != -1) {
                monsterList.remove(skeletonIndex);
                monsterList.add(new MobSpawnInfo.Spawners(EntityTypeConstant.UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE, 95, 6, 6));
            }
            if (zombieIndex != -1) {
                monsterList.remove(zombieIndex);
                monsterList.add(new MobSpawnInfo.Spawners(EntityTypeConstant.UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE, 95, 6, 6));
            }
        }
    }
}
