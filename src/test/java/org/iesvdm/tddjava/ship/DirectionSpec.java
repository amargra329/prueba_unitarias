package org.iesvdm.tddjava.ship;

import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;

@Test
public class DirectionSpec {

    public void whenGetFromShortNameNThenReturnDirectionN() {
        Assert.assertEquals(Direction.getFromShortName('N'), Direction.NORTH);
    }

    public void whenGetFromShortNameWThenReturnDirectionW() {
        Assert.assertEquals(Direction.getFromShortName('W'), Direction.WEST);
    }

    public void whenGetFromShortNameBThenReturnNone() {
        Assert.assertEquals(Direction.getFromShortName('B'), Direction.NONE);
    }

    public void givenSWhenLeftThenE() {
        Direction dir = Direction.getFromShortName('S');
        assertEquals(dir.turnLeft(), Direction.EAST);
    }

    public void givenNWhenLeftThenW() {
        Direction dir = Direction.getFromShortName('N');
        assertEquals(dir.turnLeft(), Direction.WEST);
    }

    public void givenSWhenRightThenW() {
        Direction dir = Direction.getFromShortName('S');
        assertEquals(dir.turnRight(), Direction.WEST);
    }

    public void givenWWhenRightThenN() {
        Direction dir = Direction.getFromShortName('W');
        assertEquals(dir.turnRight(), Direction.NORTH);
    }
}
