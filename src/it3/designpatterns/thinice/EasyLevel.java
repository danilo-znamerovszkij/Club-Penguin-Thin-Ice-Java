package it3.designpatterns.thinice;

public class EasyLevel implements LevelGenerator{

    private String level
            = "   #######################\n"
            + "   ###.       $          ####\n"
            + "   ####       $             #\n"
            + "   ########   $            @#\n"
            + "   ##########################\n";

    public String generate(){

        return level;
    }
}
