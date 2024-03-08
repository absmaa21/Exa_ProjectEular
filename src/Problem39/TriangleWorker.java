package Problem39;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class TriangleWorker implements Callable<Set<Triangle>> {

    private final int p;

    public TriangleWorker(int p) {
        this.p = p;
    }

    @Override
    public Set<Triangle> call() {
        //System.out.println("Thread with perimeter " + p + " started!");
        Set<Triangle> triangles = new HashSet<>();
        for(int a = 1; a < p/3; a++) {
            int pa = p-a;
            int aSquared = a*a;
            for(int b = a; b < pa/2; b++) {
                int bSquared = b*b;
                int c = pa - b;
                if(aSquared + bSquared == c*c) {
                    triangles.add(new Triangle(a,b,c));
                }
            }
        }

        return triangles;
    }
}
