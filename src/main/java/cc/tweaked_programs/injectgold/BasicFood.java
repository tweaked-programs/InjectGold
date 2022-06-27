package cc.tweaked_programs.injectgold;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import java.util.*;


public class BasicFood {
    private static final String[] LOG_MSG = { "Food is being served", "Food is cooling down", "Food is being prepared", "Food is being finished" };
    public static HashMap<String, FoodContainer> meat = new HashMap<>();
    public static HashMap<String, FoodContainer> sweet = new HashMap<>();
    public static HashMap<String, FoodContainer> vegan = new HashMap<>();
    public static HashMap<String, FoodContainer> weird = new HashMap<>();

    public static void init() {
        Main.LOGGER.info(LOG_MSG[new Random().nextInt(LOG_MSG.length)]);
        /* You can be vegan, or eat meat. But there are also sweets, oh and don't eat stuff that is weird! */
        meat.put("golden_cooked_porkchop", new FoodContainer("golden_cooked_porkchop", 8, 'm'));
        meat.put("golden_cooked_cod", new FoodContainer("golden_cooked_cod", 5, 'm'));
        meat.put("golden_cooked_salmon", new FoodContainer("golden_cooked_salmon", 6, 'm'));
        meat.put("golden_cooked_beef", new FoodContainer("golden_cooked_beef", 8, 'm'));
        meat.put("golden_cooked_chicken", new FoodContainer("golden_cooked_chicken", 6, 'm'));
        meat.put("golden_cooked_rabbit", new FoodContainer("golden_cooked_rabbit", 5, 'm'));
        meat.put("golden_cooked_mutton", new FoodContainer("golden_cooked_mutton", 6, 'm'));

        String n = "golden_rotten_flesh";
        meat.put(n, new FoodContainer(n, 4, 'm'));
        meat.get(n).addEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 100, 2), 1.0f);
        meat.get(n).addEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 500, 2), 1.0f);
        meat.get(n).addEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200), 1.0f);

        sweet.put("golden_cookie", new FoodContainer("golden_cookie", 2, 's'));
        sweet.put("golden_pumpkin_pie", new FoodContainer("golden_pumpkin_pie", 8, 's'));
        sweet.put("golden_sweet_berries", new FoodContainer("golden_sweet_berries", 2, 's'));
        sweet.put("golden_glow_berries", new FoodContainer("golden_glow_berries", 2, 's'));

        vegan.put("golden_bread", new FoodContainer("golden_bread", 5, 'v'));
        vegan.put("golden_baked_potato", new FoodContainer("golden_baked_potato", 5, 'v'));
        vegan.put("golden_poisonous_potato", new FoodContainer("golden_poisonous_potato", 2, 'v'));
        vegan.put("golden_beetroot", new FoodContainer("golden_beetroot", 1, 'v'));
        vegan.put("golden_chorus_fruit", new FoodContainer("golden_chorus_fruit", 4, 'v'));
        vegan.put("golden_dried_kelp", new FoodContainer("golden_dried_kelp", 1, 'v'));

        n = "golden_tropical_fish";
        weird.put(n, new FoodContainer(n, 1));
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.CONDUIT_POWER, 850), 1.0f);

        n = "golden_pufferfish";
        weird.put(n, new FoodContainer(n, 1));
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 450), 1.0f);

        n = "golden_spider_eye";
        weird.put(n, new FoodContainer(n, 2));
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 1000), 1.0f);
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.HUNGER, 200), 1.0f);

        n = "golden_fermented_spider_eye";
        weird.put(n, new FoodContainer(n, 3));
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 800), 1.0f);
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.SPEED, 800, 1), 1.0f);
        weird.get(n).addEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 800, 150, true, true, false), 1.0f);
    }

    public static void register() {
        for (Map.Entry<String, FoodContainer> m : meat.entrySet()) {
            Registry.register(Registry.ITEM, m.getValue().getIdentifier(), m.getValue().buildItem());
        } for (Map.Entry<String, FoodContainer> s : sweet.entrySet()) {
            Registry.register(Registry.ITEM, s.getValue().getIdentifier(), s.getValue().buildItem());
        } for (Map.Entry<String, FoodContainer> v : vegan.entrySet()) {
            Registry.register(Registry.ITEM, v.getValue().getIdentifier(), v.getValue().buildItem());
        } for (Map.Entry<String, FoodContainer> w : weird.entrySet()) {
            Registry.register(Registry.ITEM, w.getValue().getIdentifier(), w.getValue().buildItem());
        }
    }
}
