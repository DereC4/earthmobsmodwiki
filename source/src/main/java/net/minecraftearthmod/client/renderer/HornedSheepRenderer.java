
package net.minecraftearthmod.client.renderer;

import net.minecraftearthmod.entity.HornedSheepEntity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.HumanoidModel;

public class HornedSheepRenderer extends HumanoidMobRenderer<HornedSheepEntity, HumanoidModel<HornedSheepEntity>> {
	public HornedSheepRenderer(EntityRendererProvider.Context context) {
		super(context, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER)), 0.5f);
		this.addLayer(new HumanoidArmorLayer(this, new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_INNER_ARMOR)),
				new HumanoidModel(context.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR))));
	}

	@Override
	public ResourceLocation getTextureLocation(HornedSheepEntity entity) {
		return new ResourceLocation("minecraft_earth_mod:textures/entities/hornedsheep.png");
	}
}
