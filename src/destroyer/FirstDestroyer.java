package destroyer;
import robocode.*;
import robocode.Robot;

import java.awt.*;

public class FirstDestroyer extends Robot {
  /**
   * FirstDestroyer - a robot by lucas b.
   */
  
  /**
   * run: MyFirstRobot's default behavior
   */
  
  public void run() {
    // Initialization of the robot should be put here
    
    setBodyColor(Color.magenta);
    setGunColor(Color.cyan);
    setRadarColor(Color.yellow);
    setBulletColor(Color.orange);
    
    // Robot main loop
    while (true) {
      // Replace the next 4 lines with any behavior you would like
      ahead(200);
      turnGunRight(180);
      back(50);
      turnGunRight(180);
    }
  }
  
  /**
   * onScannedRobot: What to do when you see another robot
   */
  public void onScannedRobot(ScannedRobotEvent e) {
    double distance = e.getDistance();
    if (distance > 800) {
      turnRight(e.getBearing());
      ahead(100);
      fire(3);
    } else if (distance <= 800 && distance >= 400) {
      turnRight(e.getBearing());
      fire(5);
    } else {
      fire(6);
    }
  }
  
  /**
   * onHitByBullet: What to do when you're hit by a bullet
   */
  public void onHitByBullet(HitByBulletEvent e) {
    // Replace the next line with any behavior you would like
    back(10);
    turnRight(20);
    ahead(20);
  }
  
  /**
   * onHitWall: What to do when you hit a wall
   */
  public void onHitWall(HitWallEvent e) {
    double bearing = e.getBearing();
    turnRight(-bearing);
    ahead(100);
  }
  
}
