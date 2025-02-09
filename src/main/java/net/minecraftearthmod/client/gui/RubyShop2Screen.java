package net.minecraftearthmod.client.gui;

import net.minecraftearthmod.world.inventory.RubyShop2Menu;
import net.minecraftearthmod.network.RubyShop2ButtonMessage;
import net.minecraftearthmod.MinecraftEarthModMod;

import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.GuiGraphics;

import java.util.HashMap;

import com.mojang.blaze3d.systems.RenderSystem;

public class RubyShop2Screen extends AbstractContainerScreen<RubyShop2Menu> {
	private final static HashMap<String, Object> guistate = RubyShop2Menu.guistate;
	private final Level world;
	private final int x, y, z;
	private final Player entity;
	Button button_purchase;
	Button button_empty;
	Button button_empty1;
	Button button_purchase1;

	public RubyShop2Screen(RubyShop2Menu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(guiGraphics);
		super.render(guiGraphics, mouseX, mouseY, partialTicks);
		this.renderTooltip(guiGraphics, mouseX, mouseY);
	}

	@Override
	protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		guiGraphics.blit(new ResourceLocation("minecraft_earth_mod:textures/screens/critterpedia_gui_blank.png"), this.leftPos + -30, this.topPos + -2, 0, 0, 236, 157, 236, 157);

		RenderSystem.disableBlend();
	}

	@Override
	public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}
		return super.keyPressed(key, b, c);
	}

	@Override
	public void containerTick() {
		super.containerTick();
	}

	@Override
	protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
		guiGraphics.drawString(this.font, Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.label_buy_boost"), -12, 25, -6750208, false);
		guiGraphics.drawString(this.font, Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.label_buy_tappable"), -12, 61, -6750208, false);
	}

	@Override
	public void onClose() {
		super.onClose();
	}

	@Override
	public void init() {
		super.init();
		button_purchase = Button.builder(Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.button_purchase"), e -> {
			if (true) {
				MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShop2ButtonMessage(0, x, y, z));
				RubyShop2ButtonMessage.handleButtonAction(entity, 0, x, y, z);
			}
		}).bounds(this.leftPos + 60, this.topPos + 52, 63, 20).build();
		guistate.put("button:button_purchase", button_purchase);
		this.addRenderableWidget(button_purchase);
		button_empty = Button.builder(Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.button_empty"), e -> {
			if (true) {
				MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShop2ButtonMessage(1, x, y, z));
				RubyShop2ButtonMessage.handleButtonAction(entity, 1, x, y, z);
			}
		}).bounds(this.leftPos + 150, this.topPos + 106, 18, 20).build();
		guistate.put("button:button_empty", button_empty);
		this.addRenderableWidget(button_empty);
		button_empty1 = Button.builder(Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.button_empty1"), e -> {
			if (true) {
				MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShop2ButtonMessage(2, x, y, z));
				RubyShop2ButtonMessage.handleButtonAction(entity, 2, x, y, z);
			}
		}).bounds(this.leftPos + 177, this.topPos + 106, 18, 20).build();
		guistate.put("button:button_empty1", button_empty1);
		this.addRenderableWidget(button_empty1);
		button_purchase1 = Button.builder(Component.translatable("gui.minecraft_earth_mod.ruby_shop_2.button_purchase1"), e -> {
			if (true) {
				MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShop2ButtonMessage(3, x, y, z));
				RubyShop2ButtonMessage.handleButtonAction(entity, 3, x, y, z);
			}
		}).bounds(this.leftPos + 60, this.topPos + 16, 63, 20).build();
		guistate.put("button:button_purchase1", button_purchase1);
		this.addRenderableWidget(button_purchase1);
	}
}
