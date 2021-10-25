package Soldats;

import Weapons.Shield;
import Weapons.Sword;

public class Fantassin extends SoldatDecorator {
    private static final int FORCE_FANTASSIN = 1;

    public Fantassin(int vie) {
        super(vie, null, null);
    }

    public int force() {
        return FORCE_FANTASSIN;
    }

    @Override
    public void addSword(Sword sword) {
        // TODO Auto-generated method stub
        super.addSword(sword);
    }

    @Override
    public void addShield(Shield shield) {
        // TODO Auto-generated method stub
        super.addShield(shield);
    }

}