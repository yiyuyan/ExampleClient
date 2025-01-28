package cn.ksmcbrigade.ec.modules;

import cn.ksmcbrigade.ec.module.Module;
import cn.ksmcbrigade.ec.module.ModuleType;
import cn.ksmcbrigade.el.events.block.BlockLightingEvent;
import cn.ksmcbrigade.el.events.render.RenderBlockEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;

public class XRay extends Module {
    public XRay() {
        super(XRay.class.getSimpleName(), GLFW.GLFW_KEY_X,ModuleType.RENDER);
    }

    @Override
    public void onEnabled(Minecraft MC, IEventBus eventBus) {
        eventBus.register(this);
        if(MC.levelRenderer!=null){
            MC.levelRenderer.allChanged();
        }
    }

    @Override
    public void onDisabled(Minecraft MC, IEventBus eventBus) {
        eventBus.unregister(this);
        if(MC.levelRenderer!=null){
            MC.levelRenderer.allChanged();
        }
    }

    @SubscribeEvent
    public void render(RenderBlockEvent event){
        event.render = BuiltInRegistries.BLOCK.getKey(event.block.getBlock()).toString().toLowerCase().contains("ore");
    }

    @SubscribeEvent
    public void light(BlockLightingEvent event){
        event.value = 15;
    }
}
