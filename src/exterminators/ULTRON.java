package exterminators;

import robocode.*;
import robocode.util.Utils;
import java.awt.Color;

public class ULTRON extends AdvancedRobot {
    public void run() {
        setBodyColor(Color.darkGray);
        setGunColor(Color.darkGray);
        setRadarColor(Color.YELLOW);
        setBulletColor(Color.MAGENTA);
        setScanColor(Color.CYAN);


        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);


        while (true) {

            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            execute();
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {

        double anguloAbsoluto = e.getBearingRadians() + getHeadingRadians();

        double distancia = e.getDistance();

        setTurnGunRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getGunHeadingRadians()));

        if (distancia < 200 && getEnergy() > 2) {
            setFire(3);
        } else {
            setFire(1);
        }


        double anguloMovimento = Utils.normalRelativeAngle(anguloAbsoluto + Math.PI / 2 - 1.0
        );

        setTurnRightRadians(Utils.normalRelativeAngle(anguloMovimento - getHeadingRadians()));
        setAhead(100);
    }

    // Método chamado quando o robô é atingido por uma bala
    public void onHitByBullet(HitByBulletEvent e) {
        // Ajusta a direção do robô para evitar mais tiros
        setTurnRight(90 - e.getBearing());
        setAhead(100); // Move o robô para frente
    }

    // Método chamado quando o robô colide com uma parede
    public void onHitWall(HitWallEvent e) {
        setBack(50); // Move o robô para trás para se afastar da parede
        setTurnRight(45); // Ajusta a direção do robô para evitar futuras colisões
        setAhead(100); // Move o robô para frente
    }
}
