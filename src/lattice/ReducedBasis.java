package lattice;

import java.util.ArrayList;

public class ReducedBasis extends Lattice {

    public ReducedBasis(ArrayList<ArrayList<Long>> grid,int st) {
        super(grid, st);
    }
    @Override
    public void print() {
        System.out.println(" Редукционный базис решётки, алгоритмом LLL: ");
        for (int i = 0; i < _N; i++) {
            System.out.print(" b*_" + (i+1) + " : ");
            for (int j = 0; j < _M; j++) {
                System.out.print(lattice.get(i).get(j) + "\t\t");
            }
            System.out.println();
        }
        System.out.println(" Количество итераций : " + steps);
    }
}
