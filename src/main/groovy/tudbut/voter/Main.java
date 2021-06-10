package tudbut.voter;

import de.tudbut.io.StreamReader;
import de.tudbut.tools.FileRW;
import tudbut.parsing.TCN;

import java.io.IOException;

public class Main {
    
    public static void main(String[] args) throws IOException, TCN.TCNException {
        App app = new App(TCN.read(new StreamReader(ClassLoader.getSystemResourceAsStream("main.cfg")).readAllAsString()));
        app.initParties();
        app.initCalculator();
        app.initDisplay();
        app.getCalculator().recalculate();
        app.getStatDisplay().displayResults();
        app.getDisplay().next();
    }
}
