package tf.ssf.sfort.lengthyladders.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class MixinItemsCompat {
	@Shadow public abstract Block getBlock();

	@Inject(method = "getPlacementContext(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/item/ItemPlacementContext;", at=@At("HEAD"), cancellable = true)
	private void register(ItemPlacementContext context, CallbackInfoReturnable<ItemPlacementContext> cir){
		if(this.getBlock() instanceof LadderBlock){
			BlockPos hit = context.getBlockPos().offset(context.getSide().getOpposite());
			BlockState state = context.getWorld().getBlockState(hit);
			if (state.isOf(this.getBlock()) && state.get(LadderBlock.FACING).equals(context.getSide())) {
				cir.setReturnValue(ItemPlacementContext.offset(context, hit.up(), Direction.DOWN));
			}
		}
	}
}
