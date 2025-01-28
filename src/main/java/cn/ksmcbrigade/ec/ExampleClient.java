package cn.ksmcbrigade.ec;

import cn.ksmcbrigade.ec.config.ModuleConfig;
import cn.ksmcbrigade.ec.module.Module;
import cn.ksmcbrigade.ec.modules.FullBright;
import cn.ksmcbrigade.ec.modules.NoFall;
import cn.ksmcbrigade.ec.modules.XRay;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ExampleClient.MODID)
public class ExampleClient {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "ec";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final ArrayList<Module> MODULES = new ArrayList<>();

    public ExampleClient(IEventBus modEventBus, ModContainer modContainer) throws IOException, NoSuchFieldException, IllegalAccessException {
        NeoForge.EVENT_BUS.register(this);

        MODULES.add(new FullBright());
        MODULES.add(new XRay());
        MODULES.add(new NoFall());

        ModuleConfig.init();

        for (String s : ModuleConfig.enables_config.keySet()) {
            for (Module module : MODULES) {
                if(module.name.equalsIgnoreCase(s)){
                    module.set(ModuleConfig.enables_config.getBoolean(s));
                }
            }
        }

        for (String s : ModuleConfig.keys_config.keySet()) {
            for (Module module : MODULES) {
                if(module.name.equalsIgnoreCase(s)){
                    module.setKey(ModuleConfig.keys_config.getInt(s));
                }
            }
        }
    }

    @SubscribeEvent
    public void tick(PlayerTickEvent.Pre event){
        MODULES.stream().filter(m -> m.enable).forEach(m -> m.onTick(Minecraft.getInstance(),Minecraft.getInstance().player));
    }

    @SubscribeEvent
    public void input(InputEvent.Key event){
        long window = Minecraft.getInstance().getWindow().getWindow();
        for (Module module : MODULES) {
            if(InputConstants.isKeyDown(window,module.key)){
                module.toggle();
            }
        }
    }
}
