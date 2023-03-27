package lab3;

import java.util.ArrayList;
import java.util.Arrays;

public enum Eatable implements HasLocation {
    COOKIES("печенье"),
    MEAT("мясо"),
    MILK("молоко"),
    SWEETS("Конфеты"),
    CARROT("морковь"),
    BANANA("банан"),
    GRAPE("виноград");

    private final String name;
    private Jar jar;
    private static final ArrayList<Eatable> eatables = new ArrayList<>(Arrays.stream(values()).toList());

    Eatable(String name) {
        this.name = name;
    }


    static ArrayList<Eatable> getAllEatables() {
        return eatables;
    }

    @Override
    public Container getContainer() {
        return this.jar;
    }

    @Override
    public void setContainer(Container container) {
        if (container instanceof Jar)
            this.jar = (Jar) container;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
