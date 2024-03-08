package Problem39;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TriangleLauncher {
    private void runEuler39() {
        ExecutorService pool = Executors.newFixedThreadPool(6);
        List<TriangleWorker> workers = new ArrayList<>();

        for(int i = 10; i <= 1000; i++) {
            workers.add(new TriangleWorker(i));
        }

        Set<Triangle> largestTriangles = new HashSet<>();

        try {
            List<Future<Set<Triangle>>> future = pool.invokeAll(workers);
            for(Future<Set<Triangle>> triangles : future) {
                if(triangles.get().size() < largestTriangles.size()) continue;
                largestTriangles = triangles.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        int perimeter = largestTriangles.stream().toList().get(0).getP();
        System.out.printf("Perimeter: " + perimeter + " Size: " + largestTriangles.size() + "\n");
    }

    public static void main(String[] args) {
        new TriangleLauncher().runEuler39();
    }
}
