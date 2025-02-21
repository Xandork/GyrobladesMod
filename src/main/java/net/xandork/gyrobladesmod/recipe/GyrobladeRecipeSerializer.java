package net.xandork.gyrobladesmod.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.types.templates.Tag;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.xandork.gyrobladesmod.GyrobladesMod;

import java.util.*;
import java.util.stream.Collectors;

import net.minecraft.world.item.Item;
import net.minecraft.tags.TagKey;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class GyrobladeRecipeSerializer implements RecipeSerializer<GyrobladeRecipe> {
    public static final GyrobladeRecipeSerializer INSTANCE = new GyrobladeRecipeSerializer();
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, "gyroblade");// Instance for registration


    private static final MapCodec<GyrobladeRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                            Codec.STRING.optionalFieldOf("group", "").forGetter(GyrobladeRecipe::getGroup),
                            CraftingBookCategory.CODEC.fieldOf("category")
                                    .orElse(CraftingBookCategory.MISC).forGetter(GyrobladeRecipe::getCategory),
                            ItemStack.STRICT_CODEC.fieldOf("result").forGetter(GyrobladeRecipe::getResult),
                            Codec.list(Ingredient.CODEC).fieldOf("ingredients")
                                    .forGetter(GyrobladeRecipe::getIngredients)
                    )
                    .apply(instance, (group, category, result, ingredients) -> new GyrobladeRecipe(group, category, ingredients, result))
    );




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

        // Write the number of ingredients
        buf.writeVarInt(recipe.getIngredients().size());

        // Serialize ingredients
        for (Ingredient ingredient : recipe.getIngredients()) {
            Ingredient.CONTENTS_STREAM_CODEC.encode(buf, ingredient);
        }

        // Serialize the result (ItemStack)
        ItemStack.STREAM_CODEC.encode(buf, recipe.getResult());
    }

    private static GyrobladeRecipe fromNetwork(RegistryFriendlyByteBuf buf) {
        String group = buf.readUtf();
        CraftingBookCategory category = buf.readEnum(CraftingBookCategory.class);

        // Read the number of ingredients
        int ingredientCount = buf.readVarInt();
        List<Ingredient> ingredients = new ArrayList<>();

        // Use CONTENTS_STREAM_CODEC to decode each ingredient
        for (int i = 0; i < ingredientCount; i++) {
            ingredients.add(Ingredient.CONTENTS_STREAM_CODEC.decode(buf));
        }

        // Read the result item stack
        ItemStack result = ItemStack.STREAM_CODEC.decode(buf);

        // Debugging output
        System.out.println("📥 Deserialized Recipe: Group=" + group + ", Category=" + category);
        System.out.println("📥 Read " + ingredientCount + " ingredients: " + ingredients);
        System.out.println("📥 Result: " + result);

        if (ingredients.isEmpty()) {
            System.err.println("⚠️ Warning: Recipe has no ingredients after deserialization!");
        }

        return new GyrobladeRecipe(group, category, ingredients, result);
    }
}
