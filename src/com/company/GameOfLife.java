package com.company;

import java.util.HashSet;
import java.util.Set;

public class GameOfLife {

    Set<Cell> world = new HashSet<>();

    int[][] possibleDirections = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1},
            };

    public GameOfLife() {

        world.add(new Cell(-10, -10));
        world.add(new Cell(-10, -11));
        world.add(new Cell(-10, -12));

        world.add(new Cell(0, 0));
        world.add(new Cell(0, 2));
        world.add(new Cell(1, 1));
        world.add(new Cell(1, 2));
        world.add(new Cell(2, 1));

        world.add(new Cell(10, 12));
        world.add(new Cell(11, 10));
        world.add(new Cell(11, 12));
        world.add(new Cell(12, 11));
        world.add(new Cell(12, 12));

        while (true) {
            printWorld();
            Set<Cell> temporaryWorld = new HashSet<>();
            for (Cell cell : world) {
                int neighbours = countNeighbours(cell);

                if (neighbours == 2 || neighbours == 3) {
                    temporaryWorld.add(cell);
                }

                checkDeadNeighbours(temporaryWorld, cell);
            }

            world = temporaryWorld;

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printWorld() {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Cell cell : world) {
            if (cell.getX() < minX) {
                minX = cell.getX();
            }
            if (cell.getY() < minY) {
                minY = cell.getY();
            }
            if (cell.getX() > maxX) {
                maxX = cell.getX();
            }
            if (cell.getY() > maxY) {
                maxY = cell.getY();
            }
        }

        System.out.println("-------------------------------------");
        for (int x = minX - 1; x <= maxX; x++) {
            for (int y = minY - 1; y <= maxY; y++) {
                if (world.contains(new Cell(x, y))) {
                    System.out.print('o');
                } else {
                    System.out.print('.');
                }
            }
            System.out.println();
        }

    }

    private void checkDeadNeighbours(Set<Cell> temporaryWorld, Cell cell) {
        for (int[] possibleDirection : possibleDirections) {
            int currentXToCheck = cell.getX() + possibleDirection[0];
            int currentYToCheck = cell.getY() + possibleDirection[1];
            if (!world.contains(new Cell(currentXToCheck, currentYToCheck))) {
                int neighboursOfDeadCell = countNeighbours(new Cell(currentXToCheck, currentYToCheck));
                if (neighboursOfDeadCell == 3) {
                    temporaryWorld.add(new Cell(currentXToCheck, currentYToCheck));
                }
            }
        }
    }

    private int countNeighbours(Cell cell) {
        int counter = 0;
        for (int[] possibleDirection : possibleDirections) {
            int currentXToCheck = cell.getX() + possibleDirection[0];
            int currentYToCheck = cell.getY() + possibleDirection[1];

            if (world.contains(new Cell(currentXToCheck, currentYToCheck))) {
                counter++;
            }

        }
        return counter;
    }

}
