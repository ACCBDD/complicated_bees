package com.accbdd.complicated_bees.genetics;

public class Genome {
    private Chromosome primary, secondary;

    public Genome(Chromosome primary, Chromosome secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public Genome(Chromosome chromosome) {
        this(chromosome, chromosome);
    }

    public Chromosome getPrimary() {
        return primary;
    }

    public void setPrimary(Chromosome primary) {
        this.primary = primary;
    }

    public Chromosome getSecondary() {
        return secondary;
    }

    public void setSecondary(Chromosome secondary) {
        this.secondary = secondary;
    }
}
