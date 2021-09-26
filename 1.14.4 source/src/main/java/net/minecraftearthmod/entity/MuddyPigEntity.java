
package net.minecraftearthmod.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.FMLPlayMessages;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftearthmod.procedures.CheckMuddyPigOnFireProcedure;
import net.minecraftearthmod.MinecraftEarthModModElements;

import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.DamageSource;
import net.minecraft.network.IPacket;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.item.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.client.renderer.model.ModelBox;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.block.material.Material;
import net.minecraft.block.Blocks;

import java.util.Map;
import java.util.HashMap;

@MinecraftEarthModModElements.ModElement.Tag
public class MuddyPigEntity extends MinecraftEarthModModElements.ModElement {
	public static EntityType entity = null;
	public MuddyPigEntity(MinecraftEarthModModElements instance) {
		super(instance, 5);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@Override
	public void initElements() {
		entity = (EntityType.Builder.<CustomEntity>create(CustomEntity::new, EntityClassification.CREATURE).setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(CustomEntity::new).size(0.9f, 0.9f)).build("muddy_pig")
						.setRegistryName("muddy_pig");
		elements.entities.add(() -> entity);
		elements.items
				.add(() -> new SpawnEggItem(entity, -31270, -9415618, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("muddy_pig"));
	}

	@Override
	public void init(FMLCommonSetupEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			biome.getSpawns(EntityClassification.CREATURE).add(new Biome.SpawnListEntry(entity, 1, 1, 3));
		}
		EntitySpawnPlacementRegistry.register(entity, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
				(entityType, world, reason, pos,
						random) -> (world.getBlockState(pos.down()).getMaterial() == Material.ORGANIC && world.getLightSubtracted(pos, 0) > 8));
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public void registerModels(ModelRegistryEvent event) {
		RenderingRegistry.registerEntityRenderingHandler(CustomEntity.class, renderManager -> {
			return new MobRenderer(renderManager, new Modelmuddypig(), 0.5f) {
				@Override
				protected ResourceLocation getEntityTexture(Entity entity) {
					return new ResourceLocation("minecraft_earth_mod:textures/muddypig.png");
				}
			};
		});
	}
	public static class CustomEntity extends AnimalEntity {
		public CustomEntity(FMLPlayMessages.SpawnEntity packet, World world) {
			this(entity, world);
		}

		public CustomEntity(EntityType<CustomEntity> type, World world) {
			super(type, world);
			experienceValue = 3;
			setNoAI(false);
		}

		@Override
		public IPacket<?> createSpawnPacket() {
			return NetworkHooks.getEntitySpawningPacket(this);
		}

		@Override
		protected void registerGoals() {
			super.registerGoals();
			this.goalSelector.addGoal(1, new TemptGoal(this, 1, Ingredient.fromItems(new ItemStack(Blocks.WHEAT, (int) (1)).getItem()), false));
			this.goalSelector.addGoal(2, new TemptGoal(this, 1, Ingredient.fromItems(new ItemStack(Items.CARROT, (int) (1)).getItem()), false));
			this.goalSelector.addGoal(3, new TemptGoal(this, 1, Ingredient.fromItems(new ItemStack(Items.POTATO, (int) (1)).getItem()), false));
			this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
			this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1));
			this.goalSelector.addGoal(6, new SwimGoal(this));
			this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
		}

		@Override
		public CreatureAttribute getCreatureAttribute() {
			return CreatureAttribute.UNDEFINED;
		}

		protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
			super.dropSpecialItems(source, looting, recentlyHitIn);
		}

