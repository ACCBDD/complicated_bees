package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;

public class BeeHousingModifier {
    private final EnumTolerance temperatureMod;
    private final EnumTolerance humidityMod;
    private final float lifespanMod;
    private final float productivityMod;

    public BeeHousingModifier(EnumTolerance temperatureMod, EnumTolerance humidityMod, float lifespanMod, float productivityMod) {
        this.temperatureMod = temperatureMod;
        this.humidityMod = humidityMod;
        this.lifespanMod = lifespanMod;
        this.productivityMod = productivityMod;
    }

    public BeeHousingModifier() {
        this.temperatureMod = EnumTolerance.NONE;
        this.humidityMod = EnumTolerance.NONE;
        this.lifespanMod = 1;
        this.productivityMod = 1;
    }

    public EnumTolerance getTemperatureMod() {
        return temperatureMod;
    }

    public EnumTolerance getHumidityMod() {
        return humidityMod;
    }

    public float getLifespanMod() {
        return lifespanMod;
    }

    public float getProductivityMod() {
        return productivityMod;
    }
}
