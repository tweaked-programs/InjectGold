package cc.tweaked_programs.injectgold.item;

import cc.tweaked_programs.injectgold.Main;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class FoodContainer {
    public FoodComponent.Builder stats;
    private GoldenFood item;
    private final Identifier KEY;
    private final char TYPE;
    private final int hunger;
    private final List<Pair<StatusEffectInstance, Float>> statusEffects = Lists.newArrayList();


    public FoodContainer(String name, int hunger, char type) {
        KEY = new Identifier(Main.MOD_ID, name);
        TYPE = type;
        this.hunger = hunger;
        this.stats = new FoodComponent.Builder();
    }

    public FoodContainer(String name, int hunger) {
        this(name, hunger, 'w');
    }


    public GoldenFood buildItem() {
        FabricItemSettings settings = new FabricItemSettings().group(Main.INJECTGOLD_GROUP).rarity(Rarity.COMMON);
        for (Pair<StatusEffectInstance, Float> effect : statusEffects)
            stats.statusEffect(effect.getFirst(), effect.getSecond());
        switch (TYPE) {
            case ('m') -> {
                stats.hunger(hunger + 1);
                stats.saturationModifier(1.1f);
                stats.meat();
                settings.group(ItemGroup.FOOD);
                settings.rarity(Rarity.UNCOMMON);
            }
            case ('s') -> {
                stats.hunger(hunger + 2);
                stats.saturationModifier(0.75f);
                stats.snack();
                stats.statusEffect(new StatusEffectInstance(StatusEffects.SPEED, 300 * hunger, (hunger / 4)), 0.35f);
                settings.group(ItemGroup.FOOD);
            }
            case ('v') -> {
                stats.hunger(hunger);
                stats.saturationModifier(1.15f);
                stats.statusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 20 * hunger), 0.1f);
                settings.group(ItemGroup.FOOD);
            }
            default -> {
                stats.hunger(hunger);
                stats.saturationModifier(0.5f);
                stats.alwaysEdible();
                settings.group(ItemGroup.FOOD);
                settings.rarity(Rarity.EPIC);
            }
        }
        item = new GoldenFood(KEY.getPath(), settings.food(stats.build()));
        return item;
    }

    public void addEffect(StatusEffectInstance effect, float chance) { statusEffects.add(new Pair<>(effect, chance)); }


    public Identifier getIdentifier() { return KEY; }

    public GoldenFood getItem() { return item; }

    public int getHunger() { return hunger; }

    public List<Pair<StatusEffectInstance, Float>> getStatusEffects() { return statusEffects; }
}
