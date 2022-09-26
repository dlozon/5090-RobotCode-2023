package frc.robot;

// Controller Imports
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick;

// Actuation imports (Motors, Compressors, etc.)

// Camera imports
import edu.wpi.first.cameraserver.CameraServer;

// Misc imports
import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.misc_subclasses.Dashboard;
import frc.robot.misc_subclasses.Limelight;
import frc.robot.subsystems.drivetrain.Drivetrain;

// Constant value imports
import static frc.robot.lists.Constants.*;
import static frc.robot.lists.ID_Numbers.*;
import static frc.robot.lists.PID_Values.*;



/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  // Controller ojects
  private Joystick joystick; 
  private XboxController xbox;

  // Subsystem objects
  private Drivetrain drivetrain;
  private Dashboard dashboard;
  private Limelight limelight;
  
  // This function is run when the robot is first started up and should be used
  // for any initialization code.
  @Override
  public void robotInit() {
    // Initialize variables
    joystick = new Joystick(JOYSTICK_PORT);
    xbox  = new XboxController(CONTROLLER_PORT);

    CameraServer.startAutomaticCapture();
    limelight = new Limelight();

    dashboard = new Dashboard();
  }

  // This function is called once at the start of auton
  @Override
  public void autonomousInit() {

  }

  // This function is called every 20ms during auton
  @Override
  public void autonomousPeriodic() { 
    
  }
  
  // This function is called once at the start of teleop
  @Override
  public void teleopInit() {
    
  }

  // This function is called every 20ms during teleop
  @Override
  public void teleopPeriodic() {
   
  }

  // This function is called every 20ms while the robot is enabled
  @Override
  public void robotPeriodic() {

    // Update subclass internal values
    limelight.updateLimelightTracking();

    // Update dashboard
    dashboard.printLimelightData(limelight);
  }
}