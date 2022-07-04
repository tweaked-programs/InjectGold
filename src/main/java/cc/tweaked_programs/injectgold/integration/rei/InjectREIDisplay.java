package cc.tweaked_programs.injectgold.integration.rei;

import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

import javax.annotation.Nonnull;

public class InjectREIDisplay extends InjectGoldRecipeDisplay<InjectRecipe> {
    public InjectREIDisplay(InjectRecipe recipe) {
        super(recipe);
    }

    @Override
    public @Nonnull
    CategoryIdentifier<?> getCategoryIdentifier() {
        return InjectREICategory.CATEGORY;
    }
}