package net.horyzon.saturationdigitaldisplay;

import net.horyzon.SerialHandler;
import net.minecraft.client.MinecraftClient;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class Saturation {
    @Environment(EnvType.CLIENT)

    public static void saveSaturationData(MinecraftClient client) {
        PlayerEntity player = client.player;
        if (client == null || client.world == null || player == null) {
            return;
        }
        float saturationLevel = player.getHungerManager().getSaturationLevel();
        int foodlevel = player.getHungerManager().getFoodLevel();
      
        System.out.println(foodlevel + " " + " " + saturationLevel);
        SerialHandler.connect("COM3");
        SerialHandler.sendData(foodlevel + "," + saturationLevel + "\n");

    }
}