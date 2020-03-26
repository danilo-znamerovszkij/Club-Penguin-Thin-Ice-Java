package it3.designpatterns.thinice;

public class PauseGame implements Command {

    public void init(StateObserver stateObserver){
        stateObserver.setState(3);
    }
}
