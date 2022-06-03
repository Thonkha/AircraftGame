package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;


public class Main extends Application {
    private final static String WHITE_CLOUD_IMAGE = "/resources/white-cloud.png";
    private final static String COIN_IMAGE = "/resources/coin1.png";


    private ImageView[] whiteClouds;
    private ImageView[] coins;
    Random randomPositionGenerator;
    private AnimationTimer gameTimer;
    private int i;
    private int points;
    public ImageView ship;
    private ImageView coin;
    private Label pointsLabel,gameOverLabel;


    @Override
    public void start(Stage stage) throws Exception{
        stage.setTitle("Plane Game");
        Pane root = new Pane();
        Scene scene = new Scene(root, 1024, 600);
        ImageView ship = createShip(scene);
        randomPositionGenerator = new Random();

        createGameElements();
        createGameLoop();
        root.getChildren().addAll(ship,whiteClouds[i],coins[i],pointsLabel,gameOverLabel);

        stage.setScene(scene);
        stage.show();

        BackgroundImage myBI = new BackgroundImage(new Image("/resources/game-bg.jpg",1024,600,false,true),
                BackgroundRepeat.REPEAT,BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        root.setBackground(new Background(myBI));

        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            double x = ship.getX();
            double y = ship.getY();

            switch (event.getCode()) {
                case UP -> ship.setY(y - 20);
                case DOWN -> ship.setY(y + 20);
                case LEFT -> ship.setX(x - 20);
                case RIGHT -> ship.setX(x + 20);

            }

            if(ship.getBoundsInParent().intersects(whiteClouds[i].getBoundsInParent())) {
                System.out.println("game over");
                gameTimer.stop();
                gameOverLabel.setText("Game over");

            }
            if(ship.getBoundsInParent().intersects(coins[i].getBoundsInParent())) {
                points++;
                String textToSet = "POINTS : ";
                if (points < 10) {
                    textToSet = textToSet + "0";
                }
                pointsLabel.setText(textToSet + points);
            }
        });



        }

    private void createGameElements() {

        pointsLabel = new Label("POINTS : 00");
        pointsLabel.setLayoutX(900);
        pointsLabel.setLayoutY(20);
        pointsLabel.setTextFill(Color.WHITE);
        pointsLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 17));
        gameOverLabel = new Label();
        gameOverLabel.setLayoutX(430);
        gameOverLabel.setLayoutY(300);
        gameOverLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 35));
        gameOverLabel.setTextFill(Color.RED);

        coins = new ImageView[5];
        for(int i = 0; i < coins.length; i++) {
            coins[i] = new ImageView(COIN_IMAGE);
            coins[i].setFitWidth(60);
            coins[i].setFitHeight(60);
        }


        whiteClouds = new ImageView[10];
        for(int i = 0; i < whiteClouds.length; i++) {
            whiteClouds[i] = new ImageView(WHITE_CLOUD_IMAGE);
            whiteClouds[i].setFitWidth(150);
            whiteClouds[i].setFitHeight(100);
        }
    }

    private void moveGameElements() {

        for (ImageView coin : coins) {
            coin.setLayoutX(coin.getLayoutX() - 5);
        }


        for (ImageView whiteCloud : whiteClouds) {
            whiteCloud.setLayoutX(whiteCloud.getLayoutX() - 5);
        }


    }

    private void checkIfElementAreBehindTheShipAndRelocated() {


        for (ImageView coin : coins) {
            if (coin.getLayoutX() < -100) {
                setNewElementPosition(coin);
            }
        }

        for (ImageView whiteCloud : whiteClouds) {
            if (whiteCloud.getLayoutX() < -100) {
                setNewElementPosition(whiteCloud);
            }
        }
    }

    private void setNewElementPosition(ImageView image) {

        image.setLayoutY(randomPositionGenerator.nextInt(370));
        image.setLayoutX(randomPositionGenerator.nextInt(1200)+300);

    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveGameElements();
                checkIfElementAreBehindTheShipAndRelocated();

            }
        };
        gameTimer.start();
    }


    private ImageView createShip(Scene scene) {
        ImageView image = new ImageView(new Image("/resources/ship1.png"));
        image.setFitWidth(150);
        image.setFitHeight(70);
        image.setLayoutY(200);

        return image;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
