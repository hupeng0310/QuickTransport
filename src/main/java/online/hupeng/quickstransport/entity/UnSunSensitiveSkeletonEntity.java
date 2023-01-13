package online.hupeng.quickstransport.entity;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 对阳光不敏感的骷髅
 */

@ParametersAreNonnullByDefault
public class UnSunSensitiveSkeletonEntity extends SkeletonEntity {

    public UnSunSensitiveSkeletonEntity(EntityType<? extends SkeletonEntity> p_i50194_1_, World p_i50194_2_) {
        super(p_i50194_1_, p_i50194_2_);
        this.xpReward = 20;
    }


    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.createMonsterAttributes().add(Attributes.MAX_HEALTH, 30);
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty, SpawnReason spawnReason,
                                           @Nullable ILivingEntityData iLivingEntityData, @Nullable CompoundNBT compoundNBT) {
        if (difficulty.getDifficulty() == Difficulty.HARD) {
            ItemStack mainHandStack = this.getMainHandItem();
            if (mainHandStack.canApplyAtEnchantingTable(Enchantments.FLAMING_ARROWS)) {
                float f = 0;
                f = random.nextFloat();
                if (f < 0.5f) {
                    mainHandStack.enchant(Enchantments.FLAMING_ARROWS, 1);
                }
            }
        }
        return super.finalizeSpawn(world, difficulty, spawnReason, iLivingEntityData, compoundNBT);
    }
}
