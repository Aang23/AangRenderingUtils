package com.aang23.renderingutils.client.render;

import com.aang23.renderingutils.AangRenderingUtils;
import com.aang23.renderingutils.common.entity.EntityLightOrb;

import codechicken.lib.math.MathHelper;
import codechicken.lib.render.CCModel;
import codechicken.lib.render.CCRenderState;
import codechicken.lib.render.OBJParser;
import codechicken.lib.texture.TextureUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

/**
 * @author Aang23
 */
public class RenderLightOrb extends Render<EntityLightOrb> {

    private static ResourceLocation fill_default = new ResourceLocation(AangRenderingUtils.MODID, "textures/entity/sphere.png");
    private static ResourceLocation halo = new ResourceLocation(AangRenderingUtils.MODID, "textures/entity/spherehalo.png");
    private ResourceLocation fill;

    private CCModel model;

    private static CCModel cubeModel = OBJParser.parseModels(new ResourceLocation(AangRenderingUtils.MODID, "models/cube.obj")).get("model"); 
    private static CCModel sphereModel = OBJParser.parseModels(new ResourceLocation(AangRenderingUtils.MODID, "models/hemisphere.obj")).get("model");

    public RenderLightOrb(RenderManager manager) {
        super(manager);
    }

    @Override
    public void doRender(EntityLightOrb entity, double x, double y, double z, float entityYaw, float partialTicks) {
        
        model = entity.isSphere() ? sphereModel : cubeModel;
        fill = entity.shouldUseCustomTexture() ? new ResourceLocation(entity.getTexture()) : fill_default;

        Minecraft minecraft = Minecraft.getMinecraft();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        CCRenderState ccrenderstate = CCRenderState.instance();
        TextureUtils.changeTexture(halo);

        if (entity.shouldUseCustomTexture()) {
            GlStateManager.color(1F, 1F, 1F, entity.getColorA());
        } else {
            GlStateManager.color(entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getColorA());
        }

        double scale = entity.getOrbSize();

        double haloCoord = 0.58 * scale;

        double dx = entity.posX - renderManager.viewerPosX;
        double dy = entity.posY - renderManager.viewerPosY;
        double dz = entity.posZ - renderManager.viewerPosZ;

        double lenghtXZ = Math.sqrt(dx * dx + dz * dz);
        double lenght = Math.sqrt(dx * dx + dy * dy + dz * dz);

        double angleY = entity.isColorSphere() ? Math.atan2(lenghtXZ, dy) * MathHelper.todeg : entity.rotationYaw;
        double angleX = entity.isColorSphere() ? Math.atan2(dx, dz) * MathHelper.todeg : entity.rotationPitch;

        if (lenght > 16 || !entity.isColorSphere()) haloCoord = 0;

        GlStateManager.disableLighting();
        minecraft.entityRenderer.disableLightmap();

        GlStateManager.pushMatrix();
        {
            GlStateManager.translate(x, y, z);

            GlStateManager.rotate((float) angleX, 0, 1, 0);
            GlStateManager.rotate((float) (angleY + 90), 1, 0, 0);

            GlStateManager.pushMatrix();
            {
                GlStateManager.rotate(90, 1, 0, 0);

                GlStateManager.disableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.depthMask(false);

                buffer.begin(0x07, DefaultVertexFormats.POSITION_TEX);
                buffer.pos(-haloCoord, 0.0, -haloCoord).tex(0.0, 0.0).endVertex();
                buffer.pos(-haloCoord, 0.0, haloCoord).tex(0.0, 1.0).endVertex();
                buffer.pos(haloCoord, 0.0, haloCoord).tex(1.0, 1.0).endVertex();
                buffer.pos(haloCoord, 0.0, -haloCoord).tex(1.0, 0.0).endVertex();
                tessellator.draw();

                GlStateManager.depthMask(true);
                GlStateManager.disableBlend();
                GlStateManager.enableAlpha();
            }
            GlStateManager.popMatrix();

            TextureUtils.changeTexture(fill);

            GlStateManager.scale(scale, scale, scale);

            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            if (!entity.shouldUseCustomTexture()) GlStateManager.color(entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getColorA()); 
            else GlStateManager.color(1F, 1F, 1F, entity.getColorA());
            ccrenderstate.startDrawing(entity.isSphere() ? 0x07 : 0x05, DefaultVertexFormats.POSITION_TEX_NORMAL);
            model.render(ccrenderstate);
            ccrenderstate.draw();

            if (entity.isTextureSphere()) {
                GlStateManager.rotate(180, 1, 1, 0);
                if (!entity.shouldUseCustomTexture()) GlStateManager.color(entity.getColorR(), entity.getColorG(), entity.getColorB(), entity.getColorA());
                else GlStateManager.color(1F, 1F, 1F, entity.getColorA());
                ccrenderstate.startDrawing(entity.isSphere() ? 0x07 : 0x05, DefaultVertexFormats.POSITION_TEX_NORMAL);
                model.render(ccrenderstate);
                ccrenderstate.draw();
            }

            GlStateManager.enableCull();
            GlStateManager.disableBlend();

        }
        GlStateManager.popMatrix();

        minecraft.entityRenderer.enableLightmap();
        GlStateManager.enableLighting();

        GlStateManager.color(1, 1, 1, 1);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityLightOrb entity) {
        return fill;
    }
}