package sf.ssf.sfort.lengthyladders.mixin;

import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LadderBlock.class)
public abstract class LongLadder {
	//TODO this level of 'if' makes me kinda sick
 	@Inject(method = "isValidPosition", at = @At("HEAD"),cancellable = true)
	public void canPlaceAt(BlockState state, IWorldReader world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		BlockState down = world.getBlockState(pos.down());
		if (down.isSolidSide(world,pos.up(),Direction.UP)){
			info.setReturnValue(true);
			info.cancel();
		}
		Direction laddir = null;
		if (state.isSolidSide(world, pos, Direction.EAST))
			laddir=Direction.EAST;
		if (state.isSolidSide(world, pos, Direction.SOUTH))
			laddir=Direction.SOUTH;
		if (state.isSolidSide(world, pos, Direction.WEST))
			laddir=Direction.WEST;
		if (state.isSolidSide(world, pos, Direction.NORTH))
			laddir=Direction.NORTH;
		if (laddir !=null){
			if(world.getBlockState(pos.down()).isSolidSide(world, pos.down(), laddir)) {
				info.setReturnValue(true);
				info.cancel();
			}else if(world.getBlockState(pos.offset(laddir)).getBlock().matchesBlock(Blocks.LADDER)){
				info.setReturnValue(false);
				info.cancel();
			}
		}
	}
	@Inject(method = "updatePostPlacement", at = @At("HEAD"),cancellable = true)
	public void getStateForNeighborUpdate(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos, CallbackInfoReturnable<BlockState> info) {
		if (!state.isValidPosition(world, pos)){
			info.setReturnValue(Blocks.AIR.getDefaultState());
			info.cancel();
		}
	}
}
