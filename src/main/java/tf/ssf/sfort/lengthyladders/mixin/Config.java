package tf.ssf.sfort.lengthyladders.mixin;

import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Config implements IMixinConfigPlugin {
    private static final String mod = "tf.ssf.sfort.lengthyladders";
    public static Logger LOGGER = LogManager.getLogger();

    public static Boolean LadderScaff = true;
    //public static final boolean fapiLoaded = FabricLoader.getInstance().getAllMods().stream().anyMatch(a->return a.getMetadata().getId().equals("fabric-api-base"));

    @Override
    public void onLoad(String mixinPackage) {
        // Configs
        File confFile = new File(
                FabricLoader.getInstance().getConfigDirectory().toString(),
                "LengthyLadders.conf"
        );
        try {
            confFile.createNewFile();
            List<String> la = Files.readAllLines(confFile.toPath());
            List<String> defaultDesc = Arrays.asList(
                    "^-Should ladders behave like scaffolding [true] true | false | iWantToBreakCompatibility"
            );
            String[] ls = la.toArray(new String[Math.max(la.size(), defaultDesc.size() * 2)|1]);
            final int hash = Arrays.hashCode(ls);
            for (int i = 0; i<defaultDesc.size();++i)
                ls[i*2+1]= defaultDesc.get(i);

            try{LadderScaff = ls[0].startsWith("false") ? null : ls[0].contains("true");}catch (Exception ignore){}
            ls[0] = LadderScaff == null ? "false" : LadderScaff ? "true" : "iWantToBreakCompatibility";

            if (hash != Arrays.hashCode(ls))
                Files.write(confFile.toPath(), Arrays.asList(ls));
            LOGGER.log(Level.INFO,mod+" successfully loaded config file");
        } catch(Exception e) {
            LOGGER.log(Level.ERROR,mod+" failed to load config file, using defaults\n"+e);
        }
    }
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        switch (mixinClassName){
            case mod+".mixin.MixinItems":
                return LadderScaff != null && !LadderScaff;
            case mod+".mixin.MixinItemsCompat":
                return LadderScaff != null && LadderScaff;
            default:
                return true;
        }
    }
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}