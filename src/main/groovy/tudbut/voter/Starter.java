package tudbut.voter;

import de.tudbut.io.StreamReader;
import de.tudbut.tools.FileRW;
import tudbut.parsing.TCN;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Starter {
    
    public static Integer cid = null;
    
    public static void main(String[] args) throws IOException, TCN.TCNException {
        if (args.length == 0) {
            App app = new App(TCN.read(new StreamReader(new File("result.cfg").exists() ? new FileInputStream("result.cfg") : ClassLoader.getSystemResourceAsStream("main.cfg")).readAllAsString()));
            app.initParties();
            app.initCalculator();
            app.initDisplay();
            app.getCalculator().recalculate();
            app.getStatDisplay().displayResults();
            app.getDisplay().next();
        }
        else {
            cid = Integer.parseInt(args[0]);
            App app = new App(TCN.read(new StreamReader(new File("result.cfg").exists() ? new FileInputStream("result.cfg") : ClassLoader.getSystemResourceAsStream("main.cfg")).readAllAsString()));
            app.initParties();
            app.initCalculator();
            app.setDisplay(new CreateDisplay(app));
            app.getDisplay().next();
        }
    }
}
