package lab3;

public class Coords {
    private final int x;
    private final int y;

    Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isNextTo(Coords c) {
        return (Math.abs(this.x - c.getX()) + Math.abs(this.y - c.getY())) == 1;
    }

    @Override
    public String toString() {
        return "Координаты: x = " + this.x + ", y = " + this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Coords that = (Coords) o;

        return (that.x == this.x && that.y == this.y);
    }

    @Override
    public int hashCode() {
        return this.x + this.y;
    }
}
