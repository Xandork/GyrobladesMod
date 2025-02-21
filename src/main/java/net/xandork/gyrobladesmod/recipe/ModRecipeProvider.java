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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    public void buildRecipes(RecipeOutput pRecipeOutput) {
        // Corrected constructor for ShapelessRecipeBuilder
        ShapelessRecipeBuilder gyrobladeshapelessrecipe = ShapelessRecipeBuilder.shapeless(
                RecipeCategory.BUILDING_BLOCKS,  // Recipe category
                ModItems.GYROBLADE_ITEM.get(),   // Result item (ItemLike)
                1                                // Number of ingredients
        );

        // Add ingredients to the shapeless recipe
        gyrobladeshapelessrecipe.requires(ModItems.RING_ITEM.get())
                .requires(ModItems.DISK_ITEM.get())
                .requires(ModItems.DRIVER_ITEM.get())
                .requires(ModItems.BLADE_ITEM.get());

        // Set unlock criteria
        gyrobladeshapelessrecipe.unlockedBy("has_ring_item", has(ModItems.RING_ITEM.get()))
                .unlockedBy("has_disk_item", has(ModItems.DISK_ITEM.get()))
                .unlockedBy("has_driver_item", has(ModItems.DRIVER_ITEM.get()))
                .unlockedBy("has_blade_item", has(ModItems.BLADE_ITEM.get()));

        // Debugging log to confirm recipe creation
        System.out.println("Saving Shapeless Recipe: gyrobladesmod:gyroblade");

        // Save the recipe
        gyrobladeshapelessrecipe.save(pRecipeOutput);

        // Check if the recipe was added successfully
        System.out.println("Recipe Saved: gyrobladesmod:gyroblade");
    }

}

