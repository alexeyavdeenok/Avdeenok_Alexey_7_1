import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main { // Основной класс
  private static final Scanner input = new Scanner(System.in);
  private static ArrayList<Person> list = new ArrayList<>();

  public static void main(String[] args) {
    String fileName = "persons.txt";
    try {
      Person person1 = new Person("Иван", "мужской", 14, 167, 67);
      Person person2 = new Person("Антон", "мужской", 23, 175, 80);
      Person person3 = new Person("Евгения", "женский", 68, 164, 65);
      Person person4 = new Person("Петр", "мужской", 72, 174, 75);
      Person person5 = new Person("Андрей", "мужской", 44, 188, 150);
      Person person6 = new Person("Анна", "женский", 35, 166, 132);
      Person person7 = new Person("Артур", "мужской", 12, 155, 50);
      Person person8 = new Person("Алла", "женский", 27, 157, 45);
      Person person9 = new Person("Иван", "мужской", 14, 167, 67);
      list.addAll(
          List.of(person1, person2, person3, person4, person5, person6, person7, person8, person9));
    } catch (FalseInputException e) {
      System.out.println(e.getMessage());
    }

    while (true) {
      try {
        printMenu();
        int choice = input.nextInt();
        switch (choice) {
          case 1: // Созданте потока и вывод в консоль
            Stream<Person> listStream = list.stream();
            listStream.forEach(System.out::println);
            listStream.close();
            break;
          case 2:
            // Фильтрация массива по выбранному полю
            personFilter();
            break;
          case 3:
            // Удаление дубликатов из массива
            list = (ArrayList<Person>) list.stream().distinct().collect(Collectors.toList());
            list.forEach(System.out::println);
            System.out.println("Дубликаты удалены");
            break;
          case 4:
            // Вывод суммарного веса объектов массива
            sumWeight();
            break;
          case 5:
            // Группировка объектов по полу и вывод средних значений
            createGroup();
            break;
          case 6:
            // Загрузка данных из файла и добавление в массив
            try {
              List<Person> people = readPersonsFromFile(fileName);
              list.addAll(people);
              System.out.println("Данные добавлены в массив");
            } catch (EmptyListException e) {
              System.out.println(e.getMessage());
            }
            break;
          case 7:
            // Сохранение данных в файл
            writeDataToFile(fileName);
            break;
          case 8:
            // Завершение работы программы
            System.out.println("Программа завершена");
            System.exit(0);
          default:
            System.out.println("Ошибка ввода");
        }
      } catch (Exception e) {
        System.out.println("Ошибка ввода");
        input.next();
      }
    }
  }

  public static void printMenu() {
    System.out.println("Выберите действие: ");
    System.out.println("1. Создание потока из массива объектов и вывод их на экран");
    System.out.println("2. Фильтрация объектов, рост/возраст которых выше заданного");
    System.out.println("3. Изъятие из массива дубликатов");
    System.out.println("4. Суммарный вес всех объектов массива");
    System.out.println("5. Группировка объектов по полу");
    System.out.println("6. Загурзка данных из файла и добавление их в массив");
    System.out.println("7. Сохранение данных в файл");
    System.out.println("8. Завершение работы программы");
  }

  /** Метод для фильтрации по выбранному полю */
  public static void personFilter() {
    System.out.println("Выберите поле для фильтрации: ");
    System.out.println("1. Рост");
    System.out.println("2. Возраст");
    int choice = input.nextInt();
    Stream<Person> personList = list.stream();
    switch (choice) {
      case 1:
        System.out.println("Введите нижнюю границу роста");
        int value = input.nextInt();
        Stream<Person> filterPerson = personList.filter(p -> p.getHeight() >= value);
        filterPerson.forEach(System.out::println);
        break;
      case 2:
        System.out.println("Введите нижнюю границу возраста");
        int value2 = input.nextInt();
        Stream<Person> filterList = personList.filter(p -> p.getAge() >= value2);
        filterList.forEach(System.out::println);
        break;
      default:
        System.out.println("Ошибка ввода");
    }
  }

  /** Метод считает сумму всех товаров и выводит её в консоль */
  public static void sumWeight() {
    Stream<Person> listPerson = list.stream();
    OptionalDouble result = listPerson.mapToDouble(Person::getWeight).reduce(Double::sum);
    if (result.isPresent()) {
      System.out.println("Суммарный вес всех объектов: " + result.getAsDouble());
    } else {
      System.out.println("Объектов нет");
    }
    listPerson.close();
  }

  /** Метод группирует данные по полу и выводит средние значения */
  public static void createGroup() {
    Map<String, DoubleSummaryStatistics> summaryStatisticsByGender =
        list.stream()
            .collect(
                Collectors.groupingBy(
                    Person::getGender, Collectors.summarizingDouble(Person::getWeight)));

    summaryStatisticsByGender.forEach(
        (gender, stats) -> {
          System.out.println("Пол " + gender + ":");
          System.out.println("  Средний вес: " + (int) stats.getAverage());
          System.out.println("  Число объектов: " + stats.getCount());
          System.out.println("  Суммарный вес: " + stats.getSum());
          System.out.println("  Минимальный вес: " + stats.getMin());
          System.out.println("  Максимальный вес: " + stats.getMax());
        });
  }

  /**
   * Метод сохраняет данные в файл
   *
   * @param fileName путь к файлу
   */
  public static void writeDataToFile(String fileName) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
      for (Person person : list) {
        bw.write(person.toString());
        bw.newLine(); // Добавляем новую строку после каждого человека
      }
      System.out.println("Данные сохранены в файл: " + fileName);
    } catch (IOException e) {
      System.out.println("Файл не создан");
    }
  }

  /**
   * Метод получает данные из файла
   *
   * @param fileName название файла
   * @return список с объектами класса Person
   * @throws EmptyListException Исключение в случае пустого файла или его отсутствия
   */
  public static List<Person> readPersonsFromFile(String fileName) throws EmptyListException {
    List<Person> people = new ArrayList<>();

    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String line;
      String[] parts = new String[6];
      int index = 0;
      while ((line = br.readLine()) != null) {
        if (line.isEmpty()) {
          if (index == 6) {
            // Создаем объект Person из данных и добавляем его в список
            Person person = createPersonFromParts(parts);
            people.add(person);
          }
          index = 0;
        } else {
          parts[index++] = line;
        }
      }
      // Проверка для последнего человека в файле
      if (index == 6) {
        Person person = createPersonFromParts(parts);
        people.add(person);
      }
    } catch (IOException e) {
      throw new EmptyListException("Файл не создан\nСохраните данные для создания файла");
    }
    if (people.isEmpty()) {
      throw new EmptyListException("Фай пуст");
    }
    return people;
  }

  /**
   * Метод создает объект класса Person из строковых значений
   *
   * @param parts Строка с данными
   * @return объект класса Person
   */
  public static Person createPersonFromParts(String[] parts) {
    String name = parts[0].split(": ")[1].trim();
    String gender = parts[1].split(": ")[1].trim();
    int age = Integer.parseInt(parts[2].split(": ")[1].trim());
    double height = Double.parseDouble(parts[3].split(": ")[1].trim());
    double weight = Double.parseDouble(parts[4].split(": ")[1].trim());
    try {
      return new Person(name, gender, age, height, weight);
    } catch (FalseInputException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
