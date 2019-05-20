/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.tictactoeexam;

import com.progmatic.tictactoeexam.enums.PlayerType;
import com.progmatic.tictactoeexam.exceptions.CellException;
import com.progmatic.tictactoeexam.interfaces.Board;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TLestyan
 */
public class BoardImpl implements Board{
    
    private static final int DEAFAULT_SIZE = 3;
    private static final int MAX_COUNT_FOR_WIN = 5;
    
    private final Cell[][] board;

    public BoardImpl(int size) {
        board = new Cell[size][size];
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = new Cell(row, col);
            }
        }
    }

    public BoardImpl() {
        this(DEAFAULT_SIZE);
    }
    
    @Override
    public PlayerType getCell(int rowIdx, int colIdx) throws CellException {
        
        if (rowIdx >= board.length 
                || rowIdx < 0
                || colIdx >= board[0].length
                || colIdx < 0) {
            throw new CellException(rowIdx, colIdx, "Cell is not on the board");
        }
        
        return board[rowIdx][colIdx].getCellsPlayer();
    }

    @Override
    public void put(Cell cell) throws CellException {
        if (getCell( cell.getRow(), cell.getCol()) != PlayerType.EMPTY) {
            throw new CellException(cell.getRow(), cell.getCol(), "Cell is not empty");
        }
        
        board[cell.getRow()][cell.getCol()].setCellsPlayer( cell.getCellsPlayer() );
    }

    @Override
    public boolean hasWon(PlayerType p) {
        
        int winFromCount = Math.min( Math.min( board.length, board[0].length ) , MAX_COUNT_FOR_WIN );
        
        // row check
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                int countP = 0;
                if (board[row][col].getCellsPlayer() == p) {
                    countP++;
                    
                    if (countP == winFromCount) {
                        return true;
                    }
                    
                } else {
                    countP = 0;
                }
            }
        }
        
        // collumn check
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length; row++) {
                int countP = 0;
                if (board[row][col].getCellsPlayer() == p) {
                    countP++;
                    
                    if (countP == winFromCount) {
                        return true;
                    }
                    
                } else {
                    countP = 0;
                }
            }
        }
        
        // diagonal check
        for (int row = 0; row < board.length - winFromCount; row++) {
            for (int col = 0; col < board[row].length - winFromCount; col++) {
                int countP = 0;
                int countP2 = 0;
                
                for (int i = 0; i < winFromCount; i++) {
                    if (board[row + i][col + i].getCellsPlayer() == p) {
                        countP++;
                    } 
                    
                    if (board[row + (winFromCount - 1) - i][col + i].getCellsPlayer() == p) {
                        countP2++;
                    } 
                }
                
                if (countP == winFromCount
                        || countP2 == winFromCount) {
                    return true;
                }
            }
        }
        
        return false;
    }

    @Override
    public List<Cell> emptyCells() {
        List<Cell> emptyCells = new ArrayList<>();
        
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col].getCellsPlayer() == PlayerType.EMPTY) {
                    emptyCells.add( new Cell(row, col) );
                }
            }
        }
        
        return emptyCells;
    }
    
    
    
}
