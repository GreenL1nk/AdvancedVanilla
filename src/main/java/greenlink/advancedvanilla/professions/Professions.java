package greenlink.advancedvanilla.professions;

import greenlink.advancedvanilla.professions.miner.Miner;

public enum Professions {

    MINER(new Miner("Шахтёр"));

    private final ProfessionBase professionBase;

    Professions(ProfessionBase professionBase) {
        this.professionBase = professionBase;
    }

    public ProfessionBase getProfessionBase() {
        return professionBase;
    }
}
