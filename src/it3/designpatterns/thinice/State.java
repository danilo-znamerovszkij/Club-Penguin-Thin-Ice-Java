package it3.designpatterns.thinice;

import java.awt.*;

public interface State {

    /**
     * A constant field value.
     */
    static final double COMMISSION_RATE = 0.10;

    /**
     * The method for changing the state.
     */
    public void setActive(boolean flag);

    public boolean getState();

}
