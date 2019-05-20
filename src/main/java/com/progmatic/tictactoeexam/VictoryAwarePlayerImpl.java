/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.tictactoeexam;

import com.progmatic.tictactoeexam.enums.PlayerType;
import com.progmatic.tictactoeexam.exceptions.CellException;
import com.progmatic.tictactoeexam.interfaces.AbstractPlayer;
import com.progmatic.tictactoeexam.interfaces.Board;
import java.util.List;

/**
 * Works only in 3x3 board!!!
 * 
 * @author TLestyan
 */
public class VictoryAwarePlayerImpl extends AbstractPlayer{
    
    public VictoryAwarePlayerImpl(PlayerType p) {
        super(p);
    }
    
    @Override
    public Cell nextMove(Board b) {
        
        List<Cell> emptyCells = b.emptyCells();
        
        if (emptyCells.isEmpty()) {
            return null;
        }
        
        for (Cell emptyCell : emptyCells) {
            
            for (int i = -2; i < 1; i++) {
                
                // in Row
                try {
                    int firstI = i == -1 
                            ? i++
                            : i;

                    if (b.getCell( emptyCell.getRow() + firstI, emptyCell.getCol()) == myType
                            && b.getCell( emptyCell.getRow() + (i + 1), emptyCell.getCol()) == myType) {
                        
                        return new Cell( emptyCell.getRow(), emptyCell.getCol(), myType);
                    }
                    
                } catch (CellException e) {}
                
                // in Column
                try {
                    int firstI = i == 0 
                            ? -1
                            : i;

                    if (b.getCell( emptyCell.getRow(), emptyCell.getCol() + firstI) == myType
                            && b.getCell( emptyCell.getRow(), emptyCell.getCol() + (i + 1)) == myType) {
                        
                        return new Cell( emptyCell.getRow(), emptyCell.getCol(), myType);
                    }
                    
                } catch (CellException e) {}
                
                // in Diagonal
                try {
                    int firstI = i == 0 
                            ? -1
                            : i;

                    if (b.getCell( emptyCell.getRow() + firstI, emptyCell.getCol() + firstI) == myType
                            && b.getCell( emptyCell.getRow() + (i + 1), emptyCell.getCol() + (i + 1)) == myType) {
                        
                        return new Cell( emptyCell.getRow(), emptyCell.getCol(), myType);
                    }
                    
                } catch (CellException e) {}
                
                // in Aganist Diagonal
                try {
                    int firstI = i == 0 
                            ? -1
                            : i;

                    if (b.getCell( emptyCell.getRow() - firstI, emptyCell.getCol() + firstI) == myType
                            && b.getCell( emptyCell.getRow() - (i + 1), emptyCell.getCol() + (i + 1)) == myType) {
                        
                        return new Cell( emptyCell.getRow(), emptyCell.getCol(), myType);
                    }
                    
                } catch (CellException e) {}
                
            }
            
        }
        
        return new Cell( emptyCells.get(0).getRow(), emptyCells.get(0).getCol(), myType);
    }
    
}
