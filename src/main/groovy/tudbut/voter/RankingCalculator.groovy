package tudbut.voter

import tudbut.parsing.TudSort

class RankingCalculator {

    Party[] parties = []
    Party voter
    DistancedParty[] result = null

    void recalculate() {
        DistancedParty[] distances = parties.collect {
            float distanceSq = 0
            it.stats.eachWithIndex {f, i ->
                f -= voter.stats[i]
                distanceSq += f*f
            }
            return new DistancedParty(it, Math.sqrt(distanceSq) as float, this)
        }

        result = TudSort.sortDouble(distances, {it.distance as double})
    }
}
