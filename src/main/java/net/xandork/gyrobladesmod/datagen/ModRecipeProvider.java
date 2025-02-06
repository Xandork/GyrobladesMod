package net.xandork.gyrobladesmod.datagen;


import net.minecraft.client.Minecraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MinecartItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        List<ItemLike> GYRITE_SMELTABLES = List.of(ModItems.RAW_GYRITE.get(),
                ModBlocks.GYRITE_ORE.get(), ModBlocks.GYRITE_DEEPSLATE_ORE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.GYRITE_BLOCK.get())
                .pattern("AAA")
                .pattern("AAA")
                .pattern("AAA")
                .define('A', ModItems.GYRITE_CRYSTAL.get())
                .unlockedBy(getHasName(ModItems.GYRITE_CRYSTAL.get()), has(ModItems.GYRITE_CRYSTAL.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SHELL_BEIGOMA.get())
                .pattern(" W ")
                .pattern(" S ")
                .pattern(" B ")
                .define('W', Items.HONEYCOMB)
                .define('S',Items.SAND)
                .define('B', ModItems.BAI_SHELL.get())
                .unlockedBy(getHasName(ModItems.BAI_SHELL.get()), has(ModItems.BAI_SHELL.get())).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BARREL_ARENA.get())
                .pattern(" P ")
                .pattern(" B ")
                .define('P', Items.PAPER)
                .define('B',Items.BARREL)
                .unlockedBy(getHasName(ModItems.SHELL_BEIGOMA.get()), has(ModItems.SHELL_BEIGOMA.get())).save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GYRITE_CRYSTAL.get(), 9)
                .requires(ModBlocks.GYRITE_BLOCK.get())
                .unlockedBy(getHasName(ModBlocks.GYRITE_BLOCK.get()), has(ModBlocks.GYRITE_BLOCK.get())).save(pRecipeOutput);

        //ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, net.xandork.gyrobladesmod.item.ModItems.GYRITE_CRYSTAL.get(), 32)
        //        .requires(net.xandork.gyrobladesmod.block.ModBlocks.MAGIC_BLOCK.get())
        //        .unlockedBy(getHasName(net.xandork.gyrobladesmod.block.ModBlocks.GYRITE_BLOCK.get()), has(net.xandork.gyrobladesmod.block.ModBlocks.GYRITE_BLOCK.get()))
        //        .save(pRecipeOutput, GyrobladesMod.MOD_ID + ":alexandrite_from_magic_block");

        oreSmelting(pRecipeOutput, GYRITE_SMELTABLES, RecipeCategory.MISC, ModItems.GYRITE_CRYSTAL.get(), 0.25f, 200, "gyrite");
        oreBlasting(pRecipeOutput, GYRITE_SMELTABLES, RecipeCategory.MISC, ModItems.GYRITE_CRYSTAL.get(), 0.25f, 100, "gyrite");


        SmithingTrimRecipeBuilder.smithingTrim(Ingredient.of(ModItems.BAI_SHELL.get()),Ingredient.of(Items.STONE), Ingredient.of(ModItems.GYRITE_CRYSTAL.get()),RecipeCategory.MISC);


    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, GyrobladesMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
