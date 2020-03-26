package it3.designpatterns.thinice;

public interface Observer {
    /**
     * The method for getting the state.
     */
    public int getCurrentState();

    public void setState(int state);
}
