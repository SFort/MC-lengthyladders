package tf.ssf.sfort;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class LadderBlockItem extends BlockItem {
    public LadderBlockItem(Block block, Settings settings) {
        super(block, settings);
    }
    @Override
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        BlockPos hit = context.getBlockPos().offset(context.getSide().getOpposite());
        BlockState state = context.getWorld().getBlockState(hit);
        if (state.isOf(this.getBlock()) && state.get(LadderBlock.FACING).equals(context.getSide())) {
            return ItemPlacementContext.offset(context, hit.up(), Direction.DOWN);
        }
        return super.getPlacementContext(context);
    }
}
