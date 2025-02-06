package net.xandork.gyrobladesmod.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.xandork.gyrobladesmod.GyrobladesMod;
import net.xandork.gyrobladesmod.entity.ModEntities;
import net.xandork.gyrobladesmod.entity.client.BeigomaModel;
import net.xandork.gyrobladesmod.entity.custom.BeigomaEntity;
import net.xandork.gyrobladesmod.entity.custom.BeigomaProjectileEntity;

@Mod.EventBusSubscriber(modid = GyrobladesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(BeigomaModel.LAYER_LOCATION,BeigomaModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.BEIGOMA.get(), BeigomaEntity.createAttributes().build());
    }
    @SubscribeEvent
    public void onDeath(LivingDeathEvent event){
        if (event.getEntity() instanceof BeigomaEntity){
            BeigomaEntity beigoma = (BeigomaEntity) event.getEntity();
            ItemEntity loot = new ItemEntity(event.getSource().getEntity().level(),event.getEntity().getX(),event.getEntity().getY(), event.getEntity().getZ(),beigoma.beigoma);
            event.getSource().getEntity().level().addFreshEntity(loot);
        }
    }
    @SubscribeEvent
    public void onLivingDropsEvent(LivingDropsEvent event){
        Entity entity = event.getEntity();
        Level world = event.getEntity().level();
        BlockPos pos = event.getEntity().getOnPos();

        if(entity instanceof BeigomaEntity){

            ItemEntity itemDropX = new ItemEntity(world, pos.getX()+.5, pos.getY()+.5, pos.getZ()+.5, ((BeigomaEntity) entity).getItemInHand(InteractionHand.MAIN_HAND));

			world.addFreshEntity(itemDropX);

            event.getDrops().add(itemDropX);

        }
    }
}
