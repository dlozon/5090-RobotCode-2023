package frc.robot.lists;

/* 
 * This class is only for holding numbers that are used repeatedly or across multiple files
 * DO NOT USE THIS FOR ANY OTHER PURPOSE
 * To access numbers in this file, statically import it like so:
 * import static frc.robot.lists.Constants.*;
 */
public final class Constants {
    // Deadzone used to combat stick-drift
    public static final double CONTROLLER_DEADZONE = .1;

    // Limelight calculation numbers
    public static final int LIME_HEIGHT = -1; // Height of limelight off the ground in inches
    public static final int TARGET_HEIGHT = -1; // Height of target in inches
    public static final double LIME_ANGLE = Math.toRadians(-1); // Angle of limelight relative to the floor

    // Limelight tuning numbers
    public static final double DESIRED_TARGET_AREA = 1.5;  // Area of the target when the robot reaches the wall
    public static final double DRIVE_K = 0.36;             // How fast to drive toward the target
    public static final double STEER_K = 0.05;             // How quickly the robot turns toward the target
    public static final double MAX_DRIVE = 0.5;            // Simple speed limit so we don't drive too fast

    // Swerve ratios
    public static final double DRIVING_MOTOR_RATIO = 1;
    public static final double TURNING_MOTOR_RATIO = 1;
}
