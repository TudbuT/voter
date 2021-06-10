package tudbut.voter

import tudbut.parsing.TCN
import tudbut.parsing.TCNArray

class App {

    TCN config
    Display statDisplay
    Display display
    Party main = new Party()
    RankingCalculator calculator = new RankingCalculator()
    Party[] parties

    App(TCN config) {
        this.config = config
        main.stats = new float[config.getInteger('dimensions')].collect {0.5f}
    }

    void initCalculator() {
        calculator.voter = main
        calculator.parties = parties
    }

    void initParties() {
        parties = config.getArray('parties').collect {
            createPartyFromConfig(it as TCN)
        }
    }

    void initDisplay() {
        display = new Display(this)
        sleep 500
        statDisplay = new Display(this)
    }

    Party createPartyFromConfig(TCN preferences) {
        Party party = new Party()
        party.name = preferences.getString('name')
        party.stats = new float[config.getInteger('dimensions')]
        TCNArray stats = preferences.getArray('stats')
        party.stats.eachWithIndex {it, idx ->
            party.stats[idx] = stats.getFloat(idx)
        }
        return party
    }

    StatDescription getDescription(int id) {
        StatDescription description = new StatDescription()
        TCNArray stat = config.getArray('stats').getArray(id)
        description.a = stat.getString(0)
        description.b = stat.getString(1)
        return description
    }
}
