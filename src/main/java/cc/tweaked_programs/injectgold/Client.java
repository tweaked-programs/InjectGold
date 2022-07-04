package cc.tweaked_programs.injectgold;

import cc.tweaked_programs.injectgold.block.BlockRegister;
import cc.tweaked_programs.injectgold.client.InjectorBlockEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegister.INJECTOR_BLOCK, RenderLayer.getCutout());
        BlockEntityRendererRegistry.register(BlockRegister.INJECTOR_BLOCK_ENTITY, InjectorBlockEntityRenderer::new);
    }
}
