package net.xandork.gyrobladesmod.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.ModTags;
import net.xandork.gyrobladesmod.component.ModDataComponentTypes;
import net.xandork.gyrobladesmod.item.ModItems;

import java.util.ArrayList;
import java.util.List;

public class GyrobladeRecipe implements Recipe<RecipeInput> {
    public static final Codec<GyrobladeRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.optionalFieldOf("group", "").forGetter(GyrobladeRecipe::getGroup),
            CraftingBookCategory.CODEC.fieldOf("category").forGetter(GyrobladeRecipe::getCategory),
            Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.getIngredients()),
            ItemStack.CODEC.fieldOf("result").forGetter(GyrobladeRecipe::getResult)
    ).apply(instance, (group, category, ingredients, result) -> {
        NonNullList<Ingredient> nonNullIngredients = NonNullList.create();
        nonNullIngredients.addAll(ingredients); // Convert list to NonNullList
        return new GyrobladeRecipe(group, category, nonNullIngredients, result);
    }));
    public final String group;
    public final CraftingBookCategory category;
    public final NonNullList<Ingredient> ingredients; // List of ResourceLocation for tags
    public final ItemStack result;
    public GyrobladeRecipe(String group, CraftingBookCategory category, NonNullList<Ingredient> ingredients, ItemStack result) {
        //super(category);
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
        System.out.println("GyrobladeRecipe created with ingredients: " + this.ingredients);
    }

    @Override
    public String getGroup() {
        return group;
    }

    public CraftingBookCategory getCategory() {
        return this.category;
    }
    @Override
    public RecipeType<?> getType() {
        return ModRecipes.GYROBLADE_RECIPE_TYPE.get();
    }
    @Override
    public NonNullList<Ingredient> getIngredients() {
        return ingredients;
    }

    // Getter for result (this is the one that was missing)
    public ItemStack getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "GyrobladeRecipe{ingredients=" + ingredients + ", result=" + result + "}";
    }


    @Override
    public boolean matches(RecipeInput pInput, Level pLevel) {

        if(pLevel.isClientSide){
            return false;
        }

        Ingredient ringIngredient = Ingredient.of(ModItems.RING_ITEM.get());
        Ingredient diskIngredient = Ingredient.of(ModItems.DISK_ITEM.get());
        Ingredient driverIngredient = Ingredient.of(ModItems.DRIVER_ITEM.get());
        Ingredient bladeIngredient = Ingredient.of(ModItems.BLADE_ITEM.get());

        if (pInput instanceof CraftingContainer craftingContainer) {
            boolean foundRing = false;
            boolean foundDisk = false;
            boolean foundDriver = false;
            boolean foundBlade = false;

            System.out.println("Crafting Container Items:");
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack stack = craftingContainer.getItem(i);
                if (!stack.isEmpty()) {
                    System.out.println("Slot " + i + ": " + stack.getItem().getDescriptionId());
                }
            }

            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack stack = craftingContainer.getItem(i);
                if (stack.isEmpty()) continue;

                System.out.println("Testing item: " + stack.getItem().getDescriptionId());

                if (ringIngredient.test(stack)) {
                    foundRing = true;
                } else if (diskIngredient.test(stack)) {
                    foundDisk = true;
                } else if (driverIngredient.test(stack)) {
                    foundDriver = true;
                } else if (bladeIngredient.test(stack)) {
                    foundBlade = true;
                }
            }

            System.out.println("Found ingredients:");
            System.out.println("Ring: " + foundRing);
            System.out.println("Disk: " + foundDisk);
            System.out.println("Driver: " + foundDriver);
            System.out.println("Blade: " + foundBlade);

            return foundRing && foundDisk && foundDriver && foundBlade;
        }

        return false;
    }

    @Override
    public ItemStack assemble(RecipeInput pInput, HolderLookup.Provider pRegistries) {
        ItemStack result = new ItemStack(ModItems.GYROBLADE_ITEM.get());

        List<ResourceLocation> texturesToMerge = new ArrayList<>();

        if (pInput instanceof CraftingContainer craftingContainer) {
            for (int i = 0; i < craftingContainer.getContainerSize(); i++) {
                ItemStack ingredient = craftingContainer.getItem(i);
                if (!ingredient.isEmpty()) {
                    ResourceLocation itemID = ForgeRegistries.ITEMS.getKey(ingredient.getItem());
                    if (itemID != null) {
                        ResourceLocation texturePath = ResourceLocation.fromNamespaceAndPath(itemID.getNamespace(), "textures/item/" + itemID.getPath() + ".png");

                        if (ingredient.is(ModTags.Items.DRIVER_TAG)) {
                            texturesToMerge.add(texturePath); // Driver first
                        } else if (ingredient.is(ModTags.Items.DISK_TAG)) {
                            texturesToMerge.add(texturePath); // Disk second
                        } else if (ingredient.is(ModTags.Items.RING_TAG)) {
                            texturesToMerge.add(texturePath); // Ring third
                        } else if (ingredient.is(ModTags.Items.BLADE_TAG)) {
                            texturesToMerge.add(texturePath); // Blade fourth
                        }
                    }
                }
            }
        }

        if (!texturesToMerge.isEmpty()) {
            result.set(ModDataComponentTypes.TEXTURE_PATHS.get(), texturesToMerge);
        } else {
            List<ResourceLocation> defaultTextures = List.of(
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/iron_balance.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/stone_disk0.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/wooden_ring1.png"),
                    ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "textures/item/iron_claw.png")
            );
            result.set(ModDataComponentTypes.TEXTURE_PATHS.get(), defaultTextures);
        }

        return result;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth == 3 && pHeight == 3;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider pRegistries) {
        return result.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.GYROBLADE_RECIPE_SERIALIZER.get();
    }
    public static class GyrobladeRecipeSerializer implements RecipeSerializer<GyrobladeRecipe> {
        //public final GyrobladeRecipeSerializer INSTANCE = new GyrobladeRecipeSerializer();
        public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyroblade");// Instance for registration


        public static final MapCodec<GyrobladeRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(GyrobladeRecipe::getGroup),
                CraftingBookCategory.CODEC.fieldOf("category").forGetter(GyrobladeRecipe::getCategory),
                Ingredient.CODEC.listOf().fieldOf("ingredients").forGetter(recipe -> recipe.getIngredients()),
                ItemStack.CODEC.fieldOf("result").forGetter(GyrobladeRecipe::getResult)
        ).apply(instance, (group, category, ingredients, result) -> {
            NonNullList<Ingredient> nonNullIngredients = NonNullList.create();
            nonNullIngredients.addAll(ingredients); // Convert list to NonNullList
            return new GyrobladeRecipe(group, category, nonNullIngredients, result);
        }));

        public static final StreamCodec<RegistryFriendlyByteBuf, GyrobladeRecipe> STREAM_CODEC = StreamCodec.of(
                GyrobladeRecipeSerializer::toNetwork, GyrobladeRecipeSerializer::fromNetwork
        );

        @Override
        public MapCodec<GyrobladeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, GyrobladeRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static void toNetwork(RegistryFriendlyByteBuf buf, GyrobladeRecipe recipe) {
            buf.writeUtf(recipe.getGroup());  // Write group (if necessary)
            buf.writeEnum(recipe.getCategory());  // Write category

            buf.writeVarInt(recipe.getIngredients().size());

            for (Ingredient ingredient : recipe.getIngredients()) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buf, recipe.getResult());
        }

        private static GyrobladeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
            String group = buf.readUtf();
            CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);

            int ingredientCount = buf.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientCount, Ingredient.EMPTY);

            for (int i = 0; i < ingredientCount; i++) {
                ingredients.set(i, Ingredient.CONTENTS_STREAM_CODEC.decode(buf));  // Deserialize each ingredient
            }

            ItemStack result = ItemStack.STREAM_CODEC.decode(buf);

            System.out.println("📥 Deserialized Recipe: Group=" + group + ", Category=" + category);
            System.out.println("📥 Read " + ingredientCount + " ingredients: " + ingredients);
            System.out.println("📥 Result: " + result);

            if (ingredients.isEmpty()) {
                System.err.println("⚠️ Warning: Recipe has no ingredients after deserialization!");
            }

            return new GyrobladeRecipe(group, category, ingredients, result);
        }


    }

}