package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class CodeGenerator {

    //Atributes
    private int length;
    private boolean rep;
    private int num_colors;
    private Set<String> set;

    //Constructora
    public CodeGenerator(int l, boolean r, int n) {
        length = l;
        rep = r;
        num_colors = n;
    }
    public String m_generateCodeObjective() {
        Random random = new Random();
        String code = "";
        while (code.length() < length) {
            int i = random.nextInt(num_colors);
            code = code + (char)(i);
        }
        return code;
    }

    /** We shouldn't have a function for that. We need to control the generation of test codes from
     *  the controller. Once there we create an instance of CodeGenerator with a history parameter,
     *  that can be updated through a GameController::updateHistoryForGenerator(). Also, we have a function
     *  GameController::getTest() that calls CodeGenerator, and the CodeGenerator return a new generated test*/
    public String m_generateCodeTest() {
        if (set.isEmpty()) {
            while (set.size() != 150) {
                set.add(codi_random());
            }
            return "1123";
        }
        else {
            Iterator it = set.iterator();
            Set<String> sons = null;
            Random random = new Random();
            String ant = (String) it.next();
            String act, aux = null;
            if (random.nextInt(100) < 3) aux = mutate(ant);
            if (random.nextInt(100) < 3) aux = permutate(ant);
            if (random.nextInt(100) < 2) aux = inverse(ant);
            if (aux == null) aux = codi_random();
            while (! sons.add(aux)) aux = codi_random();

            while(it.hasNext()) {
                act = (String) it.next();
                Pair nous;

                if (random.nextInt(100) < 50) nous = crossover_1(ant, act);
                else nous = crossover_2(ant, act);
                aux = (String) nous.getKey();
                while (!sons.add(aux)) aux = codi_random();
                aux = (String) nous.getValue();
                while (!sons.add(aux)) aux = codi_random();

                if (random.nextInt(100) < 3) aux = mutate(act);
                if (random.nextInt(100) < 3) aux = permutate(act);
                if (random.nextInt(100) < 2) aux = inverse(act);
                while (!sons.add(aux)) aux = codi_random();
                ant = act;
            }
            it = sons.iterator();
            int fitness_total = 0, num;
            while(it.hasNext()) {
                num = (int) it.next();

            }

            for (int )

            return "holaaaa";
        }
    }

    private Pair<String, String> crossover_1(String c1, String c2) {
        Random random = new Random();
        int i = random.nextInt(length-1);
        String nou_1 = c1.substring(0, i-1) + c2.substring(i);
        String nou_2 = c2.substring(0, i-1) + c1.substring(i);
        return new Pair<>(nou_1, nou_2);
    }
    private Pair<String, String> crossover_2(String c1, String c2) {
        Random random = new Random();
        int i = random.nextInt(length-1);
        int j = random.nextInt(length-1);
        String nou_1 = c1.substring(0, i-1) + c2.substring(i, j-1) + c1.substring(j);
        String nou_2 = c2.substring(0, i-1) + c1.substring(i, j-1) + c2.substring(j);
        return new Pair<>(nou_1, nou_2);
    }
    private String mutate(String c) {
        Random random = new Random();
        int i = random.nextInt(length-1);
        int j = random.nextInt(num_colors);
        String nou = c.substring(0, i) + (char) j + c.substring(i+1);
        return nou;
    }
    private String permutate(String c) {
        Random random = new Random();
        int i = random.nextInt(length-1);
        int j = random.nextInt(length-1);
        String nou = c.substring(0, i-1) + c.charAt(j) + c.substring(i+1, j-1)
                + c.charAt(i) + c.substring(j+1);
        return nou;
    }
    private String inverse(String c) {
        Random random = new Random();
        int i = random.nextInt(length-1);
        int j = random.nextInt(length-1);
        if (j<i) {
            int temp = i;
            i = j;
            j = temp;
        }
        String aux = "";
        for (int k = j; k>=i ; k--) aux = aux + c.charAt(k);
        String nou = c.substring(0, i-1) + aux + c.substring(j+1, length-1);
        return nou;
    }
    private String codi_random() {
        String nou = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) nou = nou + random.nextInt(num_colors);
        return nou;
    }

    public String m_generateCodeTestKnuth(Pair<String, String> lastTurn) {return "test";}

    /*
    private Set<String> generateCombinations() {
        Set<String> comb = null;
        String s = "";
        for (int i = 0; i < 4; ++i) {
            s.concat(String.valueOf(i));
            for (int j = 0; j < 4; ++j) {
                s.concat(String.valueOf(j));
                for (int k = 0; k < 4; ++k) {
                    s.concat(String.valueOf(k));
                    for (int l = 0; l < 4; ++l) {
                        s.concat(String.valueOf(l));
                        comb.add(s);
                    }}}}
        return comb;
    }
    */
}
