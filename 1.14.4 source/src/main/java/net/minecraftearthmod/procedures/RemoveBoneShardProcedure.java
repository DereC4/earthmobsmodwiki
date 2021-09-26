package net.minecraftearthmod.procedures;

import net.minecraftearthmod.item.BoneshardItem;
import net.minecraftearthmod.MinecraftEarthModModElements;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import java.util.Map;

@MinecraftEarthModModElements.ModElement.Tag
public class RemoveBoneShardProcedure extends MinecraftEarthModModElements.ModElement {
	public RemoveBoneShardProcedure(MinecraftEarthModModElements instance) {
		super(instance, 54);
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			System.err.println("Failed to load dependency entity for procedure RemoveBoneShard!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			System.err.println("Failed to load dependency itemstack for procedure RemoveBoneShard!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).getCooldownTracker().setCooldown(((itemstack)).getItem(), (int) 20);
		if (entity instanceof PlayerEntity)
			((PlayerEntity) entity).inventory.clearMatchingItems(p -> new ItemStack(BoneshardItem.block, (int) (1)).getItem() == p.getItem(),
					(int) 1);
	}
}
