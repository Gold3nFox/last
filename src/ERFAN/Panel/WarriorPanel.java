package ERFAN.Panel;

import javax.swing.*;

public class WarriorPanel extends BasicPanel
{

    private int dmg;
    private JLabel dmgLable;

    public WarriorPanel(int width, int heigth, int xRoot, int yRoot, int hitpoints, int maxHP, String name, int dmg) {
        super(width, heigth, xRoot, yRoot, hitpoints, maxHP, name);
        this.dmg = dmg;

        dmgLable = new JLabel("Damage :"+dmg);
        dmgLable.setSize(width/5,heigth/6);
        dmgLable.setLocation(width/4,heigth/3);
        this.add(dmgLable);
    }

}
