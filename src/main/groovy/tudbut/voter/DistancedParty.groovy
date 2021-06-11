package tudbut.voter

class DistancedParty {

    Party party
    float distance
    float distanceUnc
    RankingCalculator calculatedBy

    DistancedParty(Party party, float distance, float distanceUnc, RankingCalculator calculator) {
        setParty(party)
        setDistance(distance)
        setDistanceUnc(distanceUnc)
        setCalculatedBy(calculator)
    }

    float getAgreement() {
        return 1 - (distanceUnc / party.stats.length) as float
    }
}
