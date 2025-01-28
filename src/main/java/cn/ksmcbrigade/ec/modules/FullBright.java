package cn.ksmcbrigade.ec.modules;

import cn.ksmcbrigade.ec.module.Module;
import cn.ksmcbrigade.ec.module.ModuleType;
import cn.ksmcbrigade.el.events.misc.GetOptionValueEvent;
import net.minecraft.client.Minecraft;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class FullBright extends Module {
    public FullBright() {
        super(FullBright.class.getSimpleName(), GLFW.GLFW_KEY_C,ModuleType.RENDER);
    }

    @Override
    public void onEnabled(Minecraft MC, IEventBus eventBus) {
        eventBus.register(this);
    }

    @Override
    public void onDisabled(Minecraft MC, IEventBus eventBus) {
        eventBus.unregister(this);
    }

    @SubscribeEvent
    public void gamma(GetOptionValueEvent event){
        if(event.cap.equalsIgnoreCase(Minecraft.getInstance().options.gamma().caption.getString())){
            event.value = 15D;
        }
    }
}
