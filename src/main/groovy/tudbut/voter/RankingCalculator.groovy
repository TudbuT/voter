package tudbut.voter

import tudbut.parsing.TudSort

class RankingCalculator {

    Party[] parties = []
    Party voter
    DistancedParty[] result = null

    void recalculate() {
        DistancedParty[] distances = parties.collect {
            float distUnc = 0
            it.stats.eachWithIndex {f, i ->
                f -= voter.stats[i]
                distUnc += Math.abs(f)
            }
            return new DistancedParty(it, distUnc, this)
        }

        result = TudSort.sortDouble(distances, {it.distance as double})
    }
}
