package it.unibo.fnafretro.ai;

import java.util.Set;

/**
 * Implementazione di base di un personaggio.
 * @author  Marco Buda
 */
abstract class AiDescriptorBase implements AiDescriptor {

    private final int cooldown;
    private final Set<Integer> levelUpHours;
    private final String resourceName;

    AiDescriptorBase(
        final int cooldown,
        final Set<Integer> levelUpHours,
        final String resourceName
    ) {
        this.cooldown = cooldown;
        this.levelUpHours = levelUpHours;
        this.resourceName = resourceName;
    }

    @Override
    public int cooldown() {
        return this.cooldown;
    }

    /**
     * Stanza iniziale di default, può essere sovrascritta.
     */
    @Override
    public String startingRoom() {
        return "1A";
    }

    @Override
    public Set<Integer> levelUpHours() {
        return this.levelUpHours;
    }

    @Override
    public String resourceName() {
        return this.resourceName;
    }

}
