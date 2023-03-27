package lab4;

/**
 * Интерфейс для всего, что может содержать другие объекты внутри себя: комната, буфет, банка
 */
public interface Container {
    void put(HasLocation hasLocation) throws IncorrectContainerException;

    void remove(HasLocation hasLocation);
}
