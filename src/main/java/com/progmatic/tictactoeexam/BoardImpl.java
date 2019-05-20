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
    
    private final Cell[][] board;

    private BoardImpl(int size) {
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
        
        if (p == PlayerType.EMPTY) {
            return false;
        }
        
        if (board[0][0].getCellsPlayer() == p
                    && board[1][1].getCellsPlayer() == p
                    && board[2][2].getCellsPlayer() == p
                || board[0][2].getCellsPlayer() == p
                    && board[1][1].getCellsPlayer() == p
                    && board[2][0].getCellsPlayer() == p) {
                return true;
            }
        
        for (int i = 0; i < DEAFAULT_SIZE; i++) {
            if (board[i][0].getCellsPlayer() == p
                    && board[i][1].getCellsPlayer() == p
                    && board[i][2].getCellsPlayer() == p
                || board[0][i].getCellsPlayer() == p
                    && board[1][i].getCellsPlayer() == p
                    && board[2][i].getCellsPlayer() == p) {
                return true;
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
