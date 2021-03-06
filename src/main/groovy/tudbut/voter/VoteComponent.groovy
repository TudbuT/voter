package tudbut.voter


import java.awt.*
import java.awt.event.*

class VoteComponent extends Container {

    // Rendering and maths
    String a
    String b
    RankingCalculator calculator
    Party voter
    int idx

    // State
    boolean drag = false
    int pxLen = 250
    float val = 0.5f

    VoteComponent(StatDescription description, RankingCalculator calculator, Party voter, int idx, Display display, boolean createMode) {
        setA(description.a)
        setB(description.b)
        setCalculator(calculator)
        setVoter(voter)
        setIdx(idx)

        addMouseListener(createMouseListener())
        addMouseMotionListener(createMouseMotionListener())

        TextArea areaA = new TextArea(a)
        TextArea areaB = new TextArea(b)
        areaA.setBounds(0, 0, 140, 100)
        areaB.setBounds(160, 0, 140, 100)
        areaA.setFocusable(false)
        areaB.setFocusable(false)
        add(areaA)
        add(areaB)

        Button button
        button = new Button("Next")
        button.setBounds(120, 300, 60, 30)
        button.addActionListener({
            voter.stats[idx] = val
            if(createMode)
                display.app.savePartyToConfig(voter, Starter.cid)
            calculator.recalculate()
            if (display.app.statDisplay != null)
                display.app.statDisplay.displayResults()
            display.next()
        })
        add(button)

        if(createMode) {
            button = new Button("Save & End")
            button.setBounds(75, 350, 150, 30)
            button.addActionListener({
                voter.stats[idx] = val
                display.app.savePartyToConfig(voter, Starter.cid)
                calculator.recalculate()
                display.displayResults()
            })
            add(button)
        }

    }

    @Override
    void paint(Graphics graphics) {
        super.paint(graphics)

        graphics.drawString('VS', 142, 50)
        drawBar(graphics)
        drawButton(graphics)
    }

    void drawBar(Graphics graphics) {
        graphics = graphics.create(0, 150, 300, 50)
        int start = (int) (300 / 2 - pxLen / 2)
        graphics.drawLine(start, 5, start + pxLen, 5)
        graphics.dispose()
    }

    void drawButton(Graphics graphics) {
        graphics = graphics.create(0, 150, 300, 50)
        int start = (int) (300 / 2 - pxLen / 2)
        int p = (int) (val * pxLen)
        graphics.fillOval(start + p - 5, 0, 10, 10)
        graphics.drawString(Math.round(getRVal() * 100f) + '%', start, 30)
        graphics.dispose()
    }

    void reloadVal() {
        Point point = getMousePosition()
        if(point != null) {
            point.x -= (int) (300 / 2 - pxLen / 2)
            val = (float) (Math.max(Math.min(point.x as float / pxLen, 1f), 0f) as float)
        }
    }

    float getRVal() {
        return val * 2f - 1
    }

    MouseListener createMouseListener() {
        return new MouseAdapter() {

            @Override
            void mousePressed(MouseEvent mouseEvent) {
                Point p = getMousePosition()
                if(p.y > 110 && p.y < 190) {
                    drag = true
                    reloadVal()
                }
            }

            @Override
            void mouseReleased(MouseEvent mouseEvent) {
                if(drag) {
                    drag = false
                    reloadVal()
                    repaint()
                }
            }
        }
    }

    MouseMotionListener createMouseMotionListener() {
        return new MouseMotionAdapter() {
            @Override
            void mouseMoved(MouseEvent mouseEvent) {
                if(drag) {
                    reloadVal()
                    repaint()
                }
            }

            @Override
            void mouseDragged(MouseEvent mouseEvent) {
                if(drag) {
                    reloadVal()
                    repaint()
                }
            }
        }
    }
}
