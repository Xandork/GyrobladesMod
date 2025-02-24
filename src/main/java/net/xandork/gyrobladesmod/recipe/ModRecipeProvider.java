package net.xandork.gyrobladesmod.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    public void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapelessRecipeBuilder gyrobladeshapelessrecipe = ShapelessRecipeBuilder.shapeless(
                RecipeCategory.BUILDING_BLOCKS,  // Recipe category
                ModItems.GYROBLADE_ITEM.get(),   // Result item (ItemLike)
                1                                // Number of ingredients
        );

        gyrobladeshapelessrecipe.requires(Ingredient.of(ModTags.Items.RING_TAG))
                .requires(Ingredient.of(ModTags.Items.DISK_TAG))
                .requires(Ingredient.of(ModTags.Items.DRIVER_TAG))
                .requires(Ingredient.of(ModTags.Items.BLADE_TAG));

        gyrobladeshapelessrecipe.unlockedBy("has_ring", has(ModTags.Items.RING_TAG))
                .unlockedBy("has_disk", has(ModTags.Items.DISK_TAG))
                .unlockedBy("has_driver", has(ModTags.Items.DRIVER_TAG))
                .unlockedBy("has_blade", has(ModTags.Items.BLADE_TAG));


        System.out.println("Saving Shapeless Recipe: gyrobladesmod:gyroblade");

        gyrobladeshapelessrecipe.save(pRecipeOutput);

        System.out.println("Recipe Saved: gyrobladesmod:gyroblade");
    }

}

