package com.accbdd.complicated_bees.genetics;

import com.accbdd.complicated_bees.genetics.gene.enums.EnumTolerance;

public class BeeHousingModifier {
    private final EnumTolerance temperatureMod;
    private final EnumTolerance humidityMod;
    private final float lifespanMod;
    private final float productivityMod;
    private final float territoryMod;
    private final float mutationMod;

    public BeeHousingModifier(EnumTolerance temperatureMod, EnumTolerance humidityMod, float lifespanMod, float productivityMod, float territoryMod, float mutationMod) {
        this.temperatureMod = temperatureMod;
        this.humidityMod = humidityMod;
        this.lifespanMod = lifespanMod;
        this.productivityMod = productivityMod;
        this.territoryMod = territoryMod;
        this.mutationMod = mutationMod;
    }

    public BeeHousingModifier() {
        this.temperatureMod = EnumTolerance.NONE;
        this.humidityMod = EnumTolerance.NONE;
        this.lifespanMod = 1;
        this.productivityMod = 1;
        this.territoryMod = 1;
        this.mutationMod = 1;
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

    public float getTerritoryMod() {
        return territoryMod;
    }

    public float getMutationMod() {
        return mutationMod;
    }

    public static class Builder {
        private EnumTolerance temperatureMod = EnumTolerance.NONE;
        private EnumTolerance humidityMod = EnumTolerance.NONE;
        private float lifespanMod = 1;
        private float productivityMod = 1;
        private float territoryMod = 1;
        private float mutationMod = 1;

        public BeeHousingModifier build() {
            return new BeeHousingModifier(temperatureMod, humidityMod, lifespanMod, productivityMod, territoryMod, mutationMod);
        }

        public Builder temperature(EnumTolerance mod) {
            this.temperatureMod = mod;
            return this;
        }

        public Builder humidity(EnumTolerance mod) {
            this.humidityMod = mod;
            return this;
        }

        public Builder lifespan(float mod) {
            this.lifespanMod = mod;
            return this;
        }

        public Builder productivity(float mod) {
            this.productivityMod = mod;
            return this;
        }

        public Builder territory(float mod) {
            this.territoryMod = mod;
            return this;
        }

        public Builder mutation(float mod) {
            this.mutationMod = mod;
            return this;
        }
    }
}
