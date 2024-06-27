package Items;

import Main.Game;

import static Utilz.Constants.ItemConstants.*;

public class ItemContainer extends Item{
    public ItemContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitBox();
    }

    private void createHitBox() {
        if (objType == BOX){
           initHitBox(25, 18);

           xDrawOffset = (int) (7 * Game.SCALE);
           yDrawOffset = (int) (12 * Game.SCALE);
        }else {
            initHitBox(23, 25);
            xDrawOffset = (int) (8 * Game.SCALE);
            yDrawOffset = (int) (5 * Game.SCALE);
        }
        hitBox.y += yDrawOffset + (int) (Game.SCALE * 2);
        hitBox.x += xDrawOffset / 2;
    }

    public void update(){
        if(doAnimation)
            updateAnimationTick();
    }
}
