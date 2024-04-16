package net.minecraftearthmod.client.gui;


public class RubyShopScreen extends AbstractContainerScreen<RubyShopMenu> {

	private final static HashMap<String, Object> guistate = RubyShopMenu.guistate;

	private final Level world;
	private final int x, y, z;
	private final Player entity;



		Button button_purchase;
		Button button_empty;


	public RubyShopScreen(RubyShopMenu container, Inventory inventory, Component text) {
		super(container, inventory, text);
		this.world = container.world;
		this.x = container.x;
		this.y = container.y;
		this.z = container.z;
		this.entity = container.entity;
		this.imageWidth = 176;
		this.imageHeight = 166;
	}



	@Override public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(ms);
		super.render(ms, mouseX, mouseY, partialTicks);
		this.renderTooltip(ms, mouseX, mouseY);


	}

	@Override protected void renderBg(PoseStack ms, float partialTicks, int gx, int gy) {
		RenderSystem.setShaderColor(1, 1, 1, 1);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();


			
				RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft_earth_mod:textures/screens/critterpedia_gui_blank.png"));
				this.blit(ms, this.leftPos + -29, this.topPos + -2, 0, 0,
					236, 157,
					236, 157);
			
			
				RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft_earth_mod:textures/screens/rubyblock.png"));
				this.blit(ms, this.leftPos + 159, this.topPos + 70, 0, 0,
					256, 256,
					256, 256);
			
			
				RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft_earth_mod:textures/screens/ruby.png"));
				this.blit(ms, this.leftPos + 132, this.topPos + -2, 0, 0,
					256, 256,
					256, 256);
			
			
				RenderSystem.setShaderTexture(0, new ResourceLocation("minecraft_earth_mod:textures/screens/ruby.png"));
				this.blit(ms, this.leftPos + -29, this.topPos + -2, 0, 0,
					256, 256,
					256, 256);
			

		RenderSystem.disableBlend();
	}

	@Override public boolean keyPressed(int key, int b, int c) {
		if (key == 256) {
			this.minecraft.player.closeContainer();
			return true;
		}


		return super.keyPressed(key, b, c);
	}

	@Override public void containerTick() {
		super.containerTick();
	}

	@Override protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
			this.font.draw(poseStack,
				Component.translatable("gui.minecraft_earth_mod.ruby_shop.label_welcome_to_the_ruby_shop"),
				-11, 16, -3407872);
			this.font.draw(poseStack,
				Component.translatable("gui.minecraft_earth_mod.ruby_shop.label_random_plant"),
				-11, 43, -6750208);
	}

	@Override public void onClose() {
		super.onClose();
		Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
	}

	@Override public void init() {
		super.init();

		this.minecraft.keyboardHandler.setSendRepeatsToGui(true);



			button_purchase = new Button(
				this.leftPos + 60, this.topPos + 34,
				63, 20,
				Component.translatable("gui.minecraft_earth_mod.ruby_shop.button_purchase"),
e -> {
	    if (        true
) {
			MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShopButtonMessage(0, x, y, z));
			RubyShopButtonMessage.handleButtonAction(entity, 0, x, y, z);
		}
}
			);

			guistate.put("button:button_purchase", button_purchase);
			this.addRenderableWidget(button_purchase);

			button_empty = new Button(
				this.leftPos + 177, this.topPos + 106,
				18, 20,
				Component.translatable("gui.minecraft_earth_mod.ruby_shop.button_empty"),
e -> {
	    if (        true
) {
			MinecraftEarthModMod.PACKET_HANDLER.sendToServer(new RubyShopButtonMessage(1, x, y, z));
			RubyShopButtonMessage.handleButtonAction(entity, 1, x, y, z);
		}
}
			);

			guistate.put("button:button_empty", button_empty);
			this.addRenderableWidget(button_empty);



	}

}

