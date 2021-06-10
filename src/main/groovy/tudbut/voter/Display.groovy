package tudbut.voter

import javax.xml.stream.Location
import java.awt.*
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

class Display {

    int index = 0
    Frame frame
    Container panel = new Container()
    App app
    static Point mouseAtStartup = MouseInfo.pointerInfo.location
    static int i = mouseAtStartup.x / 2 as int
    static int nid = 0
    int id
    static boolean bm = false

    Display(App app) {
        id = nid++
        this.app = app
        frame = new Frame("Voter")
        frame.setSize(300, 500)
        frame.setLocation(i, mouseAtStartup.y / 2 as int)
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

            @Override
            void windowDeiconified(WindowEvent windowEvent) {
                if(index > app.main.stats.length)
                    return

                if(id == 0) {
                    if(app.statDisplay != null) {
                        if(!app.statDisplay.frame.visible) {
                            app.statDisplay.frame.setVisible(true)
                        }
                    }
                }
                if(id == 1) {
                    if(!app.display.frame.visible) {
                        app.display.frame.setVisible(true)
                    }
                }
            }

            @Override
            void windowIconified(WindowEvent windowEvent) {
                if(index > app.main.stats.length)
                    return

                if(id == 0) {
                    if(app.statDisplay != null) {
                        if(app.statDisplay.frame.visible) {
                            app.statDisplay.frame.setVisible(false)
                        }
                    }
                }
                if(id == 1) {
                    if(app.display.frame.visible) {
                        app.display.frame.setVisible(false)
                    }
                }
            }
        })
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            void componentMoved(ComponentEvent componentEvent) {
                if(index > app.main.stats.length)
                    return

                if(bm) {
                    bm = false
                    return
                }
                if(id == 0) {
                    if(app.statDisplay != null) {
                        Point location = frame.location
                        location.x += 300
                        if(app.statDisplay.frame.location != location) {
                            app.statDisplay.frame.setLocation(location)
                            bm = true
                        }
                    }
                }
                if(id == 1) {
                    Point location = frame.location
                    location.x -= 300
                    if(app.display.frame.location != location) {
                        app.display.frame.setLocation(location)
                        bm = true
                    }
                }
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
        VoteComponent component = new VoteComponent(app.getDescription(i), app.calculator, app.main, i, this, false)
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
