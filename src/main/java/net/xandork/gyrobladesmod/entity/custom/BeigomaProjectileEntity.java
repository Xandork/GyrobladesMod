package net.xandork.gyrobladesmod.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec2;
import net.xandork.gyrobladesmod.entity.ModEntities;
import net.xandork.gyrobladesmod.item.ModItems;

public class BeigomaProjectileEntity extends ThrowableItemProjectile {
    int weightstability;
    int speedmaneuverability;
    int jumpheight;
    int attackdamage;
    int knockback;
    private float rotation;
    public Vec2 groundedOffset;
    public BeigomaProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel){
        super(pEntityType,pLevel);
    }
    public BeigomaProjectileEntity(Level pLevel){
        super(ModEntities.BEIGOMAPROJECTILE.get(),pLevel);
    }
    public BeigomaProjectileEntity(Level pLevel, LivingEntity livingEntity){
        super(ModEntities.BEIGOMAPROJECTILE.get(), livingEntity,pLevel);
        this.setOwner(livingEntity);
    }

    public void setAttributes(Component name, int w, int s, int j, int a, int k){
        setCustomName(name);
        weightstability = w;
        speedmaneuverability = s;
        jumpheight = j;
        attackdamage = a;
        knockback = k;
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.STONE_BEIGOMA.get();
    }

    //protected float getGravity() {
    //    return 0.20F;}
    //@Override
    //protected float func_70182_d() {
    //    return 0.5F; // this is the speed arrows fly
    //}

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        if(!this.level().isClientSide()){
            this.level().broadcastEntityEvent(this,((byte) 3));
            BeigomaEntity chicken = ModEntities.BEIGOMA.get().create(this.level());
            if (chicken != null) {
                chicken.setAge(-24000);
                chicken.setOwnerUUID((this.getOwner()).getUUID());
                chicken.setTame(true, false);
                chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(chicken);
                chicken.beigoma = this.getItem();
                chicken.setItemInHand(InteractionHand.MAIN_HAND, this.getItem());
                chicken.setAttributes(this.getName(),weightstability,speedmaneuverability,jumpheight,attackdamage,knockback);
            }
            //Will be the entity now
        }
        this.level().broadcastEntityEvent(this, (byte)3);
        this.discard();
        if(pResult.getDirection() == Direction.SOUTH){
            groundedOffset = new Vec2(215f,180f);
        }
        if(pResult.getDirection() == Direction.NORTH){
            groundedOffset = new Vec2(215f,0F);
        }
        if(pResult.getDirection() == Direction.EAST){
            groundedOffset = new Vec2(215f,-90F);
        }
        if(pResult.getDirection() == Direction.WEST){
            groundedOffset = new Vec2(215f,90F);
        }
        if(pResult.getDirection() == Direction.DOWN){
            groundedOffset = new Vec2(115F,180f);
        }
        if(pResult.getDirection() == Direction.UP){
            groundedOffset = new Vec2(285F,180f);
        }
        super.onHitBlock(pResult);
    }

    public float getRenderingRotation(){
        rotation += 0.5f;;
        if(rotation >= 360){
            rotation = 0;
        }
        return rotation;
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        if(!this.level().isClientSide()){
            this.level().broadcastEntityEvent(this,((byte) 3));
            //this.level().setBlock(blockPosition(), ((GyroTableBlock) ModBlocks.GYRO_TABLE.get()).getRandomBlockState(),3);

            //CompoundTag compoundtag = new CompoundTag();
            //compoundtag.putString("id", "gyroblademod:beigoma");
            //Entity entity = EntityType.loadEntityRecursive(compoundtag, this.level(), (e) -> {
            //    e.moveTo(this.xo, this.yo, this.zo, this.yRotO, this.xRotO);
            //    return e;
            //});
            //this.level().addFreshEntity(entity);

            BeigomaEntity chicken = ModEntities.BEIGOMA.get().create(this.level());
            if (chicken != null) {
                chicken.setAge(-24000);
                chicken.setOwnerUUID((this.getOwner()).getUUID());
                chicken.setTame(true, false);
                chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
                this.level().addFreshEntity(chicken);
                chicken.beigoma = this.getItem();
                chicken.setItemInHand(InteractionHand.MAIN_HAND, this.getItem());
                chicken.setAttributes(this.getName(),weightstability,speedmaneuverability,jumpheight,attackdamage,knockback);
                chicken.setCustomNameVisible(false);
            }
        }
        this.level().broadcastEntityEvent(this, (byte)3);
        this.discard();
        super.onHitEntity(pResult);
    }
    public Player getPlayerOwner(){
        return (Player) getOwner();
    }
}

