package destroyer;

import robocode.*;

import java.awt.*;
import java.util.Random;

public class Singularity extends AdvancedRobot {
  /**
   * Singularity - a robot by lucas b.
   */
    private EnemyBot enemy = new EnemyBot();
    private Random rand = new Random();
    
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
  
  public void onBulletHit(BulletHitEvent e) {
      setBodyColor(randomColor());
      setGunColor(randomColor());
      setRadarColor(randomColor());
      setBulletColor(randomColor());
      setScanColor(randomColor());
  }
  
  void doScanner() {
      setTurnRadarRight(360);
    }
    
    void doMovement() {
      setTurnRight(10);
      if (enemy.getDistance() > 200)
        setAhead(enemy.getDistance() / 2);
      if (enemy.getDistance() < 100)
        setBack(enemy.getDistance() * 2);
      setTurnLeft(10);
    }
    
    void doGun() {
      if (enemy.none()) return;
      
      double biggestDimension = Math.max(getBattleFieldHeight(), getBattleFieldWidth());
      
      if (Math.abs(getTurnRemaining()) < 10) {
        if (enemy.getDistance() < biggestDimension / 3) {
          setFire(3);
        } else {
          setFire(1);
        }
      }
    }
  
  public void onWin(WinEvent e) {
    for (int i = 0; i < 50; i++)
    {
      setBodyColor(randomColor());
      setGunColor(randomColor());
      setRadarColor(randomColor());
      setBulletColor(randomColor());
      turnRight(30);
      turnLeft(30);
    }
  }
  
    private Color randomColor() {
      return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }
    
  }