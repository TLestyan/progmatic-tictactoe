/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.progmatic.tictactoeexam.interfaces;

import com.progmatic.tictactoeexam.BoardImpl;
import com.progmatic.tictactoeexam.Cell;
import com.progmatic.tictactoeexam.SimplePlayerImpl;
import com.progmatic.tictactoeexam.exceptions.CellException;
import com.progmatic.tictactoeexam.enums.PlayerType;
import java.util.List;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author peti
 */
public class TicTacToeTest {

    public TicTacToeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Before
    public void setUp() {
    }

    @Test
    public void testGetCell() throws Exception {
        String[][] aBoard = {
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}};
        Board b = fromString(aBoard);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PlayerType cell = b.getCell(i, j);
                assertEquals(PlayerType.EMPTY, cell);
            }
        }
    }
    
    @Test
    public void testGetCell2() throws Exception {
        String[][] aBoard = {
            {"O", " ", " "},
            {" ", "X", " "},
            {" ", "O", " "}};
        Board b = fromString(aBoard);
        assertEquals(PlayerType.O, b.getCell(0, 0));
        assertEquals(PlayerType.EMPTY, b.getCell(0, 1));
        assertEquals(PlayerType.EMPTY, b.getCell(0, 2));
        assertEquals(PlayerType.EMPTY, b.getCell(1, 0));
        assertEquals(PlayerType.X, b.getCell(1, 1));
        assertEquals(PlayerType.EMPTY, b.getCell(1, 2));
        assertEquals(PlayerType.EMPTY, b.getCell(2, 0));
        assertEquals(PlayerType.O, b.getCell(2, 1));
        assertEquals(PlayerType.EMPTY, b.getCell(2, 2));    
    }
    
    @Test(expected = CellException.class)
    public void testGetFromNonExistentCell() throws Exception {
        Board b = getBoardImpl();
        b.getCell(0, 3); 
    }
    
    @Test(expected = CellException.class)
    public void testPutToWrongPlace() throws Exception {
        Board b = getBoardImpl();
        b.put(new Cell(3, 0)); 
    }
    
    @Test(expected = CellException.class)
    public void testPutAlreadyOccupied() throws Exception {
        String[][] aBoard = {
            {"O", " ", " "},
            {" ", "X", " "},
            {" ", "O", " "}};
        Board b = fromString(aBoard);
        b.put(new Cell(1, 1)); 
    }

    @Test
    public void testEmptyCells() throws CellException {
        String[][] aBoard = {
            {"O", " ", " "},
            {" ", "X", " "},
            {" ", "O", " "}};
        Board b = fromString(aBoard);
        List<Cell> emptyCells = b.emptyCells();
        assertEquals(6, emptyCells.size());
    }
    
    @Test
    public void testHasNoEmptyCells() throws CellException {
        String[][] aBoard = {
            {"O", "X", "O"},
            {"X", "X", "O"},
            {"O", "O", "X"}};
        Board b = fromString(aBoard);
        List<Cell> emptyCells = b.emptyCells();
        assertEquals(0, emptyCells.size());
    }
    
    @Test
    public void testHasWon() throws CellException {
        String[][] aBoard = {
            {"O", " ", " "},
            {" ", "X", " "},
            {" ", "X", " "}};
        Board b = fromString(aBoard);
        assertEquals(false, b.hasWon(PlayerType.X));
        assertEquals(false, b.hasWon(PlayerType.O));
    }
    
    @Test
    public void testHasWon2() throws CellException {
        String[][] aBoard = {
            {"O", "X", " "},
            {" ", "X", " "},
            {" ", "X", " "}};
        Board b = fromString(aBoard);
        assertEquals(true, b.hasWon(PlayerType.X));
        assertEquals(false, b.hasWon(PlayerType.O));
    }
    
    @Test
    public void testHasWon3() throws CellException {
        String[][] aBoard = {
            {"O", "X", " "},
            {" ", "O", " "},
            {" ", "X", "O"}};
        Board b = fromString(aBoard);
        assertEquals(false, b.hasWon(PlayerType.X));
        assertEquals(true, b.hasWon(PlayerType.O));
    }
    
    @Test
    public void testHasWon4() throws CellException {
        String[][] aBoard = {
            {" ", "O", "X"},
            {" ", "O", "O"},
            {"X", "X", "X"}};
        Board b = fromString(aBoard);
        assertEquals(true, b.hasWon(PlayerType.X));
        assertEquals(false, b.hasWon(PlayerType.O));
    }
    
    @Test
    public void testHasWon5() throws CellException {
        String[][] aBoard = {
            {"X", "X", "O"},
            {"X", "O", " "},
            {"O", "X", "O"}};
        Board b = fromString(aBoard);
        assertEquals(false, b.hasWon(PlayerType.X));
        assertEquals(true, b.hasWon(PlayerType.O));
    }
    
    @Test
    public void testSimplePlayer() throws CellException {
        String[][] aBoard = {
            {"X", "X", "O"},
            {"O", " ", "X"},
            {"X", "O", "X"}};
        Board b = fromString(aBoard);
        Player p = simplePlayerImpl(PlayerType.O);
        Cell nextMove = p.nextMove(b);
        assertEquals(1, nextMove.getCol());
        assertEquals(1, nextMove.getRow());
        assertEquals(PlayerType.O, nextMove.getCellsPlayer());
    }
    
    
    @Test
    public void testPlayerSDoNotModifyBoard() throws CellException {
        String[][] aBoard = {
            {"X", "X", "O"},
            {"O", " ", "X"},
            {"X", "O", "X"}};
        Board b = fromString(aBoard);
        Board b2 = fromString(aBoard);
        Player p = simplePlayerImpl(PlayerType.O);
        p.nextMove(b);
        assertSameBoard(b, b2);
        p = victoryAwarePlayerImpl(PlayerType.O);
        p.nextMove(b);
        assertSameBoard(b, b2);
    }
    
    @Test
    public void testSimplePlayerReturnsNullWhenNoEmtpyCellExists() throws CellException {
        String[][] aBoard = {
            {"X", "X", "O"},
            {"O", "O", "X"},
            {"X", "O", "X"}};
        Board b = fromString(aBoard);
        Player p = simplePlayerImpl(PlayerType.O);
        Cell nextMove = p.nextMove(b);
        assertNull(nextMove);
    }
    
    @Test
    public void testVictoryAwarePlayer() throws CellException {
        String[][] aBoard = {
            {"X", "X", "O"},
            {"X", " ", " "},
            {"O", "X", " "}};
        Board b = fromString(aBoard);
        Player p = victoryAwarePlayerImpl(PlayerType.O);
        Cell nextMove = p.nextMove(b);
        assertEquals(1, nextMove.getCol());
        assertEquals(1, nextMove.getRow());
        assertEquals(PlayerType.O, nextMove.getCellsPlayer());
    }
    
    @Test
    public void testVictoryAwarePlayer2() throws CellException {
        String[][] aBoard = {
            {" ", " ", "O"},
            {"X", " ", " "},
            {" ", "X", "O"}};
        Board b = fromString(aBoard);
        Player p = victoryAwarePlayerImpl(PlayerType.O);
        Cell nextMove = p.nextMove(b);
        assertEquals(2, nextMove.getCol());
        assertEquals(1, nextMove.getRow());
        assertEquals(PlayerType.O, nextMove.getCellsPlayer());
    }

    
    private Board fromString(String[][] strArr) throws CellException {
        Board ret = getBoardImpl();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PlayerType cellState;
                String str = strArr[i][j];
                switch (str) {
                    case "X":
                        cellState = PlayerType.X;
                        break;
                    case "O":
                        cellState = PlayerType.O;
                        break;
                    default:
                        cellState = PlayerType.EMPTY;
                        break;
                }

                Cell cell = new Cell(i, j, cellState);
                ret.put(cell);
            }

        }
        return ret;
    }
    
    private void assertSameBoard(Board b1, Board b2) throws CellException{
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                PlayerType cell1 = b1.getCell(i, j);
                PlayerType cell2 = b2.getCell(i, j);
                assertEquals(cell1, cell2);
            }
        }
    }
    
    //TODO
    private Board getBoardImpl(){
        return new BoardImpl();
    }
    
    //TODO
    private Player simplePlayerImpl(PlayerType p){
        return new SimplePlayerImpl(p);
    }
    
    //TODO
    private Player victoryAwarePlayerImpl(PlayerType p){
        return null;
    }

}
