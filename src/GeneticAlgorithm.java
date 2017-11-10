import javafx.util.Pair;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class GeneticAlgorithm {

    private Integer numCols;
    private String ObjectiveCode;

    public GeneticAlgorithm(Integer nc, String oc) {
        numCols = nc;
        ObjectiveCode = oc;
    }

    public Pair<Integer,Integer> getFB(String testCode, String objectiveCode) {
        //TODO(?): check they're same size
        Integer correctPlace = 0;
        Integer correctColor = 0;
        Pair<Integer,Integer> result;

        char[] test = testCode.toCharArray();
        char[] objective = objectiveCode.toCharArray();

        //Gives blacks
        for (int i = 0; i < test.length; ++i) {
            if ( test[i] == objective[i] ) {
                test[i] = '*';
                ++correctPlace;
            }
        }

        //Gives whites
        for (int i = 0; i < test.length; ++i) {
            for (int j = 0; j < objective.length; ++j) ++correctColor;
        }

        result = new Pair<>(correctPlace, correctColor);
        return result;
    }

    public Integer fitnessValue(String test, String reference, ArrayList<Pair<String, Pair<Integer,Integer> > > guesses) {

        ArrayList<Pair<Integer, Integer>> differences = new ArrayList<>();
        for (int i = 0; i < guesses.size(); ++i) {
            differences.add(getDifference(test,guesses.get(i)));
        }

        Integer blackdiff = 0;
        Integer whitediff = 0;
        for (int i = 0; i < differences.size(); ++i) {
            blackdiff += differences.get(i).getKey().intValue();
            whitediff += differences.get(i).getValue().intValue();
        }
        return blackdiff + whitediff;
    }

    public Pair<Integer,Integer> getDifference(String test,  Pair<String, Pair<Integer,Integer>> reference) {

        Pair<Integer,Integer> testResult = getFB(test,reference.getKey());

        Integer B = abs(testResult.getKey().intValue() - reference.getValue().getKey().intValue());
        Integer W = abs(testResult.getValue().intValue() - reference.getValue().getValue().intValue());

        Pair<Integer,Integer> difference = new Pair<>(B,W);

        return difference;
    }

    public void PlayGame() {

        ArrayList<Pair<String, Pair<Integer,Integer> > >
                guesses = new ArrayList<>();

        String initialGuess = "1122";
        Integer turn = 1;

        Pair<Integer,Integer> result = onePlay(initialGuess,turn,ObjectiveCode);
        Pair<String,Pair<Integer,Integer>> P = new Pair<>(initialGuess,result);
        guesses.add(P);

        ArrayList<String> eligibles = new ArrayList<>();

        Pair<Integer,Integer> Sol = new Pair<>(4,0);
        while (result != Sol) {
            eligibles = GA();

            while(eligibles.size()==0){
                eligibles = GA();
            }

            ArrayList<String> checkRepeated = new ArrayList<>();
            for (int i = 0; i < guesses.size(); ++i) checkRepeated.add(guesses.get(i).getKey());
            eligibles.removeAll(checkRepeated);

            ++turn;
            //agafo el primer, potser val la pena mirar l'heuristica
            result = onePlay(eligibles.get(0),turn,ObjectiveCode);
            Pair<String, Pair<Integer,Integer> > G = new Pair<> (eligibles.get(0),result);
            guesses.add(G);

            if (result == Sol){
                System.out.println("Codebraker Wins!");
                System.out.println("Objective Code: "+ ObjectiveCode);
            }
        }
    }

    public Pair<Integer,Integer> onePlay(String test, Integer turn, String objective) {
        System.out.println("Turn nº: " + turn + " trying with code " + turn + " to find " + objective);
        return getFB(test, objective);
    }
}
