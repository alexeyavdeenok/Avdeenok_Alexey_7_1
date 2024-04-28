import java.util.Objects;

/** Представляет собой человека с атрибутами, такими как имя, пол, возраст, рост, вес. */
public class Person {
  private String name;
  private String gender;
  private int age = -1;
  private double height;
  private double weight;
  private boolean retiree;

  /** Конструктор по умолчанию, инициализирующий человека значениями по умолчанию. */
  public Person() {
    try {
      setName("None");
      setGender("None");
      setAge(0);
      setHeight(0);
      setWeight(0);
      setRetiree();
    } catch (FalseInputException e) { // Подавление исключения
    }
  }

  /**
   * Параметризованный конструктор, инициализирующий человека заданными атрибутами.
   *
   * @param name Имя человека.
   * @param gender Пол человека (может быть "мужской", "женский" или "none").
   * @param age Возраст человека.
   * @param height Рост человека (в метрах).
   * @param weight Вес человека (в килограммах).
   * @throws FalseInputException Если какой-либо из входных параметров недопустим.
   */
  public Person(String name, String gender, int age, double height, double weight)
      throws FalseInputException {
    try {
      setName(name);
      setGender(gender);
      setAge(age);
      setHeight(height);
      setWeight(weight);
      setRetiree();
    } catch (FalseInputException e) {
      System.out.println(e.getMessage());
      throw new FalseInputException("Объект не создан"); // Повторное генерирование исключения
    }
  }

  /**
   * Возвращает строковое представление объекта человека.
   *
   * @return Строка, содержащая атрибуты человека.
   */
  @Override
  public String toString() {
    return "Имя: "
        + this.name
        + "\nПол: "
        + this.gender
        + "\nВозраст: "
        + this.age
        + "\nРост: "
        + this.height * 100
        + "\nВес: "
        + this.weight
        + "\nПенсионер: "
        + this.retiree
        + "\n";
  }

  /** Устанавливает статус пенсионера на основе возраста и пола. */
  public void setRetiree() {
    if (Objects.equals(this.gender, "мужской") && this.age > 62) {
      this.retiree = true;
    } else this.retiree = Objects.equals(this.gender, "женский") && this.age > 57;
  }

  /**
   * Возвращает статус пенсионера человека.
   *
   * @return True, если человек пенсионер, иначе false.
   */
  public boolean getRetire() {
    return this.retiree;
  }

  /**
   * Возвращает имя человека.
   *
   * @return Имя человека.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Устанавливает имя человека.
   *
   * @param name Имя для установки.
   * @throws FalseInputException Если предоставленное имя пусто.
   */
  public void setName(String name) throws FalseInputException {
    if (name.isEmpty()) {
      throw new FalseInputException("Имя не введено");
    } else {
      this.name = name;
    }
  }

  /**
   * Возвращает пол человека.
   *
   * @return Пол человека.
   */
  public String getGender() {
    return this.gender;
  }

  /**
   * Устанавливает пол человека.
   *
   * @param gender Пол для установки.
   * @throws FalseInputException Если предоставленный пол недопустим.
   */
  public void setGender(String gender) throws FalseInputException {
    String gendercheck = gender.toLowerCase();
    switch (gendercheck) {
      case "мужской":
        this.gender = gender;
        break;
      case "женский":
        this.gender = gender;
        break;
      case "none":
        this.gender = gender;
        break;
      default:
        throw new FalseInputException("Введен недопустимый пол");
    }
  }

  /**
   * Возвращает возраст человека.
   *
   * @return Возраст человека.
   */
  public int getAge() {
    return this.age;
  }

  /**
   * Устанавливает возраст человека.
   *
   * @param age Возраст для установки.
   * @throws FalseInputException Если предоставленный возраст недопустим.
   */
  public void setAge(int age) throws FalseInputException {
    try {
      assert age >= 0 && age < 150 : "Неверный возраст"; // Утверждение о недопустимом возрасте
      this.age = age;
    } catch (AssertionError e) {
      throw new FalseInputException(e.getMessage());
    }
  }

  /**
   * Возвращает рост человека.
   *
   * @return Рост человека.
   */
  public double getHeight() {
    return this.height * 100;
  }

  /**
   * Устанавливает рост человека.
   *
   * @param height Рост для установки (в метрах).
   * @throws FalseInputException Если предоставленный рост недопустим.
   */
  public void setHeight(double height) throws FalseInputException {
    if (height >= 36 && height <= 251 || height == 0) {
      this.height = height / 100;
    } else {
      throw new FalseInputException("Введен недопустимый рост");
    }
  }

  /**
   * Возвращает вес человека.
   *
   * @return Вес человека.
   */
  public double getWeight() {
    return this.weight;
  }

  /**
   * Устанавливает вес человека.
   *
   * @param weight Вес для установки (в килограммах).
   * @throws FalseInputException Если предоставленный вес недопустим.
   */
  public void setWeight(double weight) throws FalseInputException {
    if (weight >= 3.0 && weight <= 300 || weight == 0) {
      this.weight = weight;
    } else {
      throw new FalseInputException("Недопустимый вес");
    }
  }

  /**
   * Вычисляет и возвращает категорию индекса массы тела человека.
   *
   * @return Категория индекса массы тела.
   */
  public String indexMas() {
    if (this.height == 0) {
      return "Индекс массы тела вычислить нельзя";
    }
    double masIndex = this.weight / (this.height * this.height);
    if (masIndex < 16.5) {
      return "Большой недовес";
    } else if (masIndex >= 16.5 && masIndex < 18.5) {
      return "Недовес";
    } else if (masIndex >= 18.5 && masIndex < 25.0) {
      return "Нормальный вес";
    } else if (masIndex >= 25.0 && masIndex < 30.0) {
      return "Избыточный вес";
    } else if (masIndex >= 30 && masIndex < 35) {
      return "Ожирение";
    } else if (masIndex >= 35 && masIndex < 40) {
      return "Сильное ожирение";
    } else {
      return "Супер ожирение";
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Person person = (Person) o;
    return name.equals(person.name)
        && age == person.age
        && gender.equals(person.gender)
        && height == person.height
        && weight == person.weight;
  }

  @Override
  public int hashCode() {
    int result = name == null ? 0 : name.hashCode();
    result = result * 31 + age + (int) height + (int) weight;
    return result;
  }
}
