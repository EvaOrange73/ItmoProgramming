package lab4;

import java.util.ArrayList;

/**
 * Контейнер, внутри которого вещи имеют четкое положение (координаты)
 */
public abstract class OrganizedContainer implements Container {

    protected int height;
    protected int width;

    OrganizedContainer(int height, int width) {
        this.height = height;
        this.width = width;
    }
    public static class Coords {
        private int x;
        private int y;

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


    abstract ArrayList<HasLocation> getItems();

    abstract Coords coordsOf(HasLocation hasLocation);

    abstract HasLocation getItem(Coords coords);

    abstract void put(HasLocation hasLocation, Coords coords) throws IncorrectContainerException;

    protected final void putNextTo(HasLocation stand, HasLocation move) throws IncorrectContainerException{
        Coords coords = this.coordsOf(stand);
        if (coords != null) {
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    if (coords.isNextTo(new Coords(i, j))
                            && (this.getItem(new Coords(i, j)) == null)) {
                        this.put(move, new Coords(i, j));
                        return;
                    }
                }
            }
        }
    }

    final boolean isNextTo(HasLocation o1, HasLocation o2) {
        return this.coordsOf(o1).isNextTo(this.coordsOf(o2));
    }

    public ArrayList<HasLocation> whoIsNextTo(Personage personage) {
        ArrayList<HasLocation> environment = new ArrayList<>();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.getItem(new Coords(i, j)) != null && this.isNextTo(personage, this.getItem(new Coords(i, j))))
                    environment.add(this.getItem(new Coords(i, j)));
            }
        }
        return environment;
    }


}
