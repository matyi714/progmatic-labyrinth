package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.CellType;
import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.exceptions.CellException;
import com.progmatic.labyrinthproject.exceptions.InvalidMoveException;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author pappgergely
 */
public class LabyrinthImpl implements Labyrinth {

    public CellType[][] lab;
    private int width;
    private int height;
    private Coordinate playerPosition;
    private Coordinate endPosition;

    public LabyrinthImpl() {
        width = -1;
        height = -1;
        playerPosition = new Coordinate(-1, -1);
        loadLabyrinthFile("labyrinth1");
    }

    @Override
    public void loadLabyrinthFile(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            width = Integer.parseInt(sc.nextLine());
            height = Integer.parseInt(sc.nextLine());
            lab = new CellType[height][width];    
            for (int hh = 0; hh < height; hh++) {
                String line = sc.nextLine();
                for (int ww = 0; ww < width; ww++) {
                    switch (line.charAt(ww)) {
                        case 'W':
                            lab[hh][ww] = CellType.WALL;
                            break;
                        case 'E':
                            lab[hh][ww] = CellType.END;
                            endPosition = new Coordinate(ww, hh);
                            break;
                        case 'S':
                            lab[hh][ww] = CellType.START;
                            playerPosition = new Coordinate(ww, hh);
                            break;
                        case ' ':
                            lab[hh][ww] = CellType.EMPTY;
                            break;
                        default:
                            lab[hh][ww] = CellType.WALL;
                            break;
                    }
                }
            }
        } catch (FileNotFoundException | NumberFormatException ex) {
            System.out.println(ex.toString());
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public CellType getCellType(Coordinate c) throws CellException {
        if ((c.getCol() > lab.length) || (c.getRow() > lab[0].length)) {
            throw new CellException(c, "Túl messzire mentél");
        } else {
            CellType ct = lab[c.getCol()][c.getRow()];
            return ct;
        }
    }

    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setCellType(Coordinate c, CellType type) throws CellException {
        if (c.getRow()>lab.length || c.getCol()>lab[0].length) {
            throw  new CellException(c, "Próbáld újra");
        }
        if (type.equals(CellType.START)) {
            playerPosition = c;
        }
        lab[c.getRow()][c.getCol()] = type;
    }

    @Override
    public Coordinate getPlayerPosition() {
        return this.playerPosition;
    }

    @Override
    public boolean hasPlayerFinished() {
        if (endPosition.getCol()==playerPosition.getCol() && endPosition.getRow()== playerPosition.getRow()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Direction> possibleMoves() {
        List<Direction> posDir = new ArrayList<>();
        int y = playerPosition.getRow();
        int x = playerPosition.getCol();
        int xlength = lab[0].length - 1;
        int ylength = lab.length - 1;
        if (x == 0) {
            if (y == 0) {
                if (!lab[1][0].equals(CellType.WALL)) {
                    posDir.add(Direction.SOUTH);
                }
                if (!lab[0][1].equals(CellType.WALL)) {
                    posDir.add(Direction.EAST);
                }
                return posDir;
            }
            if (y == ylength) {
                if (!lab[ylength][1].equals(CellType.WALL)) {
                    posDir.add(Direction.EAST);
                }
                if (!lab[ylength - 1][0].equals(CellType.WALL)) {
                    posDir.add(Direction.NORTH);
                }
                return posDir;
            }
            if (!lab[y + 1][0].equals(CellType.WALL)) {
                posDir.add(Direction.SOUTH);
            }
            if (!lab[y - 1][0].equals(CellType.WALL)) {
                posDir.add(Direction.NORTH);
            }
            if (!lab[y][1].equals(CellType.WALL)) {
                posDir.add(Direction.EAST);
            }
            return posDir;
        }
        if (x == xlength) {
            if (y == 0) {
                if (!lab[1][xlength].equals(CellType.WALL)) {
                    posDir.add(Direction.SOUTH);
                }
                if (!lab[0][xlength - 1].equals(CellType.WALL)) {
                    posDir.add(Direction.WEST);
                }
                return posDir;
            }
            if (y == ylength) {
                if (!lab[ylength][xlength - 1].equals(CellType.WALL)) {
                    posDir.add(Direction.WEST);
                }
                if (!lab[ylength - 1][xlength].equals(CellType.WALL)) {
                    posDir.add(Direction.NORTH);
                }
                return posDir;
            }
            if (!lab[y + 1][xlength].equals(CellType.WALL)) {
                posDir.add(Direction.SOUTH);
            }
            if (!lab[y - 1][xlength].equals(CellType.WALL)) {
                posDir.add(Direction.NORTH);
            }
            if (!lab[y][xlength - 1].equals(CellType.WALL)) {
                posDir.add(Direction.EAST);
            }
            return posDir;
        }
        if (y == 0) {
            if (!lab[0][x + 1].equals(CellType.WALL)) {
                posDir.add(Direction.EAST);
            }
            if (!lab[0][x - 1].equals(CellType.WALL)) {
                posDir.add(Direction.WEST);
            }
            if (!lab[1][x].equals(CellType.WALL)) {
                posDir.add(Direction.SOUTH);
            }
            return posDir;
        }
        if (y == ylength) {
            if (!lab[ylength][x + 1].equals(CellType.WALL)) {
                posDir.add(Direction.EAST);
            }
            if (!lab[ylength][x - 1].equals(CellType.WALL)) {
                posDir.add(Direction.WEST);
            }
            if (!lab[ylength - 1][x].equals(CellType.WALL)) {
                posDir.add(Direction.NORTH);
            }
            return posDir;
        }
        if (true) {
            if (!lab[y][x + 1].equals(CellType.WALL)) {
                posDir.add(Direction.EAST);
            }
            if (!lab[y][x - 1].equals(CellType.WALL)) {
                posDir.add(Direction.WEST);
            }
            if (!lab[y + 1][x].equals(CellType.WALL)) {
                posDir.add(Direction.SOUTH);
            }
            if (!lab[y - 1][x].equals(CellType.WALL)) {
                posDir.add(Direction.NORTH);
            }
        }
        return posDir;
    }

    @Override
    public void movePlayer(Direction direction) throws InvalidMoveException {
        List<Direction> l = possibleMoves();
        if (!l.contains(direction)) {
            throw new InvalidMoveException();
        }
        switch (direction) {
            case EAST:
                playerPosition.setCol(1);
                break;
            case WEST:
                playerPosition.setCol(-1);
                break;
            case NORTH:
                playerPosition.setRow(-1);
                break;
            default:
                playerPosition.setRow(1);
                break;
        }
    }

}
