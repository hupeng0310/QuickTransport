package online.hupeng.quickstransport.entity;

import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

/**
 * 对阳光不敏感的僵尸
 */
public class UnSunSensitiveZombieEntity extends ZombieEntity {

    public UnSunSensitiveZombieEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        this.xpReward = 20;
        ItemStack itemStack = new ItemStack(Items.IRON_SWORD);
        itemStack.enchant(Enchantments.FIRE_ASPECT, 1);
        this.setItemInHand(Hand.MAIN_HAND, itemStack);
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
}
