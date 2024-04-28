/** Исключение, выбрасываемое при попытке обращения к пустому списку. */
public class EmptyListException extends Exception {
  public EmptyListException(String message) {
    super(message);
  }
}
