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
            spawnInfoBuilder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityTypeConstant.UN_SUN_SENSITIVE_SKELETON_ENTITY_TYPE, 95, 10, 10))
                    .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityTypeConstant.UN_SUN_SENSITIVE_ZOMBIE_ENTITY_TYPE, 95, 10, 10));
            List<MobSpawnInfo.Spawners> spawnersList = spawnInfoBuilder.getSpawner(EntityClassification.MONSTER);
            //删除原有的僵尸和骷髅
            spawnersList.removeIf(spawners -> spawners.type == EntityType.ZOMBIE || spawners.type == EntityType.SKELETON);
        }
    }
}
