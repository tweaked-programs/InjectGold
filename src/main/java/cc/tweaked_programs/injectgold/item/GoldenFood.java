package cc.tweaked_programs.injectgold.item;

import cc.tweaked_programs.injectgold.Main;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class GoldenFood extends Item {
    private final Pair<String, Formatting> TYPE;

    public GoldenFood(String id, Settings settings) {
        super(settings);
        this.TYPE = ItemRegister.getType(id);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return Objects.equals(TYPE.getFirst(), "weird");
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        tooltip.add(
                Text.translatable("item."+ Main.MOD_ID+".type.tooltip").formatted(Formatting.GRAY).append(
                        Text.translatable("item."+ Main.MOD_ID+"."+TYPE.getFirst()+".tooltip").formatted(TYPE.getSecond())
                ));
    }
}
