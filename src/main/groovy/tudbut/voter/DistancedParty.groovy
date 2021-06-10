package tudbut.voter

class DistancedParty {

    Party party
    float distance
    RankingCalculator calculatedBy

    DistancedParty(Party party, float distance, RankingCalculator calculator) {
        setParty(party)
        setDistance(distance)
        setCalculatedBy(calculator)
    }

    float getAgreement() {
        return 1 - (distance*distance / party.stats.length) as float
    }
}
