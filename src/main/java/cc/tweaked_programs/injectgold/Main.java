package cc.tweaked_programs.injectgold;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Main implements ModInitializer {
	public static final String MOD_ID = "injectgold";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static ItemGroup injectfood_group;
	public static final CakeBlock GOLDEN_CAKE = new GoldenCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(1.0f));

	@Override
	public void onInitialize() {
		BasicFood.init();
		injectfood_group = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "main"))
				.icon(() -> new ItemStack(GOLDEN_CAKE))
				.appendItems(stacks -> {
					for (Map.Entry<String, FoodContainer> m : BasicFood.meat.entrySet())
						stacks.add(new ItemStack(m.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> s : BasicFood.sweet.entrySet())
						stacks.add(new ItemStack(s.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> v : BasicFood.vegan.entrySet())
						stacks.add(new ItemStack(v.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> w : BasicFood.weird.entrySet())
						stacks.add(new ItemStack(w.getValue().getItem()));
					stacks.add(new ItemStack(GOLDEN_CAKE));
				}).build();

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "golden_cake"), GOLDEN_CAKE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "golden_cake"), new BlockItem(GOLDEN_CAKE, new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1)));
		BasicFood.register();
	}
}