package lab4;

import java.util.ArrayList;
import java.util.Random;

public class Jar implements Container, HasLocation {


    private Cupboard cupboard;
    private final ArrayList<HasLocation> items;
    private static final Random randomGenerator = new Random();

    public Jar() {
        int size = randomGenerator.nextInt(10) + 1;
        ArrayList<Eatable> food = Personage.TIGGER.getUnfavoriteFood();
        ArrayList<HasLocation> items = new ArrayList<>();
        for (int i = 0; i < size; i++)
            items.add(food.get(randomGenerator.nextInt(food.size())));
        this.items = items;
    }

    public HasLocation getRandomItem() {
        if (this.items.size() == 0) {
            System.out.println("Банка пуста");
            return null;
        }
        int index = randomGenerator.nextInt(this.items.size());
        HasLocation item = this.items.get(index);
        this.items.remove(index);
        return item;
    }

    @Override
    public Container getContainer() {
        return cupboard;
    }

    @Override
    public void setContainer(Container container) {
        if (container instanceof Cupboard)
            this.cupboard = (Cupboard) container;
        else throw new IncorrectContainerException(container, this);
    }

    @Override
    public void put(HasLocation hasLocation) {
        hasLocation.setContainer(this);
        this.items.add(hasLocation);
    }

    @Override
    public void remove(HasLocation hasLocation) {
        hasLocation.setContainer(null);
        this.items.remove(hasLocation);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Банка с: ");
        for (HasLocation hasLocation : this.items) {
            str.append(hasLocation.toString());
            str.append(", ");
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Jar that = (Jar) o;

        return (this.items.equals(that.items));
    }

    @Override
    public int hashCode() {
        int total = 31;
        for (HasLocation hasLocation : this.items) {
            total = total * 31 + hasLocation.hashCode();
        }
        return total;
    }
}
