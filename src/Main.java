import lattice.Lattice;
import lattice.ReducedBasis;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //
        Scanner in = new Scanner(System.in);
        System.out.print(" Количество векторов в решётке : ");
        int n_lattice = in.nextInt();
        System.out.print(" Размерность : ");
        int m_lattice = in.nextInt();
        //
        /*
        int n_lattice,m_lattice;
        do {
            n_lattice = (int) (random() * 4) + 2;
            m_lattice = (int) (random() * 4) + 2;
        } while (n_lattice > m_lattice);

         */
        Lattice lattice = new Lattice(n_lattice, m_lattice);
        lattice.print();
        ReducedBasis b = lattice.toLLLReducedBasis(0.1);
        b.print();

    }
}
