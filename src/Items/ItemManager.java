package Items;


import Levels.Level;
import Utilz.LoadSave;
import gameState.Playing;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static Utilz.Constants.ItemConstants.*;

public class ItemManager {
    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs;
    private ArrayList<Potion>potions;
    private ArrayList<ItemContainer>containers;

    public ItemManager(Playing playing){
        this.playing = playing;
        loadImgs();
    }

    public void checkObjekTouched(Rectangle2D.Float hitBox){
        for (Potion p : potions)
            if (p.isActive()) {
                if (hitBox.intersects(p.getHitBox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
    }

    public void applyEffectToPlayer(Potion p){
        if (p.getObjType() == RED_POTION)
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        else
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
    }

    public void checkItemHit(Rectangle2D.Float attackbox){
        for (ItemContainer ic : containers)
            if (ic.isActive() && !ic.doAnimation){
                if (ic.getHitBox().intersects(attackbox)){
                    ic.setAnimation(true);
                    int type = 0;
                    if (ic.getObjType() == BARREL)
                        type = 1;
                    potions.add(new Potion((int) (ic.getHitBox().x + ic.getHitBox().width / 2), (int) (ic.getHitBox().y - ic.getHitBox().height / 2), type));
                    return;
                }
            }

    }

    public void loadObject(Level newLevel) {
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++)
            for (int i = 0; i < potionImgs[j].length; i++)
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++)
            for (int i = 0; i < containerImgs[j].length; i++)
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
    }

    public  void update(){
        for(Potion p : potions)
            if(p.isActive())
                p.update();
        
        for (ItemContainer ic : containers)
            if (ic.isActive())
                ic.update();
    }

    public void draw(Graphics g, int xLvlOffset){
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
    }


    private void drawContainers(Graphics g, int xLvlOffset) {
        for (ItemContainer ic : containers)
            if (ic.isActive()){
                int type = 0;
                if (ic.getObjType() == BARREL)
                    type = 1;
                g.drawImage(containerImgs[type][ic.getAniIndex()],
                        (int) (ic.getHitBox().x - ic.getxDrawOffset() - xLvlOffset),
                        (int) (ic.getHitBox().y - ic.getyDrawOffset()),
                        CONTAINER_WIDTH,
                        CONTAINER_HEIGHT,
                        null);
            }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions)
            if (p.isActive()) {
                int type = 0;
                if (p.getObjType() == RED_POTION)
                    type = 1;
                g.drawImage(potionImgs[type][p.getAniIndex()],
                        (int) (p.getHitBox().x - p.getxDrawOffset() - xLvlOffset),
                        (int) (p.getHitBox().y - p.getyDrawOffset()),
                        POTION_WIDTH, POTION_HEIGHT,
                        null);
            }
    }

    public void resetAllItems() {
        loadObject(playing.getLevelManager().getCurrentLevel());
        for (Potion p : potions)
            p.reset();
        for (ItemContainer ic : containers)
            ic.reset();
    }
}
