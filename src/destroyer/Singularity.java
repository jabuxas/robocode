package destroyer;

import robocode.*;

import java.awt.*;

public class Singularity extends AdvancedRobot {
    private EnemyBot enemy = new EnemyBot();
    
    public void run() {
      setBodyColor(Color.magenta);
      setGunColor(Color.cyan);
      setRadarColor(Color.yellow);
      setBulletColor(Color.orange);
     
      setAdjustRadarForGunTurn(true);
      enemy.reset();
      
      while (true) {
        doScanner();
        doMovement();
        doGun();
        execute();
      }
    }
    
    public void onScannedRobot(ScannedRobotEvent e) {
      if (
          enemy.none() ||
              e.getDistance() < enemy.getDistance() - 70 ||
              e.getName().equals(enemy.getName())
      ) {
        enemy.update(e);
        setTurnRight(e.getBearing());
      }
    }
    
    public void onRobotDeath(RobotDeathEvent e) {
      if (e.getName().equals(enemy.getName())) {
        enemy.reset();
      }
    }
    
    void doScanner() {
      setTurnRadarRight(360);
    }
    
    void doMovement() {
      if (enemy.getDistance() > 200)
        setAhead(enemy.getDistance() / 2);
      if (enemy.getDistance() < 100)
        setBack(enemy.getDistance());
    }
    
    void doGun() {
      if (enemy.none()) return;
      
      double max = Math.max(getBattleFieldHeight(), getBattleFieldWidth());
      
      if (Math.abs(getTurnRemaining()) < 10) {
        if (enemy.getDistance() < max / 3) {
          setFire(3);
        } else {
          setFire(1);
        }
      }
    }
    
  }