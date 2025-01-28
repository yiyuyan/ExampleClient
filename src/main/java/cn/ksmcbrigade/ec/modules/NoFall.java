package cn.ksmcbrigade.ec.modules;

import cn.ksmcbrigade.ec.module.Module;
import cn.ksmcbrigade.ec.module.ModuleType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

public class NoFall extends Module {
    public NoFall() {
        super(NoFall.class.getSimpleName(), GLFW.GLFW_KEY_N,ModuleType.MOVEMENT);
    }

    @Override
    public void onTick(Minecraft MC, LocalPlayer player) {
        if(player.fallDistance>=3 && !player.mayFly() && !player.isFallFlying() && player.getDeltaMovement().y>=-0.5 && !player.isCreative() && !player.isSpectator() && !player.getItemInHand(player.getUsedItemHand()).is(Items.MACE)){
            MC.getConnection().getConnection().send(new ServerboundMovePlayerPacket.StatusOnly(true));
        }
    }
}
