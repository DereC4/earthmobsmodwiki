
package net.minecraftearthmod.entity;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.material.Material;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;

import javax.annotation.Nullable;





public class DyedCatEntity extends TamableAnimal  {


	public DyedCatEntity(PlayMessages.SpawnEntity packet, Level world) {
    	this(MinecraftEarthModModEntities.DYED_CAT.get(), world);
    }

	public DyedCatEntity(EntityType<DyedCatEntity> type, Level world) {
    	super(type, world);
		maxUpStep = 0.6f;
		xpReward = 2;
		setNoAi(false);




	}

	@Override public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}


	@Override protected void registerGoals() {
		super.registerGoals();

            this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
this.goalSelector.addGoal(2,new PanicGoal(this, 1.2));this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));this.goalSelector.addGoal(5, new FloatGoal(this));

	}

	@Override public MobType getMobType() {
		return MobType.UNDEFINED;
	}





	@Override public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.ambient"));
	}


	@Override public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.hurt"));
	}

	@Override public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.cat.death"));
	}



	@Override public boolean hurt(DamageSource source, float amount) {
			if (source == DamageSource.FALL)
				return false;
		return super.hurt(source, amount);
	}




	@Override public InteractionResult mobInteract(Player sourceentity, InteractionHand hand) {
		ItemStack itemstack = sourceentity.getItemInHand(hand);
		InteractionResult retval = InteractionResult.sidedSuccess(this.level.isClientSide());


			Item item = itemstack.getItem();
			if (itemstack.getItem() instanceof SpawnEggItem) {
				retval = super.mobInteract(sourceentity, hand);
			} else if (this.level.isClientSide()) {
				retval = (this.isTame() && this.isOwnedBy(sourceentity) || this.isFood(itemstack))
						? InteractionResult.sidedSuccess(this.level.isClientSide()) : InteractionResult.PASS;
			} else {
				if (this.isTame()) {
					if (this.isOwnedBy(sourceentity)) {
						if (item.isEdible() && this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.usePlayerItem(sourceentity, hand, itemstack);
							this.heal((float)item.getFoodProperties().getNutrition());
							retval = InteractionResult.sidedSuccess(this.level.isClientSide());
						} else if (this.isFood(itemstack) && this.getHealth() < this.getMaxHealth()) {
							this.usePlayerItem(sourceentity, hand, itemstack);
							this.heal(4);
							retval = InteractionResult.sidedSuccess(this.level.isClientSide());
						} else {
							retval = super.mobInteract(sourceentity, hand);
						}
					}
				} else if (this.isFood(itemstack)) {
					this.usePlayerItem(sourceentity, hand, itemstack);
					if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, sourceentity)) {
						this.tame(sourceentity);
						this.level.broadcastEntityEvent(this, (byte) 7);
					} else {
						this.level.broadcastEntityEvent(this, (byte) 6);
					}

					this.setPersistenceRequired();
					retval = InteractionResult.sidedSuccess(this.level.isClientSide());
				} else {
					retval = super.mobInteract(sourceentity, hand);
					if (retval == InteractionResult.SUCCESS || retval == InteractionResult.CONSUME)
						this.setPersistenceRequired();
				}
			}


			return retval;
	}





        @Override public AgeableMob getBreedOffspring(ServerLevel serverWorld, AgeableMob ageable) {
			DyedCatEntity retval = MinecraftEarthModModEntities.DYED_CAT.get().create(serverWorld);
			retval.finalizeSpawn(serverWorld, serverWorld.getCurrentDifficultyAt(retval.blockPosition()), MobSpawnType.BREEDING, null, null);
			return retval;
		}

		@Override public boolean isFood(ItemStack stack) {
			return List.of(Items.COD,Items.SALMON,Items.TROPICAL_FISH).contains(stack.getItem());
		}








	public static void init() {
			SpawnPlacements.register(MinecraftEarthModModEntities.DYED_CAT.get(),
					SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
					(entityType, world, reason, pos, random) ->
							(world.getBlockState(pos.below()).getMaterial() == Material.GRASS && world.getRawBrightness(pos, 0) > 8)
			);

	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.3);
		builder = builder.add(Attributes.MAX_HEALTH, 10);
		builder = builder.add(Attributes.ARMOR, 0);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 3);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);






		return builder;
	}

}
