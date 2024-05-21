package mainPack;

public class MainFactory {
private static String[] defaultOptions;

    public static String[] initFactory() {
        defaultOptions=new String[]{
                "--plugin","pretty",
                "--plugin","json:target/report/cucumber.json",
                "--plugin","html:target/report/cucumber.html",
                "--glue", "steps",
                "classpath:feature/RestAPI.feature",
                "-m"

        };
        return defaultOptions;
    }
}

//"--plugin","{pretty,html:target/report/cucumber.html, json:target/report/cucumber.json}",
//+"html:target/report/cucumber.html"+","