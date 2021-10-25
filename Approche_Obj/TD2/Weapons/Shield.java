package Weapons;

public class Shield extends Weapon{
    private int defense = 2;

    @Override
    public Boolean isSword() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Boolean isShield() {
        // TODO Auto-generated method stub
        return true;
    }

    public int getDefense(){
        return defense;
    }

}
