package net.minecraftearthmod.procedures;

import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;


public class MudFailsafeProcedure {
public static void execute(
LevelAccessor world,
double x,
double y,
double z,
Entity entity
) {
if(
entity == null
) return ;
if (entity instanceof Player _playerHasItem? _playerHasItem.getInventory().contains(new ItemStack(MinecraftEarthModModItems.PAIL.get())):false) {if (entity instanceof Player _player) {
ItemStack _stktoremove = new ItemStack(MinecraftEarthModModItems.PAIL.get());
_player.getInventory()
.clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
}if (entity instanceof Player _player) {
ItemStack _setstack = new ItemStack(Items.BUCKET);
_setstack.setCount(1);
ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
}}if (entity instanceof Player _playerHasItem? _playerHasItem.getInventory().contains(new ItemStack(MinecraftEarthModModItems.BUCKET_OF_MUD.get())):false) {if (entity instanceof Player _player) {
ItemStack _stktoremove = new ItemStack(MinecraftEarthModModItems.BUCKET_OF_MUD.get());
_player.getInventory()
.clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
}if (world instanceof ServerLevel _level)
_level.getServer().getCommands().performPrefixedCommand(
new CommandSourceStack(CommandSource.NULL, new Vec3(x, y, z), Vec2.ZERO,
_level, 4, "", Component.literal(""), _level.getServer(), null).withSuppressedOutput(), "give @p minecraft_earth_mod:mud_bucket");}
}
}
