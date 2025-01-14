import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class Character {
    String name;
    int health;
    int attackPower;
    int experience;

    Character(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.experience = 0;
    }

    void attack(Character enemy) {
        System.out.println(name + " megtámadta " + enemy.name + "-t!");
        enemy.health -= attackPower;
        System.out.println(enemy.name + " életereje: " + enemy.health);
    }

    boolean isAlive() {
        return health > 0;
    }

    void gainExperience(int amount) {
        experience += amount;
        System.out.println(name + " tapasztalatot szerzett! Összes tapasztalat: " + experience);
        if (experience >= 100) {
            levelUp();
        }
    }

    void levelUp() {
        health += 20;
        attackPower += 5;
        experience = 0;
        System.out.println(name + " szintet lépett! Új életerő: " + health + ", új támadási erő: " + attackPower);
    }

    void increaseHealth(int amount) {
        health += amount;
        System.out.println(name + " új életerőt nyert! Új életerő: " + health);
    }
}

class Player extends Character {
    Player(String name) {
        super(name, 100, 10);
    }
}

class Enemy extends Character {
    Enemy(String name) {
        super(name, 50, 30);
    }
}

class Location {
    String name;
    String description;
    List<Enemy> enemies;

    Location(String name, String description) {
        this.name = name;
        this.description = description;
        enemies = new ArrayList<>();
    }

    void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    void display() {
        System.out.println("Helyszín: " + name);
        System.out.println(description);
        if (enemies.isEmpty()) {
            System.out.println("Nincsenek ellenségek a közelben.");
        } else {
            System.out.println("Ellenségek a közelben:");
            for (Enemy enemy : enemies) {
                System.out.println("- " + enemy.name + " (életerő: " + enemy.health + ")");
            }
        }
    }
}