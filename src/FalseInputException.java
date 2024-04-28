/** Исключение, выбрасываемое при обнаружении недопустимых значений. */
public class FalseInputException extends Exception {
  public FalseInputException(String message) {
    super(message);
  }
}
