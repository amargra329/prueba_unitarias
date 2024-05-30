package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class LocationSpec {

    private final int x = 12;
    private final int y = 32;
    private final Direction direction = Direction.NORTH;
    private Point max;
    private Location location;
    private List<Point> obstacles;

    @BeforeMethod
    public void beforeTest() {
        max = new Point(50, 50);
        location = new Location(new Point(x, y), direction);
        obstacles = new ArrayList<Point>();
    }

    public void whenInstantiatedThenXIsStored() {
        assertEquals(location.getX(), x);
    }

    public void whenInstantiatedThenYIsStored() {
        assertEquals(location.getY(), y);
    }

    public void whenInstantiatedThenDirectionIsStored() {
        assertEquals(location.getDirection(), direction);
    }

    public void givenDirectionNWhenForwardThenYDecreases() {
       location.setDirection(Direction.NORTH);
       location.forward();
       assertEquals(location.getY(), y - 1);
    }

    public void givenDirectionSWhenForwardThenYIncreases() {
        location.setDirection(Direction.SOUTH);
        location.forward();
        assertEquals(location.getY(), y + 1);
    }

    public void givenDirectionEWhenForwardThenXIncreases() {
        location.setDirection(Direction.EAST);
        location.forward();
        assertEquals(location.getX(), x + 1);
    }

    public void givenDirectionWWhenForwardThenXDecreases() {
        location.setDirection(Direction.WEST);
        location.forward();
        assertEquals(location.getX(), x - 1);
    }

    public void givenDirectionNWhenBackwardThenYIncreases() {
        location.setDirection(Direction.NORTH);
        location.backward();
        assertEquals(location.getY(), y + 1);
    }

    public void givenDirectionSWhenBackwardThenYDecreases() {
        location.setDirection(Direction.SOUTH);
        location.backward();
        assertEquals(location.getY(), y - 1);
    }

    public void givenDirectionEWhenBackwardThenXDecreases() {
        location.setDirection(Direction.EAST);
        location.backward();
        assertEquals(location.getX(), x - 1);
    }

    public void givenDirectionWWhenBackwardThenXIncreases() {
        location.setDirection(Direction.WEST);
        location.backward();
        assertEquals(location.getX(), x + 1);
    }

    public void whenTurnLeftThenDirectionIsSet() {
        location.turnLeft();
        assertEquals(location.getDirection(), Direction.WEST);
    }

    public void whenTurnRightThenDirectionIsSet() {
        location.turnRight();
        assertEquals(location.getDirection(), Direction.EAST);
    }

    public void givenSameObjectsWhenEqualsThenTrue() {
        assertTrue(location.equals(location));
    }

    public void givenDifferentObjectWhenEqualsThenFalse() {
        Location loc = new Location(new Point(10, y), direction);
        assertFalse(location.equals(loc));
    }

    public void givenDifferentXWhenEqualsThenFalse() {
        Location loc = new Location(new Point(10, y), direction);
        assertFalse(location.equals(loc));
    }

    public void givenDifferentYWhenEqualsThenFalse() {
        Location loc = new Location(new Point(x, 10), direction);
        assertFalse(location.equals(loc));
    }

    public void givenDifferentDirectionWhenEqualsThenFalse() {
        Location loc2 = new Location(new Point(x, y), Direction.SOUTH);
        assertFalse(location.equals(loc2));
    }

    public void givenSameXYDirectionWhenEqualsThenTrue() {
        Location loc2 = location;
        assertTrue(location.equals(loc2));
    }

    public void whenCopyThenDifferentObject() {
        Location loc2 = location.copy();
        loc2.setDirection(Direction.EAST);
        assertNotEquals(loc2.getDirection(), location.getDirection());
    }

    public void whenCopyThenEquals() {
        Location loc2 = location.copy();
        assertTrue(location.equals(loc2));
    }

    public void givenDirectionEAndXEqualsMaxXWhenForwardThen1() {
        Location loc = new Location(new Point(max.getX(), y), Direction.EAST);
        loc.forward();
        assertEquals(loc.getX(),1);
    }

    public void givenDirectionWAndXEquals1WhenForwardThenMaxX() {
        Location loc = new Location(new Point(1, y), Direction.WEST);
        loc.forward();
        assertEquals(loc.getX(), max.getX());
    }

    public void givenDirectionNAndYEquals1WhenForwardThenMaxY() {
        Location loc = new Location(new Point(x, 1), Direction.NORTH);
        loc.forward();
        assertEquals(loc.getY(), max.getY());
    }

    public void givenDirectionSAndYEqualsMaxYWhenForwardThen1() {
        Location loc = new Location(new Point(x, max.getY()), Direction.SOUTH);
        loc.forward();
        assertEquals(loc.getY(),1);
    }

    public void givenObstacleWhenForwardThenReturnFalse() {

    }

    public void givenObstacleWhenBackwardThenReturnFalse() {

    }
}
