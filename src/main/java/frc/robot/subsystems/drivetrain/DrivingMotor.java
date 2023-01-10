package frc.robot.subsystems.drivetrain;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DrivingMotor {
    private CANSparkMax motor;

    // Constructor method
    public DrivingMotor(int motorID) {
        motor = new CANSparkMax(motorID, MotorType.kBrushless);
    }

    // Getters
    public CANSparkMax getMotor() { return motor; }

    public void stop() { motor.set(0); }

    // Drive the motor
    public void forwards(double speed) { motor.set(speed);}
    public void backwards(double speed) { motor.set(-speed);}

}