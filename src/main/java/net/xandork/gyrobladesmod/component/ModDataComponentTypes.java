package net.xandork.gyrobladesmod.component;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.xandork.gyrobladesmod.GyrobladesMod;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, GyrobladesMod.MOD_ID);
    public static final RegistryObject<DataComponentType<BlockPos>> COORDINATES =
            register("coordinates", builder -> builder.persistent(BlockPos.CODEC));
    public static final RegistryObject<DataComponentType<ItemStack>> WRAPPEDBEIGOMA =
            register("wrapped_beigoma", builder -> builder.persistent(ItemStack.CODEC));
    //beigoma stats
    public static final RegistryObject<DataComponentType<Integer>> BEIATTACK =
            register("bei_attack", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));
    public static final RegistryObject<DataComponentType<Integer>> BEIHEALTH =
            register("bei_health", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));
    public static final RegistryObject<DataComponentType<Integer>> BEIIMPACT =
            register("bei_impact", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));
    public static final RegistryObject<DataComponentType<Integer>> BEIAGILITY =
            register("bei_agility", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));
    public static final RegistryObject<DataComponentType<Integer>> BEIWEIGHT =
            register("bei_weight", builder -> builder.persistent(ExtraCodecs.POSITIVE_INT));
    private static <T> RegistryObject<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }
    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
