package exterminators;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class ULTRON extends AdvancedRobot {
    @Override
    public void run() {
        setBodyColor(Color.darkGray);
        setGunColor(Color.black);
        setRadarColor(Color.darkGray);
        setBulletColor(Color.yellow);
        setScanColor(Color.CYAN);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        while (true) {
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        double anguloAbsoluto = e.getBearingRadians() + getHeadingRadians();
        double distancia = e.getDistance();

        setTurnGunRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getGunHeadingRadians()));

        if (distancia < 200 && getEnergy() > 2) {
            setFire(3);
        } else {
            setFire(1);
        }

        double anguloMovimento = Utils.normalRelativeAngle(anguloAbsoluto + Math.PI / 2 - 0.6);
        setTurnRightRadians(Utils.normalRelativeAngle(anguloMovimento - getHeadingRadians()));
        setAhead(100);
        execute();
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
        execute();
    }
}
