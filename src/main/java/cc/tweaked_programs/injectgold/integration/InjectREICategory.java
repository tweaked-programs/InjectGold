package cc.tweaked_programs.injectgold.integration;

import cc.tweaked_programs.injectgold.Main;
import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.DisplayRenderer;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.*;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.minecraft.stat.StatFormatter.DECIMAL_FORMAT;

public class InjectREICategory implements DisplayCategory<InjectREIDisplay> {
    public static final CategoryIdentifier<InjectREIDisplay> CATEGORY = CategoryIdentifier.of(Main.MOD_ID, InjectRecipe.Type.ID);
    private final Identifier OVERLAY = new Identifier(Main.MOD_ID, "textures/gui/injector_overlay.png");

    @Override
    public CategoryIdentifier<? extends InjectREIDisplay> getCategoryIdentifier() { return CATEGORY; }

    @Override
    public Text getTitle() { return Text.translatable("block.injectgold.injector_block"); }

    @Override
    public Renderer getIcon() { return EntryStacks.of(new ItemStack(Main.INJECTOR_BLOCK)); }

    @Override
    public @Nonnull
    List<Widget> setupDisplay(InjectREIDisplay display, Rectangle bounds) {
        Point start = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 41+9);
        List<Widget> widgets = new ArrayList<>();

        List<EntryIngredient> input = display.getInputEntries();
        List<EntryIngredient> output = display.getOutputEntries();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(start.x+42, start.y+23)));

        widgets.add(Widgets.createSlotBackground(new Point(start.x+1, start.y+24)));
        widgets.add(Widgets.createResultSlotBackground(new Point(start.x+95, start.y+24)));

        Integer fuelAmount = display.getFuelAmount();
        widgets.add(Widgets.createLabel(new Point(start.x+36, start.y+43), Text.translatable("category."+Main.MOD_ID+".fuel.needed").append(
                Text.of(fuelAmount.toString())).formatted(Formatting.GRAY)));

        widgets.add(Widgets.createSlot(new Point(start.x + 1, start.y + 24)).markInput().entries(input.get(0)));
        widgets.add(Widgets.createSlot(new Point(start.x+95, start.y+24)).markOutput().disableBackground().entries(output.get(0)));

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 46;
    }
}
