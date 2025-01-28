package cn.ksmcbrigade.ec.module;

import cn.ksmcbrigade.ec.ExampleClient;
import cn.ksmcbrigade.ec.config.ModuleConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;

public class Module {

    public final String name;
    public final ModuleType type;
    public int key;
    public boolean enable = false;

    public Module(String name,int defaultKey,ModuleType type){
        this.name = name;
        this.key = defaultKey;
        this.type = type;
    }

    public Module(String name,ModuleType type){
        this(name,-1,type);
    }

    public void onTick(Minecraft MC, LocalPlayer player){}

    public void onEnabled(Minecraft MC, IEventBus eventBus){}

    public void onDisabled(Minecraft MC,IEventBus eventBus){}

    public void set(boolean enable){
        this.enable = enable;
        Minecraft MC = Minecraft.getInstance();
        if(enable){
            this.onEnabled(MC, NeoForge.EVENT_BUS);
        }
        else{
            this.onDisabled(MC,NeoForge.EVENT_BUS);
        }
        try {
            ModuleConfig.init();
            ModuleConfig.enables_config.put(this.name,this.enable);
        } catch (Exception e) {
            ExampleClient.LOGGER.error("Failed to save the module config: {}",this.name,e);
        }
    }

    public void toggle(){
        this.set(!this.enable);
    }

    public void setKey(int key){
        this.key = key;
        try {
            ModuleConfig.init();
            ModuleConfig.keys_config.put(this.name,this.key);
        } catch (Exception e) {
            ExampleClient.LOGGER.error("Failed to save the module config: {}",this.name,e);
        }

    }
}
