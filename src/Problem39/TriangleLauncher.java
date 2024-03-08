package Problem39;

import java.util.*;
import java.util.concurrent.*;

public class TriangleLauncher {
    private int runEuler39() {
        ExecutorService pool = Executors.newFixedThreadPool(6, r -> {
            Thread t = new Thread(r);
            t.setPriority(Thread.MAX_PRIORITY);
            return t;
        });
        TriangleWorker[] workersArray = new TriangleWorker[496];

        for(int i = 0; i <= 495; i++) {
            workersArray[i] = new TriangleWorker(i*2+10);
        }

        Set<Triangle> largestTriangles = new HashSet<>();

        try {
            List<Future<Set<Triangle>>> future = pool.invokeAll(Arrays.stream(workersArray).toList());
            pool.shutdown();

            for(Future<Set<Triangle>> triangles : future) {
                Set<Triangle> t = triangles.get();
                if(t.size() < largestTriangles.size()) continue;
                largestTriangles = t;
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return largestTriangles.iterator().next().getP();
    }

    public static void main(String[] args) {
        int n = 1000;
        double sum = 0;
        int solution = -1;

        for (int i = 0; i < n; i++) {
            double start = System.currentTimeMillis();
            solution = new TriangleLauncher().runEuler39();
            double end = System.currentTimeMillis();
            sum += end - start;
        }

        System.out.printf("Execution time: %f milliseconds%nSolution: %d", (sum / n), solution);
    }
}
