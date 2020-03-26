package it3.designpatterns.thinice;

public class InMainMenu implements State {

    private boolean isActive = true;

    public void setActive(boolean state){
        isActive = state;
    };

    public boolean getState(){
        return isActive;
    }

}
