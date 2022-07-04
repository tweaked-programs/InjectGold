package cc.tweaked_programs.injectgold;

import cc.tweaked_programs.injectgold.block.BlockRegister;
import cc.tweaked_programs.injectgold.item.ItemRegister;
import cc.tweaked_programs.injectgold.item.FoodContainer;
import cc.tweaked_programs.injectgold.recipe.RecipeRegister;
import cc.tweaked_programs.injectgold.statuseffect.GoldRushStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Main implements ModInitializer {
	public static final String MOD_ID = "injectgold";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemGroup INJECTGOLD_GROUP;
	public static final StatusEffect GOLDRUSH = new GoldRushStatusEffect();

	@Override
	public void onInitialize() {
		RecipeRegister.register();
		BlockRegister.register();
		ItemRegister.init();
		INJECTGOLD_GROUP = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "main"))
				.icon(() -> new ItemStack(BlockRegister.GOLDEN_CAKE))
				.appendItems(stacks -> {
					for (Map.Entry<String, FoodContainer> m : ItemRegister.meat.entrySet())
						stacks.add(new ItemStack(m.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> s : ItemRegister.sweet.entrySet())
						stacks.add(new ItemStack(s.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> v : ItemRegister.vegan.entrySet())
						stacks.add(new ItemStack(v.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> w : ItemRegister.weird.entrySet())
						stacks.add(new ItemStack(w.getValue().getItem()));
					stacks.add(new ItemStack(BlockRegister.GOLDEN_CAKE));
					stacks.add(new ItemStack(BlockRegister.INJECTOR_BLOCK));
					stacks.add(new ItemStack(BlockRegister.GROBLUN));
				}).build();
		ItemRegister.register();
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "goldrush"), GOLDRUSH);
	}
}