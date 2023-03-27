package lab3;

import java.util.ArrayList;

public class Cupboard extends OrganizedContainer implements HasLocation {
    private ArrayList<HasLocation> shelf;
    private Room room;

    Cupboard(int length) {
        super(0, length);
        this.shelf = new ArrayList<>();
        for (int i = 0; i < length; i++)
            this.shelf.add(null);
    }

    Cupboard() {
        this(5);
        for (int i = 0; i < super.width; i++) {
            Jar jar = new Jar();
            this.shelf.set(i, jar);
        }
    }


    @Override
    public Container getContainer() {
        return this.room;
    }

    @Override
    public void setContainer(Container container) {
        if (container instanceof Room)
            this.room = (Room) container;
    }

    @Override
    public void put(HasLocation hasLocation) {
        for (int i = 0; i < super.width; i++) {
            if (this.shelf.get(i) == null) {
                hasLocation.setContainer(this);
                this.shelf.set(i, hasLocation);
            }
        }
    }

    @Override
    public void remove(HasLocation hasLocation) {
        this.shelf.set(this.shelf.indexOf(hasLocation), null);
    }

    @Override
    public ArrayList<HasLocation> getItems() {
        ArrayList<HasLocation> items = new ArrayList<>();
        for (HasLocation hasLocation : this.shelf)
            if (hasLocation != null)
                items.add(hasLocation);
        return items;
    }

    @Override
    Coords coordsOf(HasLocation hasLocation) {
        return new Coords(0, this.shelf.indexOf(hasLocation));
    }

    @Override
    HasLocation getItem(Coords coords) {
        return this.shelf.get(coords.getY());
    }

    @Override
    void put(HasLocation hasLocation, Coords coords) {
        this.shelf.set(coords.getY(), hasLocation);
    }

    @Override
    public String toString() {
        return "Буфет";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Cupboard that = (Cupboard) o;

        return (this.shelf.equals(that.shelf));
    }

    @Override
    public int hashCode() {
        int total = 31;
        for (HasLocation hasLocation : this.shelf) {
            total = total * 31 + hasLocation.hashCode();
        }
        return total;
    }
}
