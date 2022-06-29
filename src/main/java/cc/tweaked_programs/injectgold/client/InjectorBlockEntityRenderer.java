package cc.tweaked_programs.injectgold.client;

import cc.tweaked_programs.injectgold.entity.InjectorBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class InjectorBlockEntityRenderer implements BlockEntityRenderer<InjectorBlockEntity> {
    public InjectorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(InjectorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack stack = entity.getStack(0);

        if (!stack.isEmpty()) {
            matrices.push();

            // Calculate the current offset in the y value
            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 4.0;
            // Move the item
            matrices.translate(0.5, 0.8 + offset, 0.5);

            // Rotate the item
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 4));

            MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 1);

            // Mandatory call after GL calls
            matrices.pop();
        }
    }
}
