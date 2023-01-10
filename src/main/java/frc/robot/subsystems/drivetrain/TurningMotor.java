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
}