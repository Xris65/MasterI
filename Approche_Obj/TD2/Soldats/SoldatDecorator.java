package Soldats;

import java.util.ArrayList;

import Weapons.Shield;
import Weapons.Sword;
import Weapons.Weapon;

public class SoldatDecorator implements Soldat{
    ArrayList<Weapon> armes = new ArrayList<Weapon>();
    private int vie;


    public SoldatDecorator(int vie,Sword sword, Shield shield) {
        this.vie = vie;
        armes.add(sword);
        armes.add(shield);
    }

    public int getVie(){
        return vie;
    }

    public boolean parer(int force) {
        for(Weapon arme : armes){
            if(arme.isShield()){
                int defense = ((Shield) arme).getDefense();
                vie = (vie + defense > force) ? (vie + defense - force) : 0;
            }
        }
        vie = (vie > force) ? vie - force : 0;
        return vie > 0;
    }

    public int hit() {
        for(Weapon arme : armes){
            if(arme.isSword()){
                return ((Sword) arme).getForce() + force();
            }
        }
        return force();
    }

    @Override
    public void addSword(Sword sword) {
        // TODO Auto-generated method stub
        for(Weapon arme : armes){
            if(!arme.isSword() && armes.size() < 2)
            {
                armes.add(sword);
            }
        }
    }

    @Override
    public void addShield(Shield shield) {
        // TODO Auto-generated method stub
        for(Weapon arme : armes){
            if(!arme.isShield() && armes.size() < 2)
            {
                armes.add(shield);
            }
        }
    }

    @Override
    public int force() {
        // TODO Auto-generated method stub
        return this.force();
    }



}
