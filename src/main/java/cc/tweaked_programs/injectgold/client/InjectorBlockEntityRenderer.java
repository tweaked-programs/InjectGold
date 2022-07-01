package cc.tweaked_programs.injectgold.client;

import cc.tweaked_programs.injectgold.Main;
import cc.tweaked_programs.injectgold.block.InjectorBlock;
import cc.tweaked_programs.injectgold.entity.InjectorBlockEntity;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InjectorBlockEntityRenderer implements BlockEntityRenderer<InjectorBlockEntity> {
    public InjectorBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(InjectorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Direction dir = Direction.NORTH;
        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof InjectorBlock)
            dir = Objects.requireNonNull(entity.getFacing());

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
        matrices.push();
        matrices.translate(0, 0.95, 0.5);

        int rot = 0;
        switch (dir) {
            case WEST:
                rot = 270;
                matrices.translate(0.06,-0.79,-0.16);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180-22.5f));
                break;
            case EAST:
                rot = 90;
                matrices.translate(0.94,-0.79,0.16);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180-22.5f));
                break;
            case NORTH:
                rot = 0;
                matrices.translate(0.66,-0.79,-0.44);
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-22.5f));
                break;
            case SOUTH:
                rot = 180;
                matrices.translate(0.335,-0.79,0.43);
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180-22.5f));
                break;
        }

        matrices.scale(0.003f,0.003f,0.003f);
        List<Text> term = new ArrayList<>();
        Pair<Text, Text> termInfo = entity.getLabel();
        term.add(termInfo.getFirst());
        term.add(termInfo.getSecond());
        float len = entity.getInjectTime()/InjectorBlockEntity.MAXTIME;
        String progressbar = ("::").repeat((int)(24*len));
        term.add(Text.empty()
                        .append(" [")
                        .append(progressbar+(" ").repeat((24-(int)(24*len))))
                        .append("]").formatted(Formatting.GRAY));
        int termPos = 0;
        for (Text label : term) {
            renderer.draw(label, 0, 12 * termPos, 0xffffff, false, matrices.peek().getPositionMatrix(), vertexConsumers, false, 0, light);
            termPos++;
        }
        matrices.pop();

        ItemStack input;
        ItemStack output = ItemStack.EMPTY;
        if (!entity.getStack(0).isEmpty() && !entity.getStack(1).isEmpty()) {
            output = entity.getStack(0);
            input = entity.getStack(1);
        } else if (entity.getStack(0).isEmpty() && !entity.getStack(1).isEmpty()) {
            input = entity.getStack(1);
        } else if (!entity.getStack(0).isEmpty() && entity.getStack(1).isEmpty()) {
            input = entity.getStack(0);
        } else { return; }

        if (!input.isEmpty()) {
            matrices.push();

            matrices.translate(0.5, 0.086, 0.5);
            matrices.scale(0.7f,0.7f,0.7f);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rot));

            MinecraftClient.getInstance().getItemRenderer().renderItem(input, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 1);

            matrices.pop();
        }

        if (!output.isEmpty()) {
            matrices.push();

            matrices.translate(0.5, 0.13, 0.5);
            matrices.scale(0.7f,0.7f,0.7f);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rot+45));

            MinecraftClient.getInstance().getItemRenderer().renderItem(output, ModelTransformation.Mode.FIXED, light, overlay, matrices, vertexConsumers, 1);

            matrices.pop();
        }
    }

    @Override
    public int getRenderDistance() {
        return 32;
    }
}
