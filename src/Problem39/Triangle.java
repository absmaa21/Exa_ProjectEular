package Problem39;

public class Triangle {
    private final int sideA;
    private final int sideB;
    private final int sideC;
    private int p;

    public Triangle(int sideA, int sideB, int sideC) {
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
        this.p = sideA + sideB + sideC;
    }

    public int getSideA() {
        return sideA;
    }

    public int getSideB() {
        return sideB;
    }

    public int getSideC() {
        return sideC;
    }

    public boolean equals(Triangle o) {
        if(sideA == o.getSideA() || sideA == o.getSideB() || sideA == o.getSideC())
            return true;

        if(sideB == o.getSideA() || sideB == o.getSideB() || sideB == o.getSideC())
            return true;

        return sideC == o.getSideA() || sideC == o.getSideB() || sideC == o.getSideC();
    }
}
