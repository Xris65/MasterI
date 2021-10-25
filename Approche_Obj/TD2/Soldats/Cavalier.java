package Soldats;

import Weapons.Shield;
import Weapons.Sword;

public class Cavalier extends SoldatDecorator {
    private static final int FORCE_CAVALIER = 2;

    public Cavalier(int vie) {
        super(vie, null, null);
    }

    public int force() {
        return FORCE_CAVALIER;
    }

    @Override
    public void addSword(Sword sword) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addShield(Shield shield) {
        // TODO Auto-generated method stub
        
    }

}