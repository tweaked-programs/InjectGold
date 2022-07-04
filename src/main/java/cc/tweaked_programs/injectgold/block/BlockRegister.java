package cc.tweaked_programs.injectgold.block;

import cc.tweaked_programs.injectgold.block.entity.InjectorBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.CakeBlock;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static cc.tweaked_programs.injectgold.Main.MOD_ID;

public class BlockRegister {
    public static final CakeBlock GOLDEN_CAKE = new GoldenCakeBlock(FabricBlockSettings.of(Material.CAKE).strength(0.4f));
    public static final Block INJECTOR_BLOCK = new InjectorBlock(FabricBlockSettings.of(Material.STONE).strength(2.0f));
    public static BlockEntityType<InjectorBlockEntity> INJECTOR_BLOCK_ENTITY;
    public static final Block GROBLUN = new Block(FabricBlockSettings.of(Material.STONE).strength(3.0f));

    public static void register() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "golden_cake"), GOLDEN_CAKE);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "injector_block"), INJECTOR_BLOCK);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "groblun"), GROBLUN);

        INJECTOR_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MOD_ID, "injector_block_entity"), FabricBlockEntityTypeBuilder.create(InjectorBlockEntity::new, INJECTOR_BLOCK).build(null));
    }
}
