package it3.designpatterns.thinice;

public class StartGame implements Command{

    public StartGame(){

    }

    public void init(StateObserver stateObserver){
        stateObserver.setState(2);
    }
}

