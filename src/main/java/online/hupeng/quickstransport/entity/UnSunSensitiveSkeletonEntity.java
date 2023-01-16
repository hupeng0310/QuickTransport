package online.hupeng.quickstransport.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 对阳光不敏感的骷髅
 */

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnSunSensitiveSkeletonEntity extends SkeletonEntity {

    public UnSunSensitiveSkeletonEntity(EntityType<? extends SkeletonEntity> p_i50194_1_, World p_i50194_2_) {
        super(p_i50194_1_, p_i50194_2_);
        this.xpReward = 20;
        this.setCustomName(new StringTextComponent("骷髅"));
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
        return super.finalizeSpawn(world, difficulty, spawnReason, iLivingEntityData, compoundNBT);
    }

    @Override
    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficultyInstance) {
        if (this.getRandom().nextFloat() < 0.8) {
            this.getMainHandItem().enchant(Enchantments.FLAMING_ARROWS, 1);
        }
        if (this.getRandom().nextFloat() < 0.3) {
            this.getMainHandItem().enchant(Enchantments.POWER_ARROWS, this.getRandom().nextInt(2) + 1);
        }
        super.populateDefaultEquipmentEnchantments(difficultyInstance);
    }
}
