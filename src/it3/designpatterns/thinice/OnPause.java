package it3.designpatterns.thinice;

public class OnPause implements State {

    private boolean isActive = false;

    public void setActive(boolean state){
        isActive = state;
    };

    public boolean getState(){
        return isActive;
    }
}
