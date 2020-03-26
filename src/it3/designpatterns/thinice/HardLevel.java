package it3.designpatterns.thinice;

public class HardLevel implements LevelGenerator{

    private String level
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   #################\n"
            + "##   # ## #####  #    @#####\n"
            + "###  ## $  $        ########\n"
            + "###  ### ### # ##  #\n"
            + "##   ##     #########\n"
            + "# . ########\n"
            + "#  #######\n";

    public String generate(){

        //height 4 width 20

        return level;
    }
}
