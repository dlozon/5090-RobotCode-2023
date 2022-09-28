package frc.robot.subsystems.drivetrain;

import frc.robot.wrappers.GenericPID;

import static frc.robot.lists.PID_Values.*;
import static frc.robot.lists.Constants.TURNING_MOTOR_RATIO;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class TurningMotor {
    private CANSparkMax motor;
    private GenericPID pid;

    // Constructor method
    public TurningMotor(int motorID) {
        motor = new CANSparkMax(motorID, MotorType.kBrushless);

        // initialize a GenericPID object with values from constants file
        pid = new GenericPID(motor, ControlType.kPosition, 
                             TURNING_MOTOR_P, TURNING_MOTOR_I, TURNING_MOTOR_D);
    }
 
    // Returns the current direction of the motor in degrees [0,360]
    public double getCurrDir() {
        return pid.getPosition() / TURNING_MOTOR_RATIO;
    }

    // Turns the motor by n degrees
    public void turn(double degrees) {
        pid.activate(getCurrDir() + (degrees * TURNING_MOTOR_RATIO));
    }

    // Turns the motor to n degrees
    public void turnTo(double degrees) {
        pid.activate(degrees * TURNING_MOTOR_RATIO);
    }
}
