package lab4;

public class IncorrectContainerException extends RuntimeException{
    Container container;
    HasLocation hasLocation;
    IncorrectContainerException(Container container, HasLocation hasLocation){
        this.container = container;
        this.hasLocation = hasLocation;
    }

    @Override
    public String toString(){
        return this.hasLocation + " не может находиться в " + this.container;
    }
}
