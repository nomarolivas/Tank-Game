package edu.csc413.tankgame;

import edu.csc413.tankgame.model.*;
import edu.csc413.tankgame.view.*;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class GameDriver {
    private final MainView mainView;
    private final RunGameView runGameView;
    private final GameWorld gameWorld;

    public GameDriver() {
        mainView = new MainView(this::startMenuActionPerformed);
        runGameView = mainView.getRunGameView();
        gameWorld= new GameWorld();
    }

    public void start() {
        mainView.setScreen(MainView.Screen.START_GAME_SCREEN);
    }

    private void startMenuActionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()) {
            case StartMenuView.START_BUTTON_ACTION_COMMAND -> runGame();
            case StartMenuView.EXIT_BUTTON_ACTION_COMMAND -> mainView.closeGame();
            default -> throw new RuntimeException("Unexpected action command: " + actionEvent.getActionCommand());
        }
    }

    private void runGame() {
        mainView.setScreen(MainView.Screen.RUN_GAME_SCREEN);
        Runnable gameRunner = () -> {
            setUpGame();
            while (updateGame()) {
                runGameView.repaint();
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException exception) {
                    throw new RuntimeException(exception);
                }
            }
            mainView.setScreen(MainView.Screen.END_MENU_SCREEN);
            resetGame();
        };
        new Thread(gameRunner).start();
    }

    /**
     * setUpGame is called once at the beginning when the game is started. Entities that are present from the start
     * should be initialized here, with their corresponding sprites added to the RunGameView.
     */
    private void setUpGame() {
        // TODO: Implement.

        PlayerTank playerTank= new PlayerTank(Constants.PLAYER_TANK_ID,Constants.PLAYER_TANK_INITIAL_X,
                Constants.PLAYER_TANK_INITIAL_Y,Constants.PLAYER_TANK_INITIAL_ANGLE);


        AiTank aiTank1= new AiTank(Constants.AI_TANK_1_ID, Constants.AI_TANK_1_INITIAL_X, Constants.AI_TANK_1_INITIAL_Y,
                Constants.AI_TANK_1_INITIAL_ANGLE) {
        };

        AiTank2 aiTank2= new AiTank2(Constants.AI_TANK_2_ID, Constants.AI_TANK_2_INITIAL_X, Constants.AI_TANK_2_INITIAL_Y,
                Constants.AI_TANK_2_INITIAL_ANGLE) {
        };

        gameWorld.addEntity(playerTank);
        gameWorld.addEntity(aiTank1);
        gameWorld.addEntity(aiTank2);

        runGameView.addSprite(playerTank.getId(),RunGameView.PLAYER_TANK_IMAGE_FILE,
                playerTank.getX(),playerTank.getY(),playerTank.getAngle());

        runGameView.addSprite(aiTank1.getId(),RunGameView.AI_TANK_IMAGE_FILE,
                aiTank1.getX(),aiTank1.getY(),aiTank1.getAngle());

        runGameView.addSprite(aiTank2.getId(),RunGameView.AI_TANK_IMAGE_FILE,
                aiTank2.getX(),aiTank2.getY(),aiTank2.getAngle());

        List<WallInformation> wallStorage= WallInformation.readWalls();
        for(WallInformation entity: wallStorage ){
            Entity wall= new Walls(entity.getX(),entity.getY(),0);
            runGameView.addSprite(wall.getId(),entity.getImageFile(),entity.getX(),entity.getY(),0);
        }
    }

    /**
     * updateGame is repeatedly called in the gameplay loop. The code in this method should run a single frame of the
     * game. As long as it returns true, the game will continue running. If the game should stop for whatever reason
     * (e.g. the player tank being destroyed, escape being pressed), it should return false.
     */

    private boolean updateGame() {
        // TODO: Implement.

        for(Entity entity: gameWorld.getEntities()){
            entity.move(gameWorld);
        }

        for(Entity entity: gameWorld.getEntities()){
            runGameView.setSpriteLocationAndAngle(entity.getId(),entity.getX(),entity.getY(),entity.getAngle());
        }

        for(Entity entity: gameWorld.getTempEntities()){
            runGameView.addSprite(entity.getId(), RunGameView.SHELL_IMAGE_FILE, entity.getX(),
                    entity.getY(),entity.getAngle());
            gameWorld.addEntity(entity);
        }
        gameWorld.clearTempList();

        for(Entity entity: gameWorld.getEntities()){
            entity.checkBounds(gameWorld);
        }

        for (Entity entity: gameWorld.getTempEntities()){
            runGameView.removeSprite(entity.getId());
            gameWorld.removeEntity(entity.getId());
            gameWorld.getEntity(entity.getId());
        }
        gameWorld.clearTempList();

        for (int i = 0; i < gameWorld.getEntities().size(); i++) {
            Entity entity1 = gameWorld.iterator(i);
            for (int j = i; j < gameWorld.getEntities().size(); j++) {
                Entity entity2 = gameWorld.iterator(j);
                if (entity1 instanceof Walls && entity2 instanceof Walls) {

                } else {
                    if (gameWorld.collisionDetection(entity1, entity2)) {
                        gameWorld.handleCollision(entity1, entity2);
                    }
                }
            }
        }
        return true;
    }

    /**
     * resetGame is called at the end of the game once the gameplay loop exits. This should clear any existing data from
     * the game so that if the game is restarted, there aren't any things leftover from the previous run.
     */
    private void resetGame() {
        // TODO: Implement.
        KeyboardReader keyboard = KeyboardReader.instance();
        if(keyboard.escapePressed()){
            runGameView.reset();
        }
//        runGameView.reset();
    }

    public static void main(String[] args) {
        GameDriver gameDriver = new GameDriver();
        gameDriver.start();
    }
}
