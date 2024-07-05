package destroyer;
import robocode.*;

import java.awt.*;

public class SevenPetalLotus extends AdvancedRobot {
  /**
   * SevenPetalLotus - a robot by lucas b.
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
      setTurnRight(1000);
      setMaxTurnRate(3);
      ahead(1000);
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
  
  public void onHitRobot(HitRobotEvent event) {
    turnRight(event.getBearing());
    fire(3);
    if (event.isMyFault()) {
      turnLeft(10);
    }
  }
}
