package destroyer;
import robocode.*;
import java.awt.*;
public class Witness extends AdvancedRobot {
  /**
   * Witness - a robot by lucas b.
   */
  private byte scanDirection = 1;
  @Override
  public void run() {
    setBodyColor(Color.magenta);
    setGunColor(Color.cyan);
    setRadarColor(Color.yellow);
    setBulletColor(Color.orange);
    setAdjustRadarForGunTurn(true);
    
    setTurnRadarRight(10000);
    execute();
    
    while (true) {
      if (getRadarTurnRemaining() == 0) {
        setTurnRadarRight(1);
      }
      execute();
    }
    
  }
  
  public void onScannedRobot(ScannedRobotEvent e) {
    setTurnRight(e.getBearing());
    if (Math.abs(getTurnRemaining()) < 10) {
      if (e.getDistance() > 200) {
        setAhead(e.getDistance() / 2);
      }
      
      if (e.getDistance() < 100) {
        setBack(e.getDistance() * 2);
      }
    }
    setFire(3);
    scanDirection *= -1;
    setTurnRadarRight(360 * scanDirection);
  }
  
  public void onRobotDeath(RobotDeathEvent e) { setTurnRadarRight(1000); }
  
  public void onWin(WinEvent e) {
    for (int i = 0; i < 50; i++)
    {
      turnRight(30);
      turnLeft(30);
    }
  }
}
