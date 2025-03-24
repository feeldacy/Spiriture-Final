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

        if (url == null) {
            logger.log(Level.SEVERE, "Resource folder '/lvls' not found.");
            return new BufferedImage[0];
        }

        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            logger.log(Level.SEVERE, "Invalid URI syntax", e);
            return new BufferedImage[0];
        }


        if (file == null || !file.exists() || !file.isDirectory()) {
            logger.log(Level.SEVERE, "Invalid directory: " + file);
            return new BufferedImage[0];
        }

        File[] files = file.listFiles();
        if (files == null || files.length == 0) {
            logger.log(Level.SEVERE, "No level files found in '/lvls'");
            return new BufferedImage[0];
        }

        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals("" + (i + 1) + ".png"))
                    filesSorted[i] = files[j];
            }

        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++) {
            try {
                if (filesSorted[i] != null) {
                    imgs[i] = ImageIO.read(filesSorted[i]);
                } else {
                    logger.log(Level.SEVERE, "Missing level file: " + (i + 1) + ".png");
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error loading image: " + filesSorted[i], e);
            }
        }

        return imgs;
    }

}
