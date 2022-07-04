package cc.tweaked_programs.injectgold.integration;

import cc.tweaked_programs.injectgold.block.BlockRegister;
import cc.tweaked_programs.injectgold.integration.rei.InjectREICategory;
import cc.tweaked_programs.injectgold.integration.rei.InjectREIDisplay;
import cc.tweaked_programs.injectgold.recipe.InjectRecipe;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;

import java.util.List;

public class REIPlugin implements REIClientPlugin {
    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.addWorkstations(InjectREICategory.CATEGORY, EntryStacks.of(BlockRegister.INJECTOR_BLOCK));
        registry.add(List.of(
                new InjectREICategory()
        ));
    }

    @Override
    public void registerDisplays(DisplayRegistry helper) {
        helper.registerFiller(InjectRecipe.class, InjectREIDisplay::new);
    }
}
