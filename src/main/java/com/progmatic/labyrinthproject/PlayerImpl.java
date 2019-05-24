
package com.progmatic.labyrinthproject;

import com.progmatic.labyrinthproject.enums.Direction;
import com.progmatic.labyrinthproject.interfaces.Labyrinth;
import com.progmatic.labyrinthproject.interfaces.Player;
import java.util.List;


public class PlayerImpl implements Player{

    @Override
    public Direction nextMove(Labyrinth l) {
        List<Direction> list = l.possibleMoves();
        int r = (int) (Math.random()*list.size());
        return list.get(r);
    }

}
