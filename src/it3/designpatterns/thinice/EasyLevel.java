package it3.designpatterns.thinice;

public class EasyLevel implements LevelGenerator{

    private String level
            = "   ##########################\n"
            + "   ###.       $            @#\n"
            + "   ##########################\n";

    public String generate(){

        //height 4 width 20

        return level;
    }
}
