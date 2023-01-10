package frc.robot.subsystems.drivetrain;

import static frc.robot.lists.ID_Numbers.SWERVE_MOTOR_IDS;

public class Drivetrain {
    private TurningMotor[] turningMotors = new TurningMotor[SWERVE_MOTOR_IDS.length];
    private DrivingMotor[] drivingMotors = new DrivingMotor[SWERVE_MOTOR_IDS.length];

    // Constructor method
    public Drivetrain() {
        // Initialize all of the Swerve motors
        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            // Create new motors with IDs from ID file
            turningMotors[i] = new TurningMotor(SWERVE_MOTOR_IDS[i][0]);
            drivingMotors[i] = new DrivingMotor(SWERVE_MOTOR_IDS[i][1]);
        }
    }

    public void drive(double speed) {
        for (DrivingMotor motor : drivingMotors) {
            motor.forwards(speed);
        }
    }
    public void spin(double speed) {
        for (TurningMotor motor : turningMotors) {
            motor.forwards(speed);
        }
    }


    public void stopDriving() { 
        for (DrivingMotor motor : drivingMotors) {
            motor.stop();
        }
    }
    public void stopTurning() { 
        for (TurningMotor motor : turningMotors) {
            motor.stop();
        }
    }
    public void stop() { stopDriving(); stopTurning(); }
}