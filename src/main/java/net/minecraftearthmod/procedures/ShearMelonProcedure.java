package net.minecraftearthmod.procedures;

import net.minecraftforge.eventbus.api.Event;

import javax.annotation.Nullable;


@Mod.EventBusSubscriber public class ShearMelonProcedure {
@SubscribeEvent public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
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
if ((world.getBlockState(new BlockPos(x,y,z))).getBlock() == Blocks.MELON) {if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem():ItemStack.EMPTY).getItem() == Items.SHEARS) {{
ItemStack _ist = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem():ItemStack.EMPTY);
if(_ist.hurt(1, RandomSource.create(), null)) {
_ist.shrink(1);
_ist.setDamageValue(0);
}
}if (world instanceof Level _level) {
if (!_level.isClientSide()) {
_level.playSound(null, new BlockPos(x,y,z),
ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")),
SoundSource.NEUTRAL, 1, 1);
} else {
_level.playLocalSound(x, y, z,
ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.sheep.shear")),
SoundSource.NEUTRAL, 1, 1, false);
}
}{
BlockPos _bp = new BlockPos(x,y,z);
BlockState _bs = MinecraftEarthModModBlocks.CARVED_MELON.get().defaultBlockState();
BlockState _bso = world.getBlockState(_bp);
for(Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
if (_property != null && _bs.getValue(_property) != null)
try {
_bs = _bs.setValue(_property, (Comparable) entry.getValue());
} catch (Exception e) {}
}
world.setBlock(_bp, _bs, 3);
}
{
Direction _dir = ((entity.getDirection()).getOpposite());
BlockPos _pos = new BlockPos(x,y,z);
BlockState _bs = world.getBlockState(_pos);
Property<?> _property = _bs.getBlock().getStateDefinition().getProperty("facing");
if (_property instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(_dir)) {
world.setBlock(_pos, _bs.setValue(_dp, _dir), 3);
} else {
_property = _bs.getBlock().getStateDefinition().getProperty("axis");
if (_property instanceof EnumProperty _ap && _ap.getPossibleValues().contains(_dir.getAxis()))
world.setBlock(_pos, _bs.setValue(_ap, _dir.getAxis()), 3);
}
}if (world instanceof Level _level && !_level.isClientSide()) {
ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Items.MELON_SEEDS));
entityToSpawn.setPickUpDelay(10);
_level.addFreshEntity(entityToSpawn);
}}}
}
}
