package net.minecraftearthmod.procedures;

import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber public class MakeMudFormulaProcedure {
@SubscribeEvent public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
if (event.getHand() != event.getEntity().getUsedItemHand())
return;
execute(event,event.getLevel(),event.getPos().getX(),event.getPos().getY(),event.getPos().getZ(),event.getEntity());
}
public static void execute(
LevelAccessor world,
double x,
double y,
double z,
Entity entity
) {
execute(null,world,x,y,z,entity);
}
private static void execute(
@Nullable Event event,
LevelAccessor world,
double x,
double y,
double z,
Entity entity
) {
if(
entity == null
) return ;
if ((world.getBlockState(new BlockPos(x,y,z))).getBlock() == MinecraftEarthModModBlocks.MUD.get()||(world.getBlockState(new BlockPos(x,y-1,z))).getBlock() == MinecraftEarthModModBlocks.MUD.get()) {if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem():ItemStack.EMPTY).getItem() == Items.GLASS_BOTTLE) {if (world instanceof Level _level) {
if (!_level.isClientSide()) {
_level.playSound(null, new BlockPos(x,y,z),
ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")),
SoundSource.NEUTRAL, 1, 1);
} else {
_level.playLocalSound(x, y, z,
ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.bottle.fill")),
SoundSource.NEUTRAL, 1, 1, false);
}
}if (entity instanceof Player _player) {
ItemStack _stktoremove = new ItemStack(Items.GLASS_BOTTLE);
_player.getInventory()
.clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
}if (entity instanceof Player _player) {
ItemStack _setstack = new ItemStack(MinecraftEarthModModItems.MUD_FORMULA.get());
_setstack.setCount(1);
ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
}}}
}
}
