package Weapons;

public class Sword extends Weapon{
    private int force = 2;

    @Override
    public Boolean isSword() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Boolean isShield() {
        // TODO Auto-generated method stub
        return false;
    }
    public int getForce(){
        return force;
    }


}
