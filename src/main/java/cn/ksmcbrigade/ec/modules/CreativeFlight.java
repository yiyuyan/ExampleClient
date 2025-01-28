package cn.ksmcbrigade.ec.modules;

import cn.ksmcbrigade.ec.module.Module;
import cn.ksmcbrigade.ec.module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.neoforged.bus.api.IEventBus;
import org.lwjgl.glfw.GLFW;

import java.util.Objects;

public class CreativeFlight extends Module {
    public CreativeFlight() {
        super(CreativeFlight.class.getSimpleName(), GLFW.GLFW_KEY_G,ModuleType.MOVEMENT);
    }

    @Override
    public void onEnabled(Minecraft MC, IEventBus eventBus) {
        if(MC.player!=null){
            MC.player.getAbilities().mayfly = true;
            MC.player.getAbilities().flying = true;
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundPlayerAbilitiesPacket(MC.player.getAbilities()));
        }
    }

    @Override
    public void onDisabled(Minecraft MC, IEventBus eventBus) {
        if(MC.player!=null && !MC.player.isCreative() && !MC.player.isSpectator()){
            MC.player.getAbilities().mayfly = false;
            MC.player.getAbilities().flying = false;
            Objects.requireNonNull(MC.getConnection()).getConnection().send(new ServerboundPlayerAbilitiesPacket(MC.player.getAbilities()));
        }
    }

    @Override
    public void onTick(Minecraft MC, LocalPlayer player) {
        player.getAbilities().mayfly = true;
    }
}
