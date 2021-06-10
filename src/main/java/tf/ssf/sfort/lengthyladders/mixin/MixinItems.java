package tf.ssf.sfort.lengthyladders.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tf.ssf.sfort.lengthyladders.LadderBlockItem;

@Mixin(Items.class)
public abstract class MixinItems {
	@Inject(method = "register(Lnet/minecraft/block/Block;Lnet/minecraft/item/ItemGroup;)Lnet/minecraft/item/Item;", at=@At("HEAD"), cancellable = true)
	private static void register(Block block, ItemGroup group, CallbackInfoReturnable<Item> cir){
		if(block.equals(Blocks.LADDER))
			cir.setReturnValue(register(new LadderBlockItem(block, (new Item.Settings()).group(group))));
	}
	@Shadow
	private static Item register(BlockItem blockItem) { return null; }
}