		@Override
		public net.minecraft.util.SoundEvent getAmbientSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.ambient"));
		}

		@Override
		public net.minecraft.util.SoundEvent getHurtSound(DamageSource ds) {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.hurt"));
		}

		@Override
		public net.minecraft.util.SoundEvent getDeathSound() {
			return (net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.pig.death"));
		}

		@Override
		public void onDeath(DamageSource source) {
			super.onDeath(source);
			Entity sourceentity = source.getTrueSource();
			double x = this.posX;
			double y = this.posY;
			double z = this.posZ;
			Entity entity = this;
			{
				Map<String, Object> $_dependencies = new HashMap<>();
				$_dependencies.put("entity", entity);
				$_dependencies.put("x", x);
				$_dependencies.put("y", y);
				$_dependencies.put("z", z);
				$_dependencies.put("world", world);
				CheckMuddyPigOnFireProcedure.executeProcedure($_dependencies);
			}
		}

		@Override
		protected void registerAttributes() {
			super.registerAttributes();
			if (this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED) != null)
				this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
			if (this.getAttribute(SharedMonsterAttributes.MAX_HEALTH) != null)
				this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
			if (this.getAttribute(SharedMonsterAttributes.ARMOR) != null)
				this.getAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
			if (this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE) == null)
				this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
			this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0);
		}

		@Override
		public AgeableEntity createChild(AgeableEntity ageable) {
			return (CustomEntity) entity.create(this.world);
		}

		@Override
		public boolean isBreedingItem(ItemStack stack) {
			if (stack == null)
				return false;
			if (new ItemStack(Blocks.WHEAT, (int) (1)).getItem() == stack.getItem())
				return true;
			if (new ItemStack(Items.CARROT, (int) (1)).getItem() == stack.getItem())
				return true;
			if (new ItemStack(Items.POTATO, (int) (1)).getItem() == stack.getItem())
				return true;
			return false;
		}
	}

	// Made with Blockbench 3.6.5
	// Exported for Minecraft version 1.12
	// Paste this class into your mod and generate all required imports
	public static class Modelmuddypig extends EntityModel<Entity> {
		private final RendererModel body;
		private final RendererModel head;
		private final RendererModel leg0;
		private final RendererModel leg1;
		private final RendererModel leg2;
		private final RendererModel leg3;
		public Modelmuddypig() {
			textureWidth = 64;
			textureHeight = 32;
			body = new RendererModel(this);
			body.setRotationPoint(0.0F, 11.0F, 2.0F);
			setRotationAngle(body, 1.5708F, 0.0F, 0.0F);
			body.cubeList.add(new ModelBox(body, 28, 8, -5.0F, -10.0F, -7.0F, 10, 16, 8, 0.0F, false));
			head = new RendererModel(this);
			head.setRotationPoint(0.0F, 12.0F, -6.0F);
			head.cubeList.add(new ModelBox(head, 0, 0, -4.0F, -6.0F, -8.0F, 8, 8, 8, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 16, 16, -2.0F, -2.0F, -9.0F, 4, 3, 1, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 36, 0, -1.0F, -7.0F, -7.0F, 4, 1, 4, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 55, 5, 0.0F, -8.0F, -5.0F, 3, 1, 0, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 55, 4, 0.0F, -9.0F, -5.0F, 4, 1, 0, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 56, 3, 1.0F, -10.0F, -5.0F, 1, 1, 0, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 55, 1, 0.0F, -12.0F, -5.0F, 3, 2, 0, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 55, 0, 0.0F, -13.0F, -5.0F, 1, 1, 0, 0.0F, false));
			head.cubeList.add(new ModelBox(head, 57, 0, 2.0F, -13.0F, -5.0F, 1, 1, 0, 0.0F, true));
			leg0 = new RendererModel(this);
			leg0.setRotationPoint(-3.0F, 18.0F, 7.0F);
			leg0.cubeList.add(new ModelBox(leg0, 0, 16, -2.0F, 0.0F, -4.0F, 4, 6, 4, 0.0F, false));
			leg1 = new RendererModel(this);
			leg1.setRotationPoint(3.0F, 18.0F, 7.0F);
			leg1.cubeList.add(new ModelBox(leg1, 0, 16, -2.0F, 0.0F, -4.0F, 4, 6, 4, 0.0F, true));
			leg2 = new RendererModel(this);
			leg2.setRotationPoint(-3.0F, 18.0F, -5.0F);
			leg2.cubeList.add(new ModelBox(leg2, 0, 16, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, false));
			leg3 = new RendererModel(this);
			leg3.setRotationPoint(3.0F, 18.0F, -5.0F);
			leg3.cubeList.add(new ModelBox(leg3, 0, 16, -2.0F, 0.0F, -2.0F, 4, 6, 4, 0.0F, true));
		}

		@Override
		public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
			body.render(f5);
			head.render(f5);
			leg0.render(f5);
			leg1.render(f5);
			leg2.render(f5);
			leg3.render(f5);
		}

		public void setRotationAngle(RendererModel modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4, float f5) {
			super.setRotationAngles(e, f, f1, f2, f3, f4, f5);
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.leg0.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leg1.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.leg2.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.leg3.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
		}
	}
}
