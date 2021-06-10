package tudbut.voter

import java.awt.Container
import java.awt.Graphics

class ResultDisplay extends Container {

    RankingCalculator calculator

    ResultDisplay(RankingCalculator calculator) {
        this.calculator = calculator
    }

    @Override
    void paint(Graphics graphics) {
        super.paint(graphics)
        int y = 5
        graphics.drawString('Results: ', 5, y += 15)
        y += 30
        int i = 0
        calculator.result.collect {
            String s = "${++i}. $it.party.name [${Math.round(it.agreement * 100)}%]"
            graphics.drawString(s, 5, y += 15)
        }
    }
}
