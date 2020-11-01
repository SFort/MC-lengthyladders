package sf.ssf.sfort.mixin;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(LadderBlock.class)
public abstract class LongLadder {
	//TODO this level of 'if' makes me kinda sick
 	@Inject(method = "canPlaceAt", at = @At("HEAD"),cancellable = true)
	public void canPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable info) {
		BlockState down = world.getBlockState(pos.down());
		if (down.isSideSolidFullSquare(world,pos.up(),Direction.UP)){
			info.setReturnValue(true);
			info.cancel();
		}
		Direction laddir = null;
		if (state.isSideSolidFullSquare(world, pos, Direction.EAST))
			laddir=Direction.EAST;
		if (state.isSideSolidFullSquare(world, pos, Direction.SOUTH))
			laddir=Direction.SOUTH;
		if (state.isSideSolidFullSquare(world, pos, Direction.WEST))
			laddir=Direction.WEST;
		if (state.isSideSolidFullSquare(world, pos, Direction.NORTH))
			laddir=Direction.NORTH;
		if (laddir !=null){
			if(world.getBlockState(pos.down()).isSideSolidFullSquare(world, pos.down(), laddir)) {
				info.setReturnValue(true);
				info.cancel();
			}else if(world.getBlockState(pos.offset(laddir)).isOf(Blocks.LADDER)){
				info.setReturnValue(false);
				info.cancel();
			}
		}
	}
	@Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"),cancellable = true)
	public void getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, WorldAccess world, BlockPos pos, BlockPos posFrom,CallbackInfoReturnable<BlockState> info) {
		if (!state.canPlaceAt(world, pos)){
			info.setReturnValue(Blocks.AIR.getDefaultState());
			info.cancel();
		}
	}
}
