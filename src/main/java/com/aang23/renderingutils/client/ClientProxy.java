package com.aang23.renderingutils.client;

import com.aang23.renderingutils.client.render.RenderLightCylinder;
import com.aang23.renderingutils.client.render.RenderLightOrb;
import com.aang23.renderingutils.common.entity.EntityLightCylinder;
import com.aang23.renderingutils.common.entity.EntityLightOrb;

import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy {
    public static void preInit(){
        RenderingRegistry.registerEntityRenderingHandler(EntityLightOrb.class, RenderLightOrb::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLightCylinder.class, RenderLightCylinder::new);
    }
}