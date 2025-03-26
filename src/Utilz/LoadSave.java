package Utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoadSave {
    private static final Logger logger = Logger.getLogger(LoadSave.class.getName());

    public static final String PLAYER_ATLAS = "Spirit_Ani.png";
    public static final String LEVEL_ATLAS = "Tileset_Spiriture.png";

    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String HOME_BACKGROUND = "Background_main.png";
    public static final String BACKGROUND_MAIN = "background_game.png";
    public static final String MOVING_BACKGROUND = "Moving_Background.png";
    public static final String MOVING_BACKGROUND_2 = "small_tree.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";
    public static final String GAME_COMPLETED = "game_completed.png";

    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTIONS_MENU = "options_background.png";
    public static final String CREDITS = "credits_list.png";

    public static final String SLIME_SPRITE = "slime.png";
    public static final String MUSHROOM_SPRITE = "mushroom.png";
    public static final String BUSH_SPRITE = "bush.png";

    public static final String WATER_TOP = "water_atlas_animation.png";
    public static final String WATER_BOTTOM = "water.png";

    private static String msg = "Error reading imagee";

    public static BufferedImage GetSpriteAtlas(String fileName){
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(is);
        } catch (IOException e){
            logger.log(Level.SEVERE, msg, e);
        } finally {
            try{
                is.close();
            } catch (IOException e){
                logger.log(Level.SEVERE, msg, e);
            }
        }
        return img;
    }

    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            assert url != null;
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Error reading image level", e);
        }

        assert file != null;
        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (File value : files) {
                if (value.getName().equals((i + 1) + ".png"))
                    filesSorted[i] = value;
            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error load images", e);
            }

        return imgs;
    }

}
