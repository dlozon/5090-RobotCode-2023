package frc.robot.subsystems.drivetrain;

import frc.robot.wrappers.GenericPID;

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
        pid = new GenericPID(motor, ControlType.kPosition, 0);
    }

    public CANSparkMax getMotor() { return motor; }

    public void stop() { motor.set(0); }

    // Drive the motor
    public void forwards(double speed) { motor.set(speed);}
    public void backwards(double speed) { motor.set(-speed);}
}