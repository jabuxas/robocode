package exterminators;

import robocode.*;

public class Bumblebee extends AdvancedRobot {

    @Override
    public void run() {
        // Configurações para que a arma e o radar girem independentemente do corpo do robô
        setAdjustRadarForRobotTurn(true);
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        while (true) {
            // Gira o radar continuamente para a direita
            setTurnRadarRight(360);
            // Move para frente e para trás aleatoriamente
            setAhead(100 * (Math.random() - 0.5));
            // Gira aleatoriamente
            setTurnRight(90 * (Math.random() - 0.5));
            // Executa todas as ações pendentes
            execute();
        }
    }

    @Override
    public void onScannedRobot(ScannedRobotEvent evento) {
        double potenciaBala = Math.min(3.0, getEnergy()); // Define a potência do tiro com base na energia do robô
        double meuX = getX();
        double meuY = getY();
        double anguloAbsoluto = getHeadingRadians() + evento.getBearingRadians();
        double inimigoX = meuX + evento.getDistance() * Math.sin(anguloAbsoluto);
        double inimigoY = meuY + evento.getDistance() * Math.cos(anguloAbsoluto);
        double cabecaInimigo = evento.getHeadingRadians();
        double velocidadeInimigo = evento.getVelocity();

        // Previsão da posição do inimigo
        double deltaTempo = 0;
        double previsaoX = inimigoX;
        double previsaoY = inimigoY;
        double velocidadeBala = 20 - 3 * potenciaBala; // Velocidade da bala

        while ((++deltaTempo) * velocidadeBala < Math.hypot(meuX - previsaoX, meuY - previsaoY)) {
            previsaoX += Math.sin(cabecaInimigo) * velocidadeInimigo;
            previsaoY += Math.cos(cabecaInimigo) * velocidadeInimigo;
        }

        double theta = Math.atan2(previsaoX - meuX, previsaoY - meuY); // Calcula o ângulo absoluto

        // Gira o radar, a arma e atira
        setTurnRadarRightRadians(normalizarAngulo(anguloAbsoluto - getRadarHeadingRadians()));
        setTurnGunRightRadians(normalizarAngulo(theta - getGunHeadingRadians()));
        setFire(potenciaBala);

        // Move o robô
        setTurnRight(evento.getBearing());
        setAhead(100);
    }

    @Override
    public void onHitByBullet(HitByBulletEvent evento) {
        // Ajusta a direção do robô para evitar mais tiros
        setTurnRight(90 - evento.getBearing());
        setAhead(150); // Move para frente para escapar
    }

    @Override
    public void onHitWall(HitWallEvent evento) {
        back(50); // Move para trás para se afastar da parede
        setTurnRight(90); // Gira o robô para evitar a parede
        ahead(50); // Move para frente novamente
    }

    // Método utilitário para normalizar um ângulo para o intervalo [-PI, PI]
    private double normalizarAngulo(double angulo) {
        while (angulo > Math.PI) angulo -= 2 * Math.PI;
        while (angulo < -Math.PI) angulo += 2 * Math.PI;
        return angulo;
    }
}
