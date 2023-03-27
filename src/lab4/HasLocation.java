package lab4;

/**
 * Интерфейс для всех объектов, которые могут располагаться где-то в пространстве: персонажи, буфет, еда и т.д.
 */
public interface HasLocation {
    Container getContainer();

    void setContainer(Container container) throws IncorrectContainerException;
}
