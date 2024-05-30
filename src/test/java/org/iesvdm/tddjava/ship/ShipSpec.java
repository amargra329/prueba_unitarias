package org.iesvdm.tddjava.ship;

import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.*;

@Test
public class ShipSpec {

    private Ship ship;
    private Location location;
    private Planet planet;

    @BeforeMethod
    public void beforeTest() {
        Point max = new Point(50, 50);
        location = new Location(new Point(21, 13), Direction.NORTH);
        List<Point> obstacles = new ArrayList<>();
        obstacles.add(new Point(44, 44));
        obstacles.add(new Point(45, 46));
        planet = new Planet(max, obstacles);
//        ship = new Ship(location);
        ship = new Ship(location, planet);
    }

    public void whenInstantiatedThenLocationIsSet() {
//        Location location = new Location(new Point(21, 13), Direction.NORTH);
//        Ship ship = new Ship(location);

    }

//    public void givenNorthWhenMoveForwardThenYDecreases() {
//        ship.moveForward();
//        assertEquals(ship.getLocation().getPoint().getY(), 12);
//    }
//
//    public void givenEastWhenMoveForwardThenXIncreases() {
//        ship.getLocation().setDirection(Direction.EAST);
//        ship.moveForward();
//        assertEquals(ship.getLocation().getPoint().getX(), 22);
//    }

    public void whenMoveForwardThenForward() {
        ship.moveForward();
        int finaly = ship.getLocation().getY();

        assertEquals(finaly, 12);
    }

    public void whenMoveBackwardThenBackward() {
        ship.moveBackward();
        int finaly = ship.getLocation().getY();

        assertEquals(finaly, 14);
    }

    public void whenTurnLeftThenLeft() {
        ship.turnLeft();
        Direction finaly = ship.getLocation().getDirection();

        assertEquals(finaly, Direction.WEST);
    }

    public void whenTurnRightThenRight() {
        ship.turnRight();
        Direction finaly = ship.getLocation().getDirection();

        assertEquals(finaly, Direction.EAST);
    }

    public void whenReceiveCommandsFThenForward() {
        ship.receiveCommands("f");

        int finaly = ship.getLocation().getY();

        assertEquals(finaly, 12);
    }

    public void whenReceiveCommandsBThenBackward() {
        ship.receiveCommands("b");
        int finaly = ship.getLocation().getY();

        assertEquals(finaly, 14);
    }

    public void whenReceiveCommandsLThenTurnLeft() {
        ship.receiveCommands("l");
        Direction finaly = ship.getLocation().getDirection();

        assertEquals(finaly, Direction.WEST);
    }

    public void whenReceiveCommandsRThenTurnRight() {
        ship.receiveCommands("r");
        Direction finaly = ship.getLocation().getDirection();

        assertEquals(finaly, Direction.EAST);
    }

    public void whenReceiveCommandsThenAllAreExecuted() {
        ship.receiveCommands("r");
        Direction finalyDir = ship.getLocation().getDirection();

        assertEquals(finalyDir, Direction.EAST);

        ship.receiveCommands("l");
        finalyDir = ship.getLocation().getDirection();

        assertEquals(finalyDir, Direction.NORTH);

        ship.receiveCommands("b");
        int finalyInt = ship.getLocation().getY();

        assertEquals(finalyInt, 14);

        ship.receiveCommands("f");

        finalyInt = ship.getLocation().getY();

        assertEquals(finalyInt, 13);
    }

    public void whenInstantiatedThenPlanetIsStored() {
        Point max = new Point(50, 50);
        Planet planet = new Planet(max);
        ship = new Ship(location, planet);

        assertEquals(ship.getPlanet(), planet);
    }

    public void givenDirectionEAndXEqualsMaxXWhenReceiveCommandsFThenWrap() {

        Location loc = new Location(new Point(50,30),Direction.EAST);

        Ship ship = new Ship(loc, planet);

        ship.receiveCommands("f");

        assertEquals(ship.getLocation().getX(),1);
    }

    public void givenDirectionEAndXEquals1WhenReceiveCommandsBThenWrap() {
        Location loc = new Location(new Point(1,30),Direction.EAST);

        Ship ship = new Ship(loc, planet);

        ship.receiveCommands("b");

        assertEquals(ship.getLocation().getX(),50);
    }

    public void whenReceiveCommandsThenStopOnObstacle() {
    }

    public void whenReceiveCommandsThenOForOkAndXForObstacle() {

    }

}
