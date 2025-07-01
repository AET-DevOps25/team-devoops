package meet_at_mensa.matching.algorithm;

import java.util.List;

public class MatchingSolution {
    
    private List<MatchingSolutionBlock> solution;

    public MatchingSolution(){}

    public MatchingSolution(List<MatchingSolutionBlock> solution) {
        this.solution = solution;
    }

    public List<MatchingSolutionBlock> getSolution() {
        return this.solution;
    }

    public void setSolution(List<MatchingSolutionBlock> solution) {
        this.solution = solution;
    }

    public void add(MatchingSolutionBlock block) {
        this.solution.add(block);
    }

}
