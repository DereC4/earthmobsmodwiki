package net.minecraftearthmod.procedures;

import net.minecraftearthmod.network.MinecraftEarthModModVariables;

import net.minecraft.world.level.LevelAccessor;

public class GetSpawnMudFountainProcedure {
	public static boolean execute(LevelAccessor world) {
		return MinecraftEarthModModVariables.MapVariables.get(world).SpawnMudStructure;
	}
}
