package net.minecraftearthmod.entity.renderer;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraftearthmod.entity.CluckshroomEntity;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.MobRenderer;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.matrix.MatrixStack;

@OnlyIn(Dist.CLIENT)
public class CluckshroomRenderer {
	public static class ModelRegisterHandler {
		@SubscribeEvent
		@OnlyIn(Dist.CLIENT)
		public void registerModels(ModelRegistryEvent event) {
			RenderingRegistry.registerEntityRenderingHandler(CluckshroomEntity.entity, renderManager -> {
				return new MobRenderer(renderManager, new Modelcluckshroom(), 0.3f) {
					@Override
					public ResourceLocation getEntityTexture(Entity entity) {
						return new ResourceLocation("minecraft_earth_mod:textures/cluckshroom_2.png");
					}
				};
			});
		}
	}

	// Made with Blockbench 3.7.4
	// Exported for Minecraft version 1.15
	// Paste this class into your mod and generate all required imports
	public static class Modelcluckshroom extends EntityModel<Entity> {
		private final ModelRenderer head;
		private final ModelRenderer body;
		private final ModelRenderer body_sub_2;
		private final ModelRenderer right_leg;
		private final ModelRenderer left_leg;
		private final ModelRenderer right_wing;
		private final ModelRenderer left_wing;
		private final ModelRenderer bill;
		private final ModelRenderer chin;
		public Modelcluckshroom() {
			textureWidth = 64;
			textureHeight = 32;
			head = new ModelRenderer(this);
			head.setRotationPoint(0.0F, 15.0F, -4.0F);
			head.setTextureOffset(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F, 0.0F, true);
			head.setTextureOffset(18, 9).addBox(-1.5F, -10.0F, -0.5F, 5.0F, 4.0F, 0.0F, 0.0F, true);
			head.setTextureOffset(18, 4).addBox(1.0F, -10.0F, -3.0F, 0.0F, 4.0F, 5.0F, 0.0F, true);
			body = new ModelRenderer(this);
			body.setRotationPoint(0.0F, 16.0F, 0.0F);
			body.setTextureOffset(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, 0.0F, true);
			body_sub_2 = new ModelRenderer(this);
			body_sub_2.setRotationPoint(0.0F, 0.0F, 7.0F);
			body.addChild(body_sub_2);
			body_sub_2.setTextureOffset(28, 3).addBox(-1.0F, -8.0F, -9.0F, 0.0F, 5.0F, 5.0F, 0.0F, true);
			body_sub_2.setTextureOffset(28, 8).addBox(-3.5F, -8.0F, -6.5F, 5.0F, 5.0F, 0.0F, 0.0F, true);
			right_leg = new ModelRenderer(this);
			right_leg.setRotationPoint(2.0F, 19.0F, 1.0F);
			right_leg.setTextureOffset(26, 0).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, true);
			left_leg = new ModelRenderer(this);
			left_leg.setRotationPoint(-1.0F, 19.0F, 1.0F);
			left_leg.setTextureOffset(26, 0).addBox(-2.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F, 0.0F, true);
			right_wing = new ModelRenderer(this);
			right_wing.setRotationPoint(-3.0F, 13.0F, 0.0F);
			right_wing.setTextureOffset(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, true);
			left_wing = new ModelRenderer(this);
			left_wing.setRotationPoint(3.0F, 13.0F, 0.0F);
			left_wing.setTextureOffset(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F, 0.0F, true);
			bill = new ModelRenderer(this);
			bill.setRotationPoint(0.0F, 15.0F, -4.0F);
			bill.setTextureOffset(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F, 0.0F, true);
			chin = new ModelRenderer(this);
			chin.setRotationPoint(0.0F, 15.0F, -4.0F);
			chin.setTextureOffset(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F, 0.0F, true);
		}

		@Override
		public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue,
				float alpha) {
			head.render(matrixStack, buffer, packedLight, packedOverlay);
			body.render(matrixStack, buffer, packedLight, packedOverlay);
			right_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			left_leg.render(matrixStack, buffer, packedLight, packedOverlay);
			right_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			left_wing.render(matrixStack, buffer, packedLight, packedOverlay);
			bill.render(matrixStack, buffer, packedLight, packedOverlay);
			chin.render(matrixStack, buffer, packedLight, packedOverlay);
		}

		public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
			modelRenderer.rotateAngleX = x;
			modelRenderer.rotateAngleY = y;
			modelRenderer.rotateAngleZ = z;
		}

		public void setRotationAngles(Entity e, float f, float f1, float f2, float f3, float f4) {
			this.head.rotateAngleY = f3 / (180F / (float) Math.PI);
			this.head.rotateAngleX = f4 / (180F / (float) Math.PI);
			this.left_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * -1.0F * f1;
			this.right_leg.rotateAngleX = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.right_wing.rotateAngleZ = MathHelper.cos(f * 1.0F) * 1.0F * f1;
			this.left_wing.rotateAngleZ = MathHelper.cos(f * 1.0F) * -1.0F * f1;
		}
	}
}
