package com.aang23.renderingutils.common.entity;

import com.aang23.renderingutils.common.utils.Vector;

import elucent.albedo.event.GatherLightsEvent;
import elucent.albedo.lighting.ILightProvider;
import elucent.albedo.lighting.Light;
import elucent.albedo.lighting.LightManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author Aang23
 */
@Optional.Interface(iface = "elucent.albedo.lighting.ILightProvider", modid = "albedo")
public class EntityLightCylinder extends Entity implements ILightProvider {

    private String TEXTURE = "textures/blocks/bedrock.png";
    private Double SIZE = 1D;
    private int LIGHT_AMOUNT = 10;
    private int LIGHT_RADIUS = 10;
    private int LENGTH = 10;
    private float r = 1F, g = 1F, b = 1F, a = 1F;
    private Float YAW = 0F, PITCH = 0F;

    public EntityLightCylinder(World world) {
        super(world);
        setSize(0.1F, 0.1F);
    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
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
        for (int i = 0; i < getCylinderLenght(); i++) {
            if (i % (getCylinderLenght() / getLightAmount()) == 0) {
                Vector end = new Vector(posX, posY, posZ).plus(new Vector(this.getLookVec()).times(i * (0.3 * 2)));
                LightManager.lights.add(Light.builder().pos(end.toBlockPos())
                        .color(getColorR(), getColorG(), getColorB()).radius(getLightRadius()).build());
            }
        }
        return Light.builder().pos(this).color(getColorR(), getColorG(), getColorB()).radius(10).build();
    }

    @Override
    @Optional.Method(modid = "albedo")
    public void gatherLights(GatherLightsEvent event, Entity entity) {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt) {
        setTexture(nbt.getString("CylTexture"));
        setLightAmount(nbt.getInteger("CylAmount"));
        setCylinderSize(nbt.getDouble("CylSize"));
        setLightRadius(nbt.getInteger("CylRadius"));
        setCylinderLenght(nbt.getInteger("CylLength"));
        setColorR(nbt.getFloat("CylColorR"));
        setColorG(nbt.getFloat("CylColorG"));
        setColorB(nbt.getFloat("CylColorB"));
        setColorA(nbt.getFloat("CylColorA"));
        setCylinderYaw(nbt.getFloat("CylYaw"));
        setCylinderPitch(nbt.getFloat("CylPitch"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setString("CylTexture", getTexture());
        nbt.setInteger("CylAmount", getLightAmount());
        nbt.setDouble("CylSize", getCylinderSize());
        nbt.setInteger("CylRadius", getLightRadius());
        nbt.setInteger("CylLength", getCylinderLenght());
        nbt.setFloat("CylColorR", getColorR());
        nbt.setFloat("CylColorG", getColorG());
        nbt.setFloat("CylColorB", getColorB());
        nbt.setFloat("CylColorA", getColorA());
        nbt.setFloat("CylYaw", getCylinderYaw());
        nbt.setFloat("CylPitch", getCylinderPitch());
    }

    public void setCylinderLenght(int length) {
        LENGTH = length;
    }

    public int getCylinderLenght() {
        return LENGTH;
    }

    public void setCylinderSize(double size) {
        SIZE = size;
    }

    public double getCylinderSize() {
        return SIZE;
    }

    public void setCylinderYaw(float value) {
        YAW = value;
    }

    public float getCylinderYaw() {
        return YAW;
    }

    public void setCylinderPitch(float value) {
        PITCH = value;
    }

    public float getCylinderPitch() {
        return PITCH;
    }

    public void setLightRadius(int radius) {
        LIGHT_RADIUS = radius;
    }

    public int getLightRadius() {
        return LIGHT_RADIUS;
    }

    public void setLightAmount(int amount) {
        LIGHT_AMOUNT = amount;
    }

    public int getLightAmount() {
        return LIGHT_AMOUNT;
    }

    public String getTexture() {
        return TEXTURE;
    }

    public void setTexture(String texture) {
        TEXTURE = texture;
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

}