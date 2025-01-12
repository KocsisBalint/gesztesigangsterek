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

      
        Location forest = new Location("Oroszlány Petőfi", "Legveszélyesebb hely oroszlányban, tele titkokkal és veszélyekkel.");
        forest.addEnemy(new Enemy("Csikós Armando"));
        forest.addEnemy(new Enemy("Száva Vince"));

        Location ruins = new Location("Várgesztes-Oroszlány Hév", "Várgesztes, ahol a múlt titkai rejtőznek.");
        ruins.addEnemy(new Enemy("Jancsika"));
        ruins.addEnemy(new Enemy("Sodi"));

        Location[] locations = {forest, ruins};

     
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
            for (int i = 0; i < locations.length; i++) {
                System.out.println((i + 1) + ". " + locations[i].name);
            }
            System.out.println((locations.length + 1) + ". Kilépés");
            System.out.println((locations.length + 2) + ". Hívom a rendőrséget!"); 
            System.out.print("Válassz egy helyszínt: ");
            choice = scanner.nextInt();
            if (choice >= 1 && choice <= locations.length) {
                Location currentLocation = locations[choice - 1];
                currentLocation.display();
                if (!currentLocation.enemies.isEmpty()) {
                
                    System.out.println("\n1. Próbáld megtalálni az ellenség búvóhelyét a titkos levéllel.");
                    System.out.println("2. Támadj az ellenségre itt és most.");
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
                                System.out.println(player.name + " menekül a csatából!");
                                break;
                            } else {
                                System.out.println("Érvénytelen választás, próbáld újra.");
                            }
                        }
                        if (player.isAlive() && !enemy.isAlive()) {
                            player.gainExperience(50);
                            currentLocation.enemies.remove(enemy);
                            System.out.println(enemy.name + " legyőzve! Továbbhaladhatsz.");
                        }
                        if (!player.isAlive()) {
                            System.out.println(player.name + " meghalt! Játék vége.");
                            gameRunning = false;
                        }
                    } else {
                        System.out.println("Érvénytelen választás, próbáld újra.");
                    }
                }
            } else if (choice == locations.length + 1) {
                System.out.println("Köszönjük a játékot! Viszlát!");
                gameRunning = false;
            } else if (choice == locations.length + 2) {
               
                System.out.println("\nA rendőrséget hívod...");
                if (random.nextBoolean()) {
                    System.out.println("A rendőrség elkapja az ellenségeket! Kalandod sikeres volt! 5 év fegyházra itélik az ellenséget!");
                    gameRunning = false;
                } else {
                    System.out.println("Sajnos a rendőrség nem érkezik időben! Az ellenség bosszút állt rajtad!");
                    System.out.println(player.name + " meghalt! Játék vége.");
                    gameRunning = false;
                }
            } else {
                System.out.println("Érvénytelen választás, próbáld újra.");
            }
        }
        scanner.close();
    }
}