package it3.designpatterns.thinice;

public class EndGame implements Command{

    public void init(StateObserver stateObserver){
        stateObserver.setState(1);
    }
}
