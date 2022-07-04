package cc.tweaked_programs.injectgold.statuseffect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class GoldRushStatusEffect extends StatusEffect {
    public GoldRushStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0xFFE684);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) { return true; }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        /*if (entity instanceof PlayerEntity) {

        }*/
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        /*if (entity instanceof PlayerEntity) {

        }*/
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        /*if (entity instanceof PlayerEntity) {
            //((PlayerEntity) entity).
        }*/
    }
}
