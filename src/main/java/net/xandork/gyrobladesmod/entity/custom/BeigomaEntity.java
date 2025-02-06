package net.xandork.gyrobladesmod.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.xandork.gyrobladesmod.block.ModBlocks;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.Attr;

public class BeigomaEntity extends TamableAnimal{
    //stats
    public ItemStack beigoma;
    int weightstability;
    int speedmaneuverability;
    int jumpheight;
    int attackdamage;
    int knockback;

    private float rotation;
    public BeigomaEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel){
        super ((EntityType<? extends TamableAnimal>) pEntityType,pLevel);
    }

    public void setAttributes(Component name, int w, int s, int j, int a, int k){
        setCustomName(name);
        weightstability = w;
        speedmaneuverability = s;
        jumpheight = j;
        attackdamage = a;
        knockback = k;
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    //public void setOwner(LivingEntity owner){
    //    this.isOwnedBy(owner);
    //}

    @Override
    public void tick(){
        super.tick();
        if(this.level().isClientSide()) {
            setupAnimationStates();
        }
        //getRenderingRotation();
        this.checkSurface();
    }

    private void checkSurface(){
        //Blocks[] blocks = new Blocks{Blocks.BLACK_WOOL.getClass()};
        if(this.isInLiquid()){
            this.setHealth(this.getHealth()-1);
        }
        if(this.level().getBlockState(this.getOnPos()).getBlock().getName().toString().endsWith("WOOL")){
            this.setHealth(this.getHealth()-1);
        }
        if(this.level().getBlockState(this.getOnPos()).getBlock() == Blocks.GRASS_BLOCK){
            this.setHealth(this.getHealth()-1);
        }
        if(this.level().getBlockState(this.getOnPos().north()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().north().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(0,1,0));
            this.move(MoverType.SELF,new Vec3(0,0,-0.05));
        }
        if(this.level().getBlockState(this.getOnPos().south()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().south().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(0,-1,0));
            this.move(MoverType.SELF,new Vec3(0,0,0.05));
        }
        if(this.level().getBlockState(this.getOnPos().east()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().east().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(1,0,0));
            this.move(MoverType.SELF,new Vec3(0.05,0,0));
        }
        if(this.level().getBlockState(this.getOnPos().west()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().west().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(-1,0,0));
            this.move(MoverType.SELF,new Vec3(-0.05,0,0));
        }
        //Diagonals
        if(this.level().getBlockState(this.getOnPos().north().east()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().north().east().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(0,1,0));
            this.move(MoverType.SELF,new Vec3(0.05,0,-0.05));
        }
        if(this.level().getBlockState(this.getOnPos().south().east()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().south().east().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(0,-1,0));
            this.move(MoverType.SELF,new Vec3(0.05,0,0.05));
        }
        if(this.level().getBlockState(this.getOnPos().south().west()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().south().west().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(1,0,0));
            this.move(MoverType.SELF,new Vec3(-0.05,0,0.05));
        }
        if(this.level().getBlockState(this.getOnPos().north().west()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get() ||
                this.level().getBlockState(this.getOnPos().north().west().below()).getBlock() == ModBlocks.GYRO_ARENA_CORE.get()){
            //this.position().add(new Vec3(-1,0,0));
            this.move(MoverType.SELF,new Vec3(-0.05,0,-0.05));
        }

    }

    public float getRenderingRotation(){
        if(rotation >= 360){
            rotation = 0;
        } else {
            this.rotation = this.rotation + 5*this.getHealth();
            //this.rotation++;
        }
        return rotation;
    }

    private void setupAnimationStates(){
        //System.out.println("Setup Animation States Method called");
        if(this.idleAnimationTimeout <= 0){
            this.idleAnimationTimeout = 24;
            this.idleAnimationState.start(this.tickCount);
            //System.out.println("Idle Animation Start");
        } else {
            --this.idleAnimationTimeout;
        }

    }


    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        //System.out.println("updateWalkAnimation Method called");
        float f;
        if(this.getPose() == Pose.STANDING){
            f = Math.min(pPartialTick * 6F, 1F);;
        } else {
            f = 0f;
        }

        this.walkAnimation.update(f,0.2f);
        //System.out.println("Walk Animation Updated");
    }

    @Override
    protected void registerGoals(){
        //this.goalSelector.addGoal(0, new PanicGoal(this, 8));
        //this.goalSelector.addGoal(0,new AvoidBlock);
        this.goalSelector.addGoal(0, new FloatGoal(this));
        //this.goalSelector.addGoal(1,new WaterAvoidingRandomStrollGoal(this, 2));
        //this.goalSelector.addGoal(1, new TamableAnimal.TamableAnimalPanicGoal(1.5, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        //this.goalSelector.addGoal(3, new Wolf.WolfAvoidEntityGoal<>(this, Llama.class, 24.0F, 1.5, 1.5));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.5F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        //this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
        //this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1.0));
        //this.goalSelector.addGoal(8, new PanicGoal(this,2));
        //this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
        //this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        //this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        ////this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        ////this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        ////this.targetSelector.addGoal(3, new HurtByTargetGoal(this).setAlertOthers());
        ////this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        //this.targetSelector.addGoal(5, new NonTameRandomTargetGoal<>(this, Animal.class, false, PREY_SELECTOR));
        //this.targetSelector.addGoal(6, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
        this.targetSelector.addGoal(7, new NearestAttackableTargetGoal<>(this, BeigomaEntity.class, false));
        //this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return BeigomaEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.5)
                .add(Attributes.FLYING_SPEED, 0.5)
                .add(Attributes.ARMOR_TOUGHNESS, 0.1f)
                .add(Attributes.ATTACK_KNOCKBACK, 0.25)
                .add(Attributes.ATTACK_DAMAGE,1F)
                .add(Attributes.ENTITY_INTERACTION_RANGE,0.01)
                .add(Attributes.FOLLOW_RANGE,10)
                //.add(Attributes.JUMP_STRENGTH,1.1)
                ;
    }
    //@Nullable
    //@Override
    //public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
    //    return null;
    //}

    @Override
    protected void triggerOnDeathMobEffects(RemovalReason pRemovalReason) {
        super.triggerOnDeathMobEffects(pRemovalReason);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.PLAYER_ATTACK_SWEEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PLAYER_ATTACK_SWEEP;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.METAL_BREAK;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }
    
}
