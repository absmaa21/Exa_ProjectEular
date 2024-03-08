package Problem39;

import java.util.Objects;

public class Triangle {
    private final int sideA;
    private final int sideB;
    private final int sideC;
    private final int p;

    public Triangle(int sideA, int sideB, int sideC, int p) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.p = p;
    }

    public int getP() {
        return p;
    }

    @Override
    public int hashCode() {
        return Objects.hash(p);
    }
}
