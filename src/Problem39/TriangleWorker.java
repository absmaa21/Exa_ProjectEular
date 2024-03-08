package Problem39;

import java.util.Set;
import java.util.concurrent.Callable;

public class TriangleWorker implements Callable<Set<Triangle>> {

    private int p;

    public TriangleWorker(int p) {
        this.p = p;
    }

    @Override
    public Set<Triangle> call() throws Exception {
        System.out.println("Thread with perimeter " + p + " started!");
        return null;
    }
}
