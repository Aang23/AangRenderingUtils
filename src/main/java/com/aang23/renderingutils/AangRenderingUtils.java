package com.aang23.renderingutils;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;

import com.aang23.renderingutils.client.ClientProxy;
import com.aang23.renderingutils.common.entity.EntityLightCylinder;
import com.aang23.renderingutils.common.entity.EntityLightOrb;

import org.apache.logging.log4j.Logger;

@Mod(modid = AangRenderingUtils.MODID, name = AangRenderingUtils.NAME, version = AangRenderingUtils.VERSION)
public class AangRenderingUtils {
    public static final String MODID = "aangrenderingutils";
    public static final String NAME = "Aang's Rendering Utils";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            ClientProxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(AangRenderingUtils.MODID, "LightOrb"),
                EntityLightOrb.class, "LightOrb", 1, this, 128, 3, false);
        EntityRegistry.registerModEntity(new ResourceLocation(AangRenderingUtils.MODID, "LightCylinder"),
                EntityLightCylinder.class, "LightCylinder", 2, this, 128, 3, false);
    }
}
