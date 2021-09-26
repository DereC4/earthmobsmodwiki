package net.minecraftearthmod.procedures;

import net.minecraftearthmod.MinecraftEarthModMod;

import net.minecraft.entity.Entity;

import java.util.Map;

public class SetEggTimerProcedure {
	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				MinecraftEarthModMod.LOGGER.warn("Failed to load dependency entity for procedure SetEggTimer!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.getPersistentData().putDouble("eggtimer", 0);
	}
}
