package it3.designpatterns.thinice;

public class StateObserver implements Observer {

    OnPause onPause;
    InGame inGame;
    InMainMenu inMainMenu;

    private int currentState;

    public StateObserver(){
        onPause = new OnPause();
        inGame = new InGame();
        inMainMenu = new InMainMenu();
    }

    public int getCurrentState(){

        if(inMainMenu.getState())return 1;

        if(inGame.getState())return 2;

        if(onPause.getState())return 3;

        return 0;
    }

    public void setState(int state){

        switch(state){
            case 1:
            //in main menu
                inMainMenu.setActive(true);
                onPause.setActive(false);
                inGame.setActive(false);
                break;
            case 2:
                //in game
                inMainMenu.setActive(false);
                onPause.setActive(false);
                inGame.setActive(true);
                break;
            case 3:
                //on pause
                inMainMenu.setActive(false);
                onPause.setActive(true);
                inGame.setActive(false);
                break;
        }

    }

}
