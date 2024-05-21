package mainPack;


import io.cucumber.core.cli.Main;

import java.util.stream.Stream;

public class Test {
    public static void main(String [] args){

        Stream<String> cucumberOptions= Stream.of(MainFactory.initFactory());
        io.cucumber.core.cli.Main.main(cucumberOptions.toArray(String[]::new));
    }
}
