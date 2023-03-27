package lab4;

import java.util.ArrayList;
import java.util.List;

public enum Personage implements HasLocation {
    TIGGER("Тигра", new ArrayList<>(List.of(new Eatable[]{Eatable.MEAT}))),
    KENGA("Кенга"),
    CHRISTOPHER_ROBIN("Кристофер Робин"),
    PIGLET("Пятачок"),
    LITTLE_ROO("Крошка Ру"),
    ;
    private OrganizedContainer container;
    private final String name;
    private final ArrayList<Eatable> favoriteFood;
    private ArrayList<Personage> inConversationWith = new ArrayList<>();
    private String conversationContent;

    Personage(String name, ArrayList<Eatable> favoriteFood) {
        this.name = name;
        this.favoriteFood = favoriteFood;
    }

    Personage(String name) {
        this(name, new ArrayList<>());
    }

    public class BodyParts{
        String name;
        BodyParts(String name){
            this.name = name;
        }
        public void stuckInJar(){
            System.out.println(Personage.this + " сунул " + this + " в банку");
        }
        @Override
        public String toString(){
            return this.name;
        }
    }


    @Override
    public Container getContainer() {
        return this.container;
    }

    @Override
    public void setContainer(Container container) {
        this.container = (OrganizedContainer) container;
    }

    void standNextTo(HasLocation hasLocation) {
        if (this.container != null)
            this.container.remove(this);
        OrganizedContainer container = (OrganizedContainer) hasLocation.getContainer();
        container.putNextTo(hasLocation, this);
        this.endConversation();
    }

    boolean isLove(Eatable eatable) {
        return this.favoriteFood.contains(eatable);
    }

    ArrayList<Eatable> getUnfavoriteFood() {
        ArrayList<Eatable> eatables = Eatable.getAllEatables();
        eatables.removeIf(this::isLove);
        return eatables;
    }

    public void setConversation(ArrayList<Personage> personages, String conversationContent) {
        this.inConversationWith = personages;
        this.conversationContent = conversationContent;
        for (Personage personage : personages) {
            ArrayList<Personage> personages1 = new ArrayList<>(personages);
            personages1.remove(personage);
            personages1.add(this);
            personage.inConversationWith = personages1;
            personage.conversationContent = conversationContent;
        }
    }

    public void setConversation(Personage personage, String conversationContent) throws PersonageIsBusyException {
        if(personage.getConversation().contains(this) && this.getConversation().contains(personage)) return;
        if (!personage.getConversation().isEmpty()) throw new PersonageIsBusyException(personage);
        this.inConversationWith = new ArrayList<>(List.of(new Personage[]{personage}));
        this.conversationContent = conversationContent;
        personage.inConversationWith = new ArrayList<>(List.of(new Personage[]{this}));
        personage.conversationContent = conversationContent;
    }

    public ArrayList<Personage> getConversation() {
        return this.inConversationWith;
    }

    public String getConversationContent() {
        return this.conversationContent;
    }

    private void deleteFromConversation(Personage personage) {
        this.inConversationWith.remove(personage);
        if (this.inConversationWith.isEmpty()) this.conversationContent = null;
    }

    private void endConversation() {
        ArrayList<Personage> personages = this.getConversation();
        this.inConversationWith.clear();
        this.conversationContent = null;
        for (Personage personage : personages) deleteFromConversation(this);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
