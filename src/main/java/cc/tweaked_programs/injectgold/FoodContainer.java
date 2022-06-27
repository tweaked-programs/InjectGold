package cc.tweaked_programs.injectgold;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.List;

public class FoodContainer {
    public FoodComponent.Builder stats;
    private Item item;
    private final Identifier KEY;
    private final char TYPE;
    private int hunger;
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


    public Item buildItem() {
        FabricItemSettings settings = new FabricItemSettings().group(Main.injectfood_group).rarity(Rarity.UNCOMMON);
        switch (TYPE) {
            case ('m'):
                stats.hunger(hunger+1);
                stats.saturationModifier(1.1f);
                stats.meat();
                settings.group(ItemGroup.FOOD);
                break;
            case ('s'):
                stats.hunger(hunger+2);
                stats.saturationModifier(0.75f);
                stats.snack();
                settings.group(ItemGroup.FOOD);
                break;
            case ('v'):
                stats.hunger(hunger+3);
                stats.saturationModifier(1.15f);
                settings.group(ItemGroup.FOOD);
                break;
            default:
                stats.hunger(hunger);
                stats.saturationModifier(1f);
                stats.alwaysEdible();
                settings.group(ItemGroup.FOOD);
                settings.rarity(Rarity.EPIC);
                break;
        }
        item = new Item(settings.food(stats.build()));
        return item;
    }

    public void addEffect(StatusEffectInstance effect, float chance) { statusEffects.add(new Pair<>(effect, chance)); }


    public Identifier getIdentifier() { return KEY; }

    public Item getItem() { return item; }

    public int getHunger() { return hunger; }

    public List<Pair<StatusEffectInstance, Float>> getStatusEffects() { return statusEffects; }
}
