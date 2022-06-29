package cc.tweaked_programs.injectgold;

import cc.tweaked_programs.injectgold.block.GoldenCakeBlock;
import cc.tweaked_programs.injectgold.block.InjectorBlock;
import cc.tweaked_programs.injectgold.entity.InjectorBlockEntity;
import cc.tweaked_programs.injectgold.item.FoodRegister;
import cc.tweaked_programs.injectgold.item.FoodContainer;
import cc.tweaked_programs.injectgold.statuseffect.GoldRushStatusEffect;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.BlockItem;
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
	public static final StatusEffect GOLDRUSH = new GoldRushStatusEffect();
	public static final Block INJECTOR_BLOCK = new InjectorBlock(FabricBlockSettings.of(Material.STONE).strength(1.0f));
	public static BlockEntityType<InjectorBlockEntity> INJECTOR_BLOCK_ENTITY;

	@Override
	public void onInitialize() {
		/* Block(Entity)s */
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "golden_cake"), GOLDEN_CAKE);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "injector_block"), INJECTOR_BLOCK);
		INJECTOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "injector_block_entity"), FabricBlockEntityTypeBuilder.create(InjectorBlockEntity::new, INJECTOR_BLOCK).build(null));

		/* Items (& groups) */
		FoodRegister.init();
		injectfood_group = FabricItemGroupBuilder.create(new Identifier(MOD_ID, "main"))
				.icon(() -> new ItemStack(GOLDEN_CAKE))
				.appendItems(stacks -> {
					for (Map.Entry<String, FoodContainer> m : FoodRegister.meat.entrySet())
						stacks.add(new ItemStack(m.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> s : FoodRegister.sweet.entrySet())
						stacks.add(new ItemStack(s.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> v : FoodRegister.vegan.entrySet())
						stacks.add(new ItemStack(v.getValue().getItem()));
					for (Map.Entry<String, FoodContainer> w : FoodRegister.weird.entrySet())
						stacks.add(new ItemStack(w.getValue().getItem()));
					stacks.add(new ItemStack(GOLDEN_CAKE));
					stacks.add(new ItemStack(INJECTOR_BLOCK));
				}).build();
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "golden_cake"), new BlockItem(GOLDEN_CAKE, new FabricItemSettings().group(ItemGroup.FOOD).maxCount(1)));
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "injector_block"), new BlockItem(INJECTOR_BLOCK, new FabricItemSettings().group(injectfood_group)));
		FoodRegister.register();

		/* StatusEffect */
		Registry.register(Registry.STATUS_EFFECT, new Identifier(MOD_ID, "goldrush"), GOLDRUSH);
	}
}