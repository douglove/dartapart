package net.magicstudios.jdart.data.games;

import net.magicstudios.jdart.data.service.*;

import net.magicstudios.jdart.data.*;

public class GameModelFactory {

  public GameModelFactory() {
  }

  public static GameModel getGameModel(DAGame game) {
    GameModel model = null;
    switch (game.getType()) {
      case DAGame.GAME_301:
        model = new OhOneGameModel(301, false);
        break;
      case DAGame.GAME_201:
        model = new OhOneGameModel(201, false);
        break;
      case DAGame.GAME_101:
        model = new OhOneGameModel(101, false);
        break;
      case DAGame.GAME_CRICKET_GIVE:
        model = new CricketGameModel(true);
        break;
      case DAGame.GAME_CRICKET_TAKE:
        model = new CricketGameModel(false);
        break;
      case DAGame.GAME_LAST_MAN_STANDING:
        model = new RandomPointGameModel();
        break;
    }
    model.setTriangulator(new Triangulator());
    return model;
  }
}
