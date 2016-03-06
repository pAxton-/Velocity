package org.pAxDev.Logic;

import org.newdawn.slick.opengl.Texture;
import org.pAxDev.Util.ImgLoader;

/**
 * Created by Lance on 3/6/2016.
 */
public class LoadTexture {
    ImgLoader imgLoader = new ImgLoader();
    Texture honeyPot;
    Texture starButton;
    Texture qtButton;
    Texture stButton;
    Texture playerTex;

    public LoadTexture() {
        honeyPot   = imgLoader.loadTexture("src/res/honey.png", "PNG");
        starButton = imgLoader.loadTexture("src/res/startBu.png", "PNG");
        qtButton   = imgLoader.loadTexture("src/res/quitBu.png", "PNG");
        stButton   = imgLoader.loadTexture("src/res/settingsBu.png", "PNG");
        playerTex  = imgLoader.loadTexture("src/res/badgerSpreadSheet.png", "PNG" );
    }

    public Texture getTexture(int a) {
        switch (a) {
            case 0:
                return honeyPot;

            case 1:
                return starButton;

            case 2:
                return qtButton;

            case 3:
                return stButton;

            case 4:
                return playerTex;

            default:
                return null;
        }

    }

}
