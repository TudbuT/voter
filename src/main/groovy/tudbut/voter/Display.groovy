package tudbut.voter


import java.awt.*
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent

class Display {

    int index = 0
    Frame frame
    Container panel = new Container()
    App app
    static int i = 0

    Display(App app) {
        this.app = app
        frame = new Frame("Voter")
        frame.setSize(300, 500)
        frame.setLocation(i, 0)
        i += 300
        sleep 100
        frame.setSize(300, 500)
        frame.setResizable(false)
        frame.setVisible(true)
        frame.add(panel)
        frame.addWindowListener(new WindowAdapter() {
            @Override
            void windowClosing(WindowEvent windowEvent) {
                frame.dispose()
                System.exit(0)
            }
        })
    }

    void next() {
        int i = index++
        if(i >= app.main.stats.length) {
            app.calculator.recalculate()
            displayResults()
            app.statDisplay.frame.dispose()
            return
        }
        panel.removeAll()
        VoteComponent component = new VoteComponent(app.getDescription(i), app.calculator, app.main, i, this)
        component.setVisible(true)
        component.setSize(300, 500)
        panel.add(component)
        panel.repaint()
    }

    void displayResults() {
        panel.removeAll()
        ResultDisplay component = new ResultDisplay(app.calculator)
        panel.add(component)
        component.setVisible(true)
        component.setSize(300, 500)
        panel.repaint()
    }
}
