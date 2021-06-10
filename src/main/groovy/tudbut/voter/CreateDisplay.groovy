package tudbut.voter

import de.tudbut.tools.FileRW

class CreateDisplay extends Display {
    CreateDisplay(App app) {
        super(app)
    }

    void next() {
        int i = index++
        if(i >= app.main.stats.length) {
            app.calculator.recalculate()
            displayResults()
            return
        }
        panel.removeAll()
        VoteComponent component = new VoteComponent(app.getDescription(i), app.calculator, app.parties[Main.cid], i, this, true)
        if(app.parties[Main.cid].statsLoaded)
            component.val = app.parties[Main.cid].stats[i]
        component.setVisible(true)
        component.setSize(300, 500)
        panel.add(component)
        panel.repaint()
    }

    void displayResults() {
        panel.removeAll()
        panel.repaint()
        new FileRW("result.cfg").setContent(app.config.toString())
        System.exit(0)
    }
}
