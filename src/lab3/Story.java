package lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Story {
    private Room room;
    private Cupboard cupboard;

    private Random randomGenerator;

    Story() {
        randomGenerator = new Random();

        room = new Room(10);
        cupboard = new Cupboard();
        room.put(cupboard, new Coords(2, 0));
        room.put(Personage.TIGGER, new Coords(1, 0));

        room.put(Personage.LITTLE_ROO, new Coords(3, 3));
        room.put(Personage.KENGA, new Coords(3, 2));
        room.put(Personage.CHRISTOPHER_ROBIN, new Coords(3, 4));
        room.put(Personage.PIGLET, new Coords(2, 3));
        Personage.LITTLE_ROO.setConversation(
                new ArrayList<>(List.of(new Personage[]{Personage.KENGA, Personage.CHRISTOPHER_ROBIN, Personage.PIGLET})),
                "Все уговаривают Крошку Ру принять рыбий жир"
        );
    }

    private String printEnvironment(Personage personage) {
        StringBuilder str = new StringBuilder(personage + " стоит рядом с ");
        for (HasLocation hasLocation : ((Room) personage.getContainer()).whoIsNextTo(personage)) {
            str.append(hasLocation);
            str.append(", ");
        }
        return str.toString();
    }

    private void printPersonageIsBusy(Personage personage) {
        System.out.println("Но " + personage + " занят, он говорит с " + personage.getConversation() + " : " + personage.getConversationContent());
    }

    private void printTalk(Personage p1, Personage p2, String message) {
        System.out.println(p1 + " говорит с " + p2 + ": \"" + message + "\"");
    }

    private void talk(Personage p1, Personage p2, String message) {
        if (((Room) p1.getContainer()).isNextTo(p1, p2)) {
            if ((p1.getConversation() == null) && (p2.getConversation() == null)) {
                p1.setConversation(p2, message);
                p2.setConversation(p1, message);
            }
            if ((p1.getConversation().contains(p2)) && (p2.getConversation().contains(p1)))
                this.printTalk(p1, p2, message);
            else {
                this.printTalk(p1, p2, message);
                if (!p1.getConversation().isEmpty())
                    this.printPersonageIsBusy(p1);
                if (!p2.getConversation().isEmpty())
                    this.printPersonageIsBusy(p2);
            }
        }
    }

    public ArrayList<Eatable> tiggerIsLookingForFood() {
        System.out.println(printEnvironment(Personage.TIGGER));
        ArrayList<Eatable> food = new ArrayList<>();
        for (HasLocation jar : cupboard.getItems()) {
            if (randomGenerator.nextBoolean())
                System.out.println(Personage.TIGGER + " сунул нос в банку");
            else
                System.out.println(Personage.TIGGER + " сунул лапу в банку");
            ArrayList<Eatable> jarContent = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Eatable eatable = (Eatable) ((Jar) jar).getRandomItem();
                if (eatable == null) break;
                if (jarContent.contains(eatable)) break;
                jarContent.add(eatable);
                if (Personage.TIGGER.isLove(eatable)) {
                    System.out.println(Personage.TIGGER + " любит " + eatable);
                    food.add(eatable);
                } else {
                    System.out.println(Personage.TIGGER + " не любит " + eatable);
                    ((Jar) jar).put(eatable);
                }
            }
        }
        return food;
    }

    public void tiggerAskKenga() {
        Personage.TIGGER.standNextTo(Personage.KENGA);
        System.out.println(Personage.TIGGER + " встал рядом с " + Personage.KENGA);
        talk(Personage.TIGGER, Personage.KENGA, "Где тут вкусная еда?");
        talk(Personage.LITTLE_ROO, Personage.KENGA, "Может, не надо?");
        talk(Personage.KENGA, Personage.LITTLE_ROO, "Ну-ну, милый Ру, вспомни, что ты мне обещал");
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
