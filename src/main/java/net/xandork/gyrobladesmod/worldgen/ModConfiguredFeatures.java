package net.xandork.gyrobladesmod.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.fml.common.Mod;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.block.ModBlocks;

import java.util.List;

public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_GYRITE_ORE_KEY = registerKey("sapphire_ore");
    //public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_GYRITE_ORE_KEY = registerKey("nether_sapphire_ore");
    //public static final ResourceKey<ConfiguredFeature<?, ?>> END_GYRITE_ORE_KEY = registerKey("end_sapphire_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        //RuleTest netherrackReplacables = new BlockMatchTest(Blocks.NETHERRACK);
        //RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldSapphireOres = List.of(OreConfiguration.target(stoneReplaceable,
                net.xandork.gyrobladesmod.block.ModBlocks.GYRITE_ORE.get().defaultBlockState()),
                OreConfiguration.target(deepslateReplaceables, net.xandork.gyrobladesmod.block.ModBlocks.GYRITE_DEEPSLATE_ORE.get().defaultBlockState()));

        register(context, OVERWORLD_GYRITE_ORE_KEY, Feature.ORE, new OreConfiguration(overworldSapphireOres, 9));
        //register(context, NETHER_SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(netherrackReplacables,
        //        net.xandork.gyrobladesmod.block.ModBlocks.NETHER_SAPPHIRE_ORE.get().defaultBlockState(), 9));
        //register(context, END_SAPPHIRE_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables,
        //        net.xandork.gyrobladesmod.block.ModBlocks.END_STONE_SAPPHIRE_ORE.get().defaultBlockState(), 9));
    }


    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(GyrobladesMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
