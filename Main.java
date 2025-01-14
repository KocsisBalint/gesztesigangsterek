import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Add meg a karaktered nevét: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);

        // Helyszínek létrehozása
        Location forest = new Location("Oroszlány Petőfi", "Legveszélyesebb hely Oroszlányban, tele titkokkal és veszélyekkel.");
        forest.addEnemy(new Enemy("Csikós Armando"));
        forest.addEnemy(new Enemy("Száva Vince"));

        Location ruins = new Location("Várgesztes-Oroszlány Hév", "Várgesztes, ahol a múlt titkai rejtőznek.");
        ruins.addEnemy(new Enemy("Jancsika"));
        ruins.addEnemy(new Enemy("Sodi"));

        Location cave = new Location("Sötét Barlang", "Egy sötét barlang, ahol a félelem és a veszély lakozik.");
        cave.addEnemy(new Enemy("Fekete Szellem"));
        cave.addEnemy(new Enemy("Kísértet"));

        Location[] locations = {forest, ruins, cave};

        // Segítő csatlakoztatása
        System.out.println("Szeretnél csatlakoztatni egy segítőt? A 'Titokzatos Harcos' hozzáadása 200 életerőt ad.");
        System.out.println("1. Igen, csatlakoztatom a Titokzatos Harcost.");
        System.out.println("2. Nem, nem szeretnék segítőt.");
        System.out.print("Válassz egy lehetőséget: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("A Titokzatos Harcos csatlakozott a csapatodhoz!");
            player.increaseHealth(200);
        } else {
            System.out.println("Nincs segítő a csapatban.");
        }

        System.out.println("\nA kaland során találtál egy titkos levelet, amely segíthet megtalálni az ellenség búvóhelyét.");
        System.out.println("A levél segíthet, hogy előbb rálelj a búvóhelyükre és legyőzd őket!");

        boolean gameRunning = true;
        while (gameRunning) {
            System.out.println("\nKalandjaid:");
            System.out.println("Válassz egy helyszínt:");
            for (int i = 0; i < locations.length; i++) {
                System.out.println((i + 1) + ". " + locations[i].name);
            }
            System.out.println((locations.length + 1) + ". Kilépés");

            System.out.print("Válassz egy lehetőséget: ");
            choice = scanner.nextInt();

            if (choice >= 1 && choice <= locations.length) {
                Location currentLocation = locations[choice - 1];
                currentLocation.display();
                if (!currentLocation.enemies.isEmpty()) {
                    System.out.println("\n1. Próbáld megtalálni az ellenség búvóhelyét a titkos levéllel.");
                    System.out.println("2. Támadj az ellenségre itt és most.");
                    System.out.println("3. Hívom a rendőrséget!");
                    System.out.print("Válassz egy lehetőséget: ");
                    int action = scanner.nextInt();
                    if (action == 1) {
                        System.out.println("A titkos levél segít megtalálni az ellenség búvóhelyét!");
                        System.out.println("Az ellenséget váratlanul lecsapod és legyőzöd őket!");
                        currentLocation.enemies.clear();
                        System.out.println("Játék vége: A búvóhely megtalálásával véget ért a kaland!");
                        gameRunning = false;
                    } else if (action == 2) {
                        Enemy enemy = currentLocation.enemies.get(random.nextInt(currentLocation.enemies.size()));
                        System.out.println("\nEgy " + enemy.name + " késsel és rozsdás karóval támad!");
                        while (player.isAlive() && enemy.isAlive()) {
                            System.out.println("1. Támadás");
                            System.out.println("2. Futás");
                            System.out.print("Válassz egy lehetőséget: ");
                            action = scanner.nextInt();
                            if (action == 1) {
                                player.attack(enemy);
                                if (enemy.isAlive()) {
                                    enemy.attack(player);
                                }
                            } else if (action == 2) {
                                System.out.println(player.name + " elmenekült a harcból!");
                                break;
                            }
                        }
                        if (!player.isAlive()) {
                            System.out.println("Sajnálom, " + player.name + ", legyőztek az ellenségek! A kaland véget ért.");
                            gameRunning = false;
                        } else if (!enemy.isAlive()) {
                            System.out.println("Győztél! " + enemy.name + " legyőzve!");
                            player.gainExperience(50);
                            currentLocation.enemies.remove(enemy);
                        }
                    } else if (action == 3) {
                        System.out.println("A rendőrség hívása folyamatban...");
                        if (random.nextBoolean()) {
                            System.out.println("A rendőrség megérkezett és segít neked a harcban!");
                            for (Enemy enemy : currentLocation.enemies) {
                                enemy.health -= 20; // A rendőrség 20 életerőt levon az ellenségektől
                                System.out.println("A rendőrség támadta " + enemy.name + "-t! Életereje: " + enemy.health);
                            }
                        } else {
                            System.out.println("Sajnálom, a rendőrség nem tudott megérkezni időben.");
                        }
                    }
                }
            } else {
                System.out.println("Kiléptél a játékból. Köszönjük, hogy játszottál!");
                gameRunning = false;
            }
        }
        scanner.close();
    }
}