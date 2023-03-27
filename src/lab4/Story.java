package lab4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Story {
    private OrganizedContainer room;
    private Cupboard cupboard;

    private Random randomGenerator;

    Story() {
        randomGenerator = new Random();
        room = new OrganizedContainer(10, 10) {
            private final HasLocation[][] floor = new HasLocation[this.height][this.width];

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

            @Override
            public String toString() {
                return "Комната";
            }
        };
        cupboard = new Cupboard();
        room.put(cupboard, new OrganizedContainer.Coords(2, 0));
        room.put(Personage.TIGGER, new OrganizedContainer.Coords(1, 0));

        room.put(Personage.LITTLE_ROO, new OrganizedContainer.Coords(3, 3));
        room.put(Personage.KENGA, new OrganizedContainer.Coords(3, 2));
        room.put(Personage.CHRISTOPHER_ROBIN, new OrganizedContainer.Coords(3, 4));
        room.put(Personage.PIGLET, new OrganizedContainer.Coords(2, 3));
        Personage.LITTLE_ROO.setConversation(
                new ArrayList<>(List.of(new Personage[]{Personage.KENGA, Personage.CHRISTOPHER_ROBIN, Personage.PIGLET})),
                "Все уговаривают Крошку Ру принять рыбий жир"
        );
    }

    private String printEnvironment(Personage personage) {
        StringBuilder str = new StringBuilder(personage + " стоит рядом с ");
        for (HasLocation hasLocation : ((OrganizedContainer) personage.getContainer()).whoIsNextTo(personage)) {
            str.append(hasLocation);
            str.append(", ");
        }
        return str.toString();
    }

    public ArrayList<Eatable> tiggerIsLookingForFood() {
        System.out.println(printEnvironment(Personage.TIGGER));
        class SearchSteps {
            private void openJar() {
                if (randomGenerator.nextBoolean())
                    Personage.TIGGER.new BodyParts("нос").stuckInJar();
                else
                    Personage.TIGGER.new BodyParts("лапа").stuckInJar();
            }

            private boolean addToJarContent(Eatable eatable, ArrayList<Eatable> jarContent) {
                if (eatable == null) return false;
                if (jarContent.contains(eatable)) return false;
                return true;
            }

            private boolean addToFood(Eatable eatable) {
                if (Personage.TIGGER.isLove(eatable)) {
                    System.out.println(Personage.TIGGER + " любит " + eatable);
                    return true;
                } else {
                    System.out.println(Personage.TIGGER + " не любит " + eatable);
                    return false;
                }
            }
        }
        SearchSteps searchSteps = new SearchSteps();
        ArrayList<Eatable> food = new ArrayList<>();
        for (HasLocation jar : cupboard.getItems()) {
            searchSteps.openJar();
            ArrayList<Eatable> jarContent = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Eatable eatable = (Eatable) ((Jar) jar).getRandomItem();
                if (searchSteps.addToJarContent(eatable, jarContent)) {
                    jarContent.add(eatable);
                    if (searchSteps.addToFood(eatable)) food.add(eatable);
                    else ((Jar) jar).put(eatable);
                } else ((Jar) jar).put(eatable);
            }
        }
        return food;
    }

    private void printTalk(Personage p1, Personage p2, String message) {
        if (((OrganizedContainer) p1.getContainer()).isNextTo(p1, p2)) {
            try {
                System.out.println(p1 + " говорит с " + p2 + ": \"" + message + "\"");
                p1.setConversation(p2, message);
            } catch (PersonageIsBusyException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void tiggerAskKenga() {
        Personage.TIGGER.standNextTo(Personage.KENGA);
        System.out.println(Personage.TIGGER + " встал рядом с " + Personage.KENGA);
        this.printTalk(Personage.TIGGER, Personage.KENGA, "Где тут вкусная еда?");
        this.printTalk(Personage.LITTLE_ROO, Personage.KENGA, "Может, не надо?");
        this.printTalk(Personage.KENGA, Personage.LITTLE_ROO, "Ну-ну, милый Ру, вспомни, что ты мне обещал");
    }

    @Override
    public String toString() {
        return "Но чем больше Тигра совал то свой нос, то лапу, то в одну, то в другую банку,\n" +
                "тем больше он находил вещей, которые Тигры не любят.\n" +
                "И когда он перерыл весь буфет и нашел все, что там было, и оказалось, что он ничего этого есть не может,\n" +
                "он спросил Кенгу, но Кенга, и Кристофер Робин, и Пятачок-- все стояли вокруг Крошки Ру, уговаривая его принять рыбий жир.\n" +
                "И Ру говорил: \"Может, не надо?\"-- а Кенга говорила: \"Ну-ну, милый Ру, вспомни, что ты мне обещал\".";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Story that = (Story) o;

        return (this.room.equals(that.room) && this.cupboard.equals(that.cupboard));
    }

    @Override
    public int hashCode() {
        int total = 31;
        total = total * 31 + this.room.hashCode();
        total = total * 31 + this.cupboard.hashCode();
        return total;
    }

}
