package exterminators;

import robocode.*; // Importa a biblioteca principal do Robocode
import robocode.util.Utils; // Importa utilitários do Robocode para cálculos de ângulos
import java.awt.Color; // Importa a classe Color da biblioteca Java para personalização de cores

// Define a classe ULTRON que estende a classe Robot
public class ULTRON extends AdvancedRobot {
    // Método principal do robô, executado quando a batalha começa
    public void run() {
        // Personalização de cores do robô
        setBodyColor(Color.darkGray); // Define a cor do corpo do robô como cinza escuro
        setGunColor(Color.darkGray); // Define a cor da arma do robô como vermelha
        setRadarColor(Color.YELLOW); // Define a cor do radar do robô como amarela
        setBulletColor(Color.MAGENTA); // Define a cor das balas disparadas pelo robô como magenta
        setScanColor(Color.CYAN); // Define a cor do feixe de escaneamento do robô como ciano

        // Permite que a arma e o radar girem independentemente do corpo do robô
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForRobotTurn(true);

        // Loop principal do robô
        while (true) {
            // Gira o radar indefinidamente para a direita para escanear o campo de batalha
            setTurnRadarRightRadians(Double.POSITIVE_INFINITY);
            execute(); // Executa todas as ações pendentes
        }
    }

    // Método chamado quando um robô inimigo é escaneado pelo radar
    public void onScannedRobot(ScannedRobotEvent e) {
        // Calcula a direção absoluta do inimigo
        double anguloAbsoluto = e.getBearingRadians() + getHeadingRadians();
        // Calcula a velocidade lateral do inimigo
        double velocidadeLateral = getVelocity() * Math.sin(e.getBearingRadians());

        // Ajusta a direção da arma para mirar no inimigo
        setTurnGunRightRadians(Utils.normalRelativeAngle(anguloAbsoluto - getGunHeadingRadians() + velocidadeLateral / 22));

        // Verifica se a energia do robô é maior que 2 antes de disparar
        if (getEnergy() > 2) {
            setFire(2); // Dispara uma bala com potência 2
        }

        // Calcula o ângulo de movimento para evitar colisões e se manter em movimento
        double anguloMovimento = Utils.normalRelativeAngle(anguloAbsoluto + Math.PI / 2 - 0.5);
        // Ajusta a direção do movimento e se move para frente
        setTurnRightRadians(Utils.normalRelativeAngle(anguloMovimento - getHeadingRadians()));
        setAhead(100); // Move o robô para frente
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
