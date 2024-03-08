package Problem39;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TriangleLauncher {
    private void runEuler39() {
        ExecutorService pool = Executors.newFixedThreadPool(6, r -> {
            Thread t = new Thread(r);
            t.setPriority(Thread.MAX_PRIORITY);
            return t;
        });
        TriangleWorker[] workersArray = new TriangleWorker[991];

        for(int i = 0; i <= 990; i++) {
            workersArray[i] = new TriangleWorker(i+10);
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

        int perimeter = largestTriangles.stream().toList().get(0).getP();
        //System.out.printf("Perimeter: " + perimeter + " Size: " + largestTriangles.size() + "\n");
    }

    public static void main(String[] args) {
        int n = 1000;
        double sum = 0;

        for (int i = 0; i < n; i++) {
            double start = System.currentTimeMillis();
            new TriangleLauncher().runEuler39();
            double end = System.currentTimeMillis();
            sum += end - start;
        }

        System.out.printf("Execution time: %f milliseconds%n", (sum / n));
    }
}
