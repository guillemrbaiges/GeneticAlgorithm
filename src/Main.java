public class Main {
    public static void main (String [ ] args) {
        Integer numColors = Integer.parseInt(args[0]);
        GeneticAlgorithm GA = new GeneticAlgorithm(numColors,args[1]);
        GA.PlayGame();
    }
}
