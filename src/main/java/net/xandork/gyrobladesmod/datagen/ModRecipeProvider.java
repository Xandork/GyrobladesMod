package net.xandork.gyrobladesmod.datagen;


import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
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
import net.xandork.gyrobladesmod.item.PartItems;
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.recipe.CustomRecipeBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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


        CustomRecipeBuilder gyrobladeRecipe = new CustomRecipeBuilder(
                "",
                CraftingBookCategory.MISC,
                ModItems.GYROBLADE_ITEM.get(),
                1
        );

        gyrobladeRecipe.requires(ModTags.Items.RING_TAG)
                .requires(ModTags.Items.DISK_TAG)
                .requires(ModTags.Items.DRIVER_TAG)
                .requires(ModTags.Items.BLADE_TAG);

        gyrobladeRecipe.unlockedBy("has_ring_item", has(ModItems.RING_ITEM.get()).triggerInstance())
                .unlockedBy("has_disk_item", has(ModItems.DISK_ITEM.get()).triggerInstance())
                .unlockedBy("has_driver_item", has(ModItems.DRIVER_ITEM.get()).triggerInstance())
                .unlockedBy("has_blade_item", has(ModItems.BLADE_ITEM.get()).triggerInstance());

        gyrobladeRecipe.save(pRecipeOutput, "gyroblade_item");






        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_CLAW.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_CRESCENT.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_DEVIL.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_GUST.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_TALON.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_VENOM.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_WAVE.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_DISK0.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_DISK3.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_DISK4.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_RING1.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_RING2.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_RING3.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_ATTACK.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                getFirstItemFromTag(ItemTags.PLANKS), // Any wooden planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.WOODEN_BALANCE.get(), // Resulting wooden ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "wooden_balance") // Recipe ID
        );

        ///Stone

        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_CLAW.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_CRESCENT.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_DEVIL.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_GUST.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_TALON.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_VENOM.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_WAVE.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_DISK0.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_DISK3.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_DISK4.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_RING1.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_RING2.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_RING3.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_ATTACK.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                Items.COBBLESTONE, // Any stone planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.STONE_BALANCE.get(), // Resulting stone ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "stone_balance") // Recipe ID
        );

        /// iron

        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_CLAW.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_CRESCENT.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_DEVIL.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_GUST.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_TALON.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_VENOM.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_WAVE.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_DISK0.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_DISK3.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_DISK4.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_RING1.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_RING2.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_RING3.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_ATTACK.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                Items.IRON_INGOT, // Any iron planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.IRON_BALANCE.get(), // Resulting iron ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "iron_balance") // Recipe ID
        );

        /// gold

        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_CLAW.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_CRESCENT.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_DEVIL.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_GUST.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_TALON.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_VENOM.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_WAVE.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_DISK0.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_DISK3.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_DISK4.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_RING1.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_RING2.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_RING3.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_ATTACK.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                Items.GOLD_INGOT, // Any gold planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.GOLD_BALANCE.get(), // Resulting gold ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gold_balance") // Recipe ID
        );

        /// diamond

        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_CLAW.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_CRESCENT.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_DEVIL.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_GUST.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_TALON.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_VENOM.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_WAVE.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_DISK0.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_DISK3.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_DISK4.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_RING1.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_RING2.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_RING3.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_ATTACK.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                Items.DIAMOND, // Any diamond planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.DIAMOND_BALANCE.get(), // Resulting diamond ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "diamond_balance") // Recipe ID
        );

        /// netherite

        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CLAW.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_CLAW.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_claw") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_CRESCENT.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_CRESCENT.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_crescent") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DEVIL.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_DEVIL.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_devil") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_GUST.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_GUST.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_gust") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_TALON.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_TALON.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_talon") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_VENOM.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_VENOM.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_venom") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_WAVE.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_WAVE.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_wave") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK0.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_DISK0.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_disk0") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK3.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_DISK3.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_disk3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_DISK4.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_DISK4.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_disk4") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING1.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_RING1.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_ring1") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING2.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_RING2.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_ring2") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_RING3.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_RING3.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_ring3") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_ATTACK.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_ATTACK.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_attack") // Recipe ID
        );
        generateSmithingTransformRecipe(pRecipeOutput,
                PartItems.TEMPLATE_BALANCE.get(), // Smithing template
                Items.NETHERITE_INGOT, // Any netherite planks
                ModItems.GYRITE_CRYSTAL.get(), // Addition material
                PartItems.NETHERITE_BALANCE.get(), // Resulting netherite ring
                ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "netherite_balance") // Recipe ID
        );


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
    private void generateSmithingTransformRecipe(RecipeOutput output, Item template, Item base, Item addition, Item result, ResourceLocation id) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(base), Ingredient.of(addition), RecipeCategory.MISC, result)
                .unlocks("has_template_material", has(template)) // Advancement condition
                .save(output, id);
    }
    private Item getFirstItemFromTag(TagKey<Item> tagKey) {
        return BuiltInRegistries.ITEM.getTag(tagKey)
                .flatMap(tag -> tag.stream().findFirst())
                .map(Holder::value)
                .orElse(Items.OAK_PLANKS); // Fallback item
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, GyrobladesMod.MOD_ID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
