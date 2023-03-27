package lab3;

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

    abstract ArrayList<HasLocation> getItems();

    abstract Coords coordsOf(HasLocation hasLocation);

    abstract HasLocation getItem(Coords coords);

    abstract void put(HasLocation hasLocation, Coords coords);

    protected final void putNextTo(HasLocation stand, HasLocation move) {
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


}
