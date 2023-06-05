package lattice;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Scanner;

import static java.util.regex.Pattern.compile;

public class Lattice {
    protected int steps = 0;
    protected int _N;
    protected int _M;
    ArrayList<ArrayList<Long>> lattice = new ArrayList<>(_N);

    public Lattice(int n, int m) {
        _N = n;
        _M = m;
        System.out.println(" Количество векторов: " + _N + "\t" + "Размерность : " + _M);
        for (int i = 0; i < _N; i++) {

            lattice.add(new ArrayList<Long>());
            System.out.print(" b_" + (i + 1) + " : ");
//
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            Pattern pattern = compile("-?\\d+");
            Matcher matcher = pattern.matcher(input);
//
            for (int j = 0; j < _M; j++) {
                matcher.find();
                //lattice.get(i).add((long)(Math.random()*(100+1)) - 50);
                lattice.get(i).add(Long.parseLong(matcher.group()));
            }
        }
    }
    public Lattice(ArrayList<ArrayList<Long>> grid, int st) {
        steps = st;
        _N = grid.size();
        _M = grid.get(0).size();
        lattice = grid;
    }

    public void print() {
        System.out.println(" Базис " + _N + "-ой решётки : ");
        for (int i = 0; i < _N; i++) {
            System.out.print(" b_" + (i + 1) + " : ");
            for (int j = 0; j < _M; j++) {
                System.out.print(lattice.get(i).get(j) + "\t\t");
            }
            System.out.println();
        }
    }

    public ReducedBasis toLLLReducedBasis(double q) {

        ArrayList<ArrayList<Long>> b_gram = new ArrayList<>(_N);

        for (int i = 0; i < _N; i++) {
            b_gram.add(new ArrayList<Long>(_M));
        }
        long[][] c = new long[_N][_M];
        for (int i = 0; i < _N; i++) {
            for (int j = 0; j < _M; j++) {
                b_gram.get(i).add((long) (lattice.get(i).get(j)));
            }
        }

        while (true) {
            boolean check = false ;
            for (int i = 1; i < _N; i++) {
                for (int j = i - 1; j > -1; j--) {
                    c[i][j] = Math.round(scalar(b_gram.get(i), b_gram.get(j)) / scalar(b_gram.get(j), b_gram.get(j)));
                    for (int k = 0; k < _M; k++) {
                        b_gram.get(i).set(k, (b_gram.get(i).get(k) - c[i][j] * b_gram.get(j).get(k)));
                        ++steps;
                    }
                }
            }
            for (int i = 1; i < _N; i++) {
                if ((q * scalar(P(b_gram, i - 1), P(b_gram, i - 1))) > scalar(P(b_gram, i), P(b_gram, i))) {
                    Collections.swap(b_gram, i - 1, i);
                    check = true;
                }
            }
            if (!check)
                break;
        }
        return new ReducedBasis(b_gram, steps);
    }

    public ArrayList<Long> P(ArrayList<ArrayList<Long>> vector_1, int start) {

        ArrayList<Long> temp = new ArrayList<Long>(_M);
        for (int i = 0; i < _M; i++)
            temp.add(0L);
        for (int i = start; i < _N; i++) {
            long k = Math.round(Lattice.scalar(vector_1.get(start), vector_1.get(i)) / Lattice.scalar(vector_1.get(i), vector_1.get(i)));
            for (int j = 0; j < _M; j++) {

                //temp.set(j,  k * vector_1.get(i).get(j));
                temp.set(j, temp.get(j) + k * vector_1.get(i).get(j));//right
                //temp.set(i, temp.get(i) + k * vector_1.get(i).get(j));
            }
        }
        return temp;
    }

    public static <T extends Number> double scalar(ArrayList<T> vector_1, ArrayList<T> vector_2) {
        if (vector_1.size() != vector_2.size()) {
            return -1;
        }
        double scalar = 0.0;
        for (int i = 0; i < vector_1.size(); i++)
            scalar += vector_1.get(i).doubleValue() * vector_2.get(i).doubleValue();
        return scalar;
    }
}
