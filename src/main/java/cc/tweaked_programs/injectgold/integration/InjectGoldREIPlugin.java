package cc.tweaked_programs.injectgold.integration;


import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;

import java.util.List;

public class InjectGoldREIPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry helper) {
        helper.add(List.of(
                new InjectREICategory()
        ));
    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerFiller(InjectRecipe.class, InjectREIDisplay::new);
    }
}
