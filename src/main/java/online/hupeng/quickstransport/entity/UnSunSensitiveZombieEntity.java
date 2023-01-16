package online.hupeng.quickstransport.entity;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * 对阳光不敏感的僵尸
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class UnSunSensitiveZombieEntity extends ZombieEntity {

    public UnSunSensitiveZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.xpReward = 20;
        this.setCustomName(new StringTextComponent("僵尸"));
    }

    public UnSunSensitiveZombieEntity(World world) {
        super(world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        //调整僵尸血量上限
        return ZombieEntity.createAttributes().add(Attributes.MAX_HEALTH, 40);
    }

    @Override
    protected boolean isSunSensitive() {
        return false;
    }

    @Override
    protected boolean isSunBurnTick() {
        return false;
    }

    @Override
    protected void populateDefaultEquipmentSlots(DifficultyInstance difficultyInstance) {
        ItemStack itemStack = new ItemStack(Items.IRON_SWORD);
        super.populateDefaultEquipmentSlots(difficultyInstance);
        this.setItemInHand(Hand.MAIN_HAND, itemStack);
    }

    @Override
    protected void populateDefaultEquipmentEnchantments(DifficultyInstance difficultyInstance) {
        ItemStack mainHandItemStack = this.getMainHandItem();
        if (!mainHandItemStack.isEmpty()) {
            if (this.getRandom().nextFloat() < 0.5F) {
                mainHandItemStack.enchant(Enchantments.UNBREAKING, this.getRandom().nextInt(2) + 1);
            }
            if (this.getRandom().nextFloat() < 0.3F) {
                mainHandItemStack.enchant(Enchantments.FIRE_ASPECT, 1);
            }
            if (this.getRandom().nextFloat() < 0.2F) {
                mainHandItemStack.enchant(Enchantments.SHARPNESS, 1);
            }
        }
        super.populateDefaultEquipmentEnchantments(difficultyInstance);
    }
}
