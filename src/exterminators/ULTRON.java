package exterminators;

import robocode.*;
import robocode.util.Utils;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class ULTRON extends AdvancedRobot {
    private Point2D.Double myLocation;
    private Point2D.Double enemyLocation;

    @Override
    public void run() {
        // Personalização de cores
        setBodyColor(Color.DARK_GRAY);
        setGunColor(Color.RED);
        setRadarColor(Color.YELLOW);
        setBulletColor(Color.MAGENTA);
        setScanColor(Color.CYAN);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        myLocation = new Point2D.Double(getX(), getY());

        double lateralVelocity = getVelocity() * Math.sin(e.getBearingRadians());
        double absBearing = e.getBearingRadians() + getHeadingRadians();
        double distance = e.getDistance();


        setTurnGunRightRadians(Utils.normalRelativeAngle(absBearing - getGunHeadingRadians() + lateralVelocity / 22));


        if (getEnergy() > 2) {
            setFire(2);
        }


        double moveAngle = Utils.normalRelativeAngle(absBearing + Math.PI / 2 - (distance > 150 ? 0.5 : -0.5));
        moveAngle = wallSmoothing(myLocation, moveAngle, distance > 150 ? 1 : -1);

        setTurnRightRadians(Utils.normalRelativeAngle(moveAngle - getHeadingRadians()));
        setAhead(100);


        enemyLocation = project(myLocation, absBearing, e.getDistance());
    }

    @Override
    public void onHitByBullet(HitByBulletEvent e) {

        setTurnRight(90 - e.getBearing());
        setAhead(100);
    }

    @Override
    public void onHitWall(HitWallEvent e) {

        setBack(50);
        setTurnRight(45);
        setAhead(100);
    }

    private double wallSmoothing(Point2D.Double botLocation, double angle, int orientation) {
        while (!fieldRectangle(18).contains(project(botLocation, angle, 160))) {
            angle += orientation * 0.05;
        }
        return angle;
    }

    private static Rectangle2D.Double fieldRectangle(int buffer) {
        return new Rectangle2D.Double(buffer, buffer, 800 - buffer * 2, 600 - buffer * 2);
    }

    private static Point2D.Double project(Point2D.Double sourceLocation, double angle, double length) {
        return new Point2D.Double(sourceLocation.x + Math.sin(angle) * length, sourceLocation.y + Math.cos(angle) * length);
    }
}
