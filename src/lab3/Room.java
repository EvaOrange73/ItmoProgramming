package lab3;

import java.util.ArrayList;

public class Room extends OrganizedContainer {
    private HasLocation[][] floor;

    Room(int size) {
        super(size, size);
        this.floor = new HasLocation[size][size];
    }

    @Override
    Coords coordsOf(HasLocation hasLocation) {
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (this.floor[i][j] != null && this.floor[i][j].equals(hasLocation))
                    return new Coords(i, j);
            }
        }
        return null;
    }

    @Override
    HasLocation getItem(Coords coords) {
        return this.floor[coords.getX()][coords.getY()];
    }


    @Override
    public void put(HasLocation hasLocation) {
        hasLocation.setContainer(this);
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (this.floor[i][j] == null) {
                    hasLocation.setContainer(this);
                    this.floor[i][j] = hasLocation;
                }
            }
        }
    }

    @Override
    public void put(HasLocation hasLocation, Coords coords) {
        hasLocation.setContainer(this);
        if (this.floor[coords.getX()][coords.getY()] == null)
            this.floor[coords.getX()][coords.getY()] = hasLocation;
    }

    @Override
    public void remove(HasLocation hasLocation) {
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (this.floor[i][j] != null && this.floor[i][j].equals(hasLocation))
                    this.floor[i][j] = null;
            }
        }
    }


    @Override
    public ArrayList<HasLocation> getItems() {
        ArrayList<HasLocation> items = new ArrayList<>();
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (this.floor[i][j] != null)
                    items.add(this.floor[i][j]);
            }
        }
        return items;
    }

    public ArrayList<HasLocation> whoIsNextTo(Personage personage) {
        ArrayList<HasLocation> environment = new ArrayList<>();
        for (int i = 0; i < super.height; i++) {
            for (int j = 0; j < super.width; j++) {
                if (this.floor[i][j] != null && this.isNextTo(personage, this.floor[i][j]))
                    environment.add(this.floor[i][j]);
            }
        }
        return environment;
    }

    @Override
    public String toString() {
        return "Комната";
    }

}
