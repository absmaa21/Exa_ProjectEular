package Problem39;

import java.util.Objects;

public class Triangle {
    private final int sideA;
    private final int sideB;
    private final int sideC;
    private int p;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Triangle triangle = (Triangle) o;
        return sideA == triangle.sideA && sideB == triangle.sideB && sideC == triangle.sideC && p == triangle.p;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sideA + sideB + sideC);
    }
}
