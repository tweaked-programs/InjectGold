package cc.tweaked_programs.injectgold.integration;

import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Identifier;


import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class InjectGoldRecipeDisplay<T extends Recipe<Inventory>> implements Display {
    protected final InjectRecipe recipe;
    protected final int fuel;
    protected  EntryIngredient inputs;
    protected EntryIngredient outputs;

    public InjectGoldRecipeDisplay(InjectRecipe recipe) {
        this.recipe = recipe;
        this.inputs =  EntryIngredients.of(recipe.getInput());
        this.outputs = EntryIngredients.of(recipe.getOutput());
        this.fuel = recipe.getFuelAmount();
    }

    @Override
    public @Nonnull List<EntryIngredient> getInputEntries() {
        return Collections.singletonList(inputs);
    }

    @Override
    public @Nonnull
    List<EntryIngredient> getOutputEntries() {
        return Collections.singletonList(outputs);
    }

    public int getFuelAmount() { return fuel; }

    @Override
    public @Nonnull Optional<Identifier> getDisplayLocation() {
        return Optional.ofNullable(recipe).map(InjectRecipe::getId);
    }
}
