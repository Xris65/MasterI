package Soldats;

import Weapons.Shield;
import Weapons.Sword;

public interface Soldat {
    public int force();

    public boolean parer(int force);

    public void addSword(Sword sword);
    public void addShield(Shield shield);
    public int hit();

}