package tf.ssf.sfort.lengthyladders;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class LadderBlockItem extends BlockItem {
    public LadderBlockItem(Block blockIn, Properties builder) { super(blockIn, builder); }
    @Override
    public BlockItemUseContext getBlockItemUseContext(BlockItemUseContext context) {
        BlockPos hit = context.getPos().offset(context.getFace().getOpposite());
        BlockState state = context.getWorld().getBlockState(hit);
        if (state.getBlock().equals(this.getBlock()) && state.get(LadderBlock.FACING).equals(context.getFace())) {
            return BlockItemUseContext.func_221536_a(context, hit.up(), Direction.DOWN);
        }
        return super.getBlockItemUseContext(context);
    }
}
