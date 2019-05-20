/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.progmatic.tictactoeexam;

import com.progmatic.tictactoeexam.enums.PlayerType;
import com.progmatic.tictactoeexam.interfaces.AbstractPlayer;
import com.progmatic.tictactoeexam.interfaces.Board;
import java.util.List;

/**
 *
 * @author TLestyan
 */
public class SimplePlayerImpl extends AbstractPlayer{

    public SimplePlayerImpl(PlayerType p) {
        super(p);
    }
    
    @Override
    public Cell nextMove(Board b) {
        List<Cell> emptyCells = b.emptyCells();
        
        if (emptyCells.isEmpty()) {
            return null;
        }
        
        return new Cell( emptyCells.get(0).getRow(), emptyCells.get(0).getCol(), myType);
    }
    
    
    
}
