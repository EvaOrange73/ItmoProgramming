package lab4;

public class PersonageIsBusyException extends Exception{
    Personage personage;
    PersonageIsBusyException(Personage personage){
        this.personage = personage;
    }
    @Override
    public String toString(){
        return "Но " + personage + " занят, он говорит с " + personage.getConversation() + " : " + personage.getConversationContent();
    }
}
