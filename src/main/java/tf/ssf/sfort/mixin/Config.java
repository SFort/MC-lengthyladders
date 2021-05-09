package tf.ssf.sfort.mixin;

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
    public static Logger LOGGER = LogManager.getLogger();

    public static boolean LadderScaff = true;

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
                    "^-Should ladders behave like scaffolding [true] true | false"
            );
            String[] ls = la.toArray(new String[Math.max(la.size(), defaultDesc.size() * 2)|1]);
            final int hash = Arrays.hashCode(ls);
            for (int i = 0; i<defaultDesc.size();++i)
                ls[i*2+1]= defaultDesc.get(i);

            try{LadderScaff = ls[0].contains("true");}catch (Exception ignore){}
            ls[0] = String.valueOf(LadderScaff);

            if (hash != Arrays.hashCode(ls))
                Files.write(confFile.toPath(), Arrays.asList(ls));
            LOGGER.log(Level.INFO,"tf.ssf.sfort.lengthyladders successfully loaded config file");
        } catch(Exception e) {
            LOGGER.log(Level.ERROR,"tf.ssf.sfort.lengthyladders failed to load config file, using defaults\n"+e);
        }
    }
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        switch (mixinClassName){
            case "tf.ssf.sfort.mixin.LongLadder":
                return true;
            case "tf.ssf.sfort.mixin.MixinItems":
                return LadderScaff;
            default:
                return false;
        }
    }
    @Override public String getRefMapperConfig() { return null; }
    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}