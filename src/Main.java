import java.util.Random;
import java.util.Scanner;

/**
 * @author 1ommy
 * @version 14.10.2023
 */
public class Main {
    static Random rand = new Random();
    static int fieldSize = 0;
    static int countUserShips = 0;
    static int countComputerShips = 0;
    static Players activePlayer;
    static Players winner = Players.INITIAL;
    static boolean isPlaying = true;


    public enum Cell {
        ALIVE_SHIP('Ж'),
        DEAD_SHIP('x'),
        MISS('0'),
        EMPTY('.');

        char value;

        Cell(char value) {
            this.value = value;
        }

        public char getValue() {
            return value;
        }
    }

    public enum Players {
        COMPUTER("Компьютер"),
        USER("Человек"),
        INITIAL("еще не знаем кто ходит первый");

        String value;

        Players(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static void initEmptyField(Cell[][] userField, Cell[][] computerField) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                userField[i][j] = Cell.EMPTY;
                computerField[i][j] = Cell.EMPTY;
            }
        }
    }

    public static void shoot(Cell[][] field, Players newPlayer) {

        int iShoot = rand.nextInt(fieldSize);
        int jShoot = rand.nextInt(fieldSize);

        if (field[iShoot][jShoot] != Cell.ALIVE_SHIP) {
            field[iShoot][jShoot] = Cell.MISS;
            activePlayer = newPlayer;
        } else if (field[iShoot][jShoot] == Cell.ALIVE_SHIP) {
            field[iShoot][jShoot] = Cell.DEAD_SHIP;

            if (newPlayer == Players.USER)
                countUserShips--;
            else
                countComputerShips--;
        }
    }

    public static void playGame(Cell[][] computerField, Cell[][] userField) {
        while (isPlaying) {
            System.out.println("Поле компьютера");

            printField(computerField);
            System.out.println();
            printField(userField);
            System.out.println();

            if (activePlayer == Players.COMPUTER) {
                shoot(userField, Players.USER);
            } else if (activePlayer == Players.USER) {
                shoot(computerField, Players.COMPUTER);
            }

            checkWinner();
        }
    }

    public static void checkWinner() {
        if (countUserShips == 0) {
            winner = Players.COMPUTER;
            isPlaying = false;
        }
        if (countComputerShips == 0) {
            winner = Players.USER;
            isPlaying = false;
        }
    }

    public static void printField(Cell[][] field) {
        for (int i = 0; i < fieldSize; i++) {
            for (int j = 0; j < fieldSize; j++) {
                if (field[i][j] == Cell.ALIVE_SHIP) {
                    System.out.print(field[i][j].getValue()); // -> Ж
                } else {
                    System.out.print(Cell.EMPTY.getValue());
                }
            }
            System.out.println();
        }
    }

    public static void setUpShipsOnTheMap(int countShips, Cell[][] field) {
        for (int i = 0; i < countShips; i++) {
            int iShip, jShip;

            do {
                iShip = rand.nextInt(fieldSize); // [0,fieldSize)
                jShip = rand.nextInt(fieldSize);
            } while (field[iShip][jShip] != Cell.EMPTY); // computerField[iShip][jShip] = Сell.ALIVE_SHIP,
            // тогда в ту ячейку где уже есть корабль мы его снова поставим, и мы расставим как минимум на 1 корабль
            // меньше чем countComputerShip

            field[iShip][jShip] = Cell.ALIVE_SHIP;
        }
    }

    public static void main(String[] args) {


        Cell[][] computerField, userField;

        System.out.println("Введите размер поля");
        Scanner scanner = new Scanner(System.in);
/*
 String weekDay = scanner.nextLine();
 switch (weekDay) {
 case "Пн" -> {
 System.out.println("1");
            }
 case "Вт" -> {
 System.out.println("2");
            }
 case "Ср" -> {
 System.out.println("3");
            }
 default -> {
 System.out.println("ты дебил ввел не то");
            }
        }
 for (int i = 1; i < 100; i++) {
 if (i % 2 == 0) {
 continue;
 } else if (i == 71) {
 break;
 } else {
 System.out.println(i);
            }
        }*/

        fieldSize = scanner.nextInt();
        countComputerShips = countUserShips = fieldSize;

        computerField = new Cell[fieldSize][fieldSize];
        userField = new Cell[fieldSize][fieldSize];

        initEmptyField(userField, computerField);


        /*setUpShipsOnTheMap(countComputerShips, computerField);
 setUpShipsOnTheMap(countUserShips, userField);
 if (rand.nextInt(10000) > 5000) { // 10, 6 - 10
 activePlayer = Players.USER;
        } else {
 activePlayer = Players.COMPUTER;
        }
 playGame(computerField, userField);
 System.out.println("Победил: " + winner.getValue());
    */
    }


}