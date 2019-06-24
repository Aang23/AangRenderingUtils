package com.aang23.renderingutils.common.entity;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Aang23
 */
@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class EntityLightOrb extends Entity implements ILightProvider {

    private String TEXTURE = "textures/blocks/bedrock.png";
    private int TYPE = EnumType.TEXTURE_SPHERE.ordinal();
    private double SIZE = 1D;
    private int RADIUS = 10;
    private float r = 1F, g = 1F, b = 1F, a = 1F;

    public EntityLightOrb(World world) {
        super(world);
        setSize(0.1F, 0.1F);
    }

    public String getTexture() {
        return TEXTURE;
    }

    public void setTexture(String texture) {
        TEXTURE = texture;
    }

    public EnumType getType() {
        return EnumType.values()[TYPE];
    }

    public void setType(EnumType type) {
        TYPE = type.ordinal();
    }

    /**
     * Sets the Orb's color. RGBA format
     */
    public void setColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    /**
     * Sets the light's radius
     * 
     * @param radius
     */
    public void setLightRadius(int radius) {
        RADIUS = radius;
    }

    /**
     * Sets the orb size
     * 
     * @param size
     */
    public void setOrbSize(double size) {
        SIZE = size;
    }

    public int getLightRadius() {
        return RADIUS;
    }

    public double getOrbSize() {
        return SIZE;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return true;
    }

    @Override
    @Optional.Method(modid = "albedo")
    public Light provideLight() {
        return Light.builder().pos(this).color(getColorR(), getColorG(), getColorB()).radius(getLightRadius()).build();
    }

    @Override
    @Optional.Method(modid = "albedo")
    public void gatherLights(GatherLightsEvent event, Entity entity) {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setTexture(nbt.getString("OrbTexture"));
        setType(EnumType.values()[nbt.getInteger("OrbType")]);
        setOrbSize(nbt.getDouble("OrbSize"));
        setLightRadius(nbt.getInteger("OrbRadius"));
        setColorR(nbt.getFloat("OrbColorR"));
        setColorG(nbt.getFloat("OrbColorG"));
        setColorB(nbt.getFloat("OrbColorB"));
        setColorA(nbt.getFloat("OrbColorA"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setString("OrbTexture", getTexture());
        nbt.setInteger("OrbType", getType().ordinal());
        nbt.setDouble("OrbSize", getOrbSize());
        nbt.setInteger("OrbRadius", getLightRadius());
        nbt.setFloat("OrbColorR", getColorR());
        nbt.setFloat("OrbColorG", getColorG());
        nbt.setFloat("OrbColorB", getColorB());
        nbt.setFloat("OrbColorA", getColorA());
    }

    public float getColorR() {
        return r;
    }

    public float getColorG() {
        return g;
    }

    public float getColorB() {
        return b;
    }

    public float getColorA() {
        return a;
    }

    public void setColorR(float value) {
        r = value;
    }

    public void setColorG(float value) {
        g = value;
    }

    public void setColorB(float value) {
        b = value;
    }

    public void setColorA(float value) {
        a = value;
    }

    public boolean shouldUseCustomTexture() {
        return getType() == EnumType.TEXTURE_CUBE || getType() == EnumType.TEXTURE_SPHERE;
    }

    public boolean isColorSphere() {
        return getType() == EnumType.COLOR_SPHERE;
    }

    public boolean isTextureSphere() {
        return getType() == EnumType.TEXTURE_SPHERE;
    }

    public boolean isSphere() {
        return isTextureSphere() || isColorSphere();
    }

    public enum EnumType {
        COLOR_SPHERE, COLOR_CUBE, TEXTURE_SPHERE, TEXTURE_CUBE
    }

    @Override
    protected void entityInit() {

    }
}