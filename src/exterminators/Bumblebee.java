package exterminators;

import robocode.*;
import robocode.util.Utils;

import java.awt.Color;

public class Bumblebee extends AdvancedRobot {

    @Override
    public void run() {
        setBodyColor(Color.yellow);
        setGunColor(Color.yellow);
        setRadarColor(Color.YELLOW);
        setBulletColor(Color.yellow);
        setScanColor(Color.yellow);

        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            setTurnRadarRight(360);
            setAhead(100 * (Math.random() - 0.5));
            setTurnRight(90 * (Math.random() - 0.5));
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
    public void onHitByBullet(HitByBulletEvent evento) {
        setTurnRight(90 - evento.getBearing());
        setAhead(150); // Move para frente para escapar
    }

    @Override
    public void onHitWall(HitWallEvent evento) {
        back(50); // Move para trás para se afastar da parede
        setTurnRight(90); // Gira o robô para evitar a parede
        ahead(50);
    }


}
