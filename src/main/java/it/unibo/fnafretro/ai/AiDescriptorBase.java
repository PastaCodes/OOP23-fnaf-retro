package it.unibo.fnafretro.ai;

import java.util.Set;

/**
 * Implementazione di base di un personaggio.
 * @author  Marco Buda
 */
abstract class AiDescriptorBase implements AiDescriptor {

    private final int cooldown;
    private final Set<Integer> levelUpHours;

    AiDescriptorBase(
        final int cooldown,
        final Set<Integer> levelUpHours
    ) {
        this.cooldown = cooldown;
        this.levelUpHours = levelUpHours;
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

}
