package sf.ssf.sfort.lengthyladders;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(LengthyLadders.MODID)
public class LengthyLadders {
    public static final String MODID = "lengthyladders";


    public LengthyLadders() {
        MinecraftForge.EVENT_BUS.register(this);
    }

}
