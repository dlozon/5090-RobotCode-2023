package frc.robot.subsystems.drivetrain;

import static frc.robot.lists.ID_Numbers.SWERVE_MOTOR_IDS;
import static frc.robot.lists.Constants.*;
import java.util.*;

public class Drivetrain {
    private TurningMotor[] turningMotors = new TurningMotor[SWERVE_MOTOR_IDS.length];
    private DrivingMotor[] drivingMotors = new DrivingMotor[SWERVE_MOTOR_IDS.length];
    private double[] anglesFromCenter = new double[SWERVE_MOTOR_IDS.length];
    private double[] distScalers = new double[SWERVE_MOTOR_IDS.length];

    private double prevX = 0;
    private double prevY = 0;
    private double prevRotation = 0;

    // Constructor method
    public Drivetrain() {
        // Temporarily stores the linear distance between each module and the center
        ArrayList<Double> dists = new ArrayList<Double>(SWERVE_MOTOR_IDS.length);
        double furthest;

        // Initialize all of the Swerve motors
        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            // Current module's position
            double currX = SWERVE_POSITIONS[i][0];
            double currY = SWERVE_POSITIONS[i][1];

            // Create new motors with IDs from ID file
            turningMotors[i] = new TurningMotor(SWERVE_MOTOR_IDS[i][0]);
            drivingMotors[i] = new DrivingMotor(SWERVE_MOTOR_IDS[i][1]);

            // Angle from center
            double intermediate = Math.atan2(currY - centerY, currX - centerX);
            anglesFromCenter[i] = 90 - Math.toDegrees(intermediate);

            dists.add(linearDistance(centerX, centerY, currX, currY));
        }

        // Create a list of values that can scale the motor speeds
        // so that closer modules drive slower
        furthest = Collections.max(dists);
        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            distScalers[i] = dists.get(i) / furthest;
        }
    }

    // Converts the X and Y into a degree angle and checks 
    // if the inputs have changed since the last call
    public void drive(double X, double Y, double rotation) {
        
        // If the values are all within n% of last time they were called, then
        // nothing will happen, this is to hopefully save some computing time
        double error = .01;

        if( Math.abs((X - prevX) / prevX) > error || 
        Math.abs((Y - prevY) / prevY) > error ||
        Math.abs((rotation - prevRotation) / prevRotation) > error ) {
            
            // Store new values in prev
            this.prevX = X;
            this.prevY = Y;
            this.prevRotation = rotation;
            
            // Send the drive instruction
            if(rotation == 0)
                calculateDrive(X, Y);
            else
                calculateDrive(X, Y, rotation);
        }
    }



    // Computes and sends translation instructions for each of the swerve modules
    private void calculateDrive(double X, double Y) {
        // Convert the input coordinate to a vector
        double magnitude = linearDistance(X,Y);
        double direction = 90 - Math.toDegrees(Math.atan2(Y, X));
        double oppositeDirection = (direction + 180) % 360;
        
        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            double opt1 = angularDistance(turningMotors[i].getCurrDir(), direction);
            double opt2 = angularDistance(turningMotors[i].getCurrDir(), oppositeDirection);

            // Decide which of the two options to use
            if(opt1 <= opt2) { // Choose opt1
                turningMotors[i].turn(opt1);
                drivingMotors[i].forwards(magnitude);
            }
            else { // Choose opt2
                turningMotors[i].turn(opt2);
                drivingMotors[i].backwards(magnitude);
            }
        }
    }
    // Computes and sends overall instructions for each of the swerve modules
    private void calculateDrive(double X, double Y, double rotation) {
        // [n][0] is direction and [n][1] is magnitude
        double[][] netVectors = new double[SWERVE_MOTOR_IDS.length][2];
        double largestMagnitude = 0;

        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            // Convert the rotation vector to cartesian coordinates
            double rotationOffset = Math.signum(rotation) * 90;
            double rotationDirection = Math.toRadians(anglesFromCenter[i] + rotationOffset);
            double rotationMagnitude = Math.abs(rotation);
            double rotationX = rotationMagnitude * Math.cos(rotationDirection);
            double rotationY = rotationMagnitude * Math.sin(rotationDirection);

            // Combine the rotation and translation coordinates
            double netX = X + rotationX;
            double netY = Y + rotationY;

            // Convert the cordinate into a vector
            netVectors[i][0] = 90 - Math.toDegrees(Math.atan2(netX, netY));
            netVectors[i][1] = linearDistance(netX, netY) * distScalers[i];

            // Save the largest magnitude
            if(Math.abs(netVectors[i][0]) > largestMagnitude)
                largestMagnitude = netVectors[i][0];
        }

        for (int i = 0; i < SWERVE_MOTOR_IDS.length; i++ ) {
            // Normalize the magnitude
            if(largestMagnitude > 1)
                netVectors[i][1] /= largestMagnitude;

            // Pick the most efficient way to turn the wheel
            double oppositeDirection = (netVectors[i][0] + 180) % 360;
            double opt1 = angularDistance(turningMotors[i].getCurrDir(), netVectors[i][0]);
            double opt2 = angularDistance(turningMotors[i].getCurrDir(), oppositeDirection);

            // Decide which of the two options to use
            if(opt1 <= opt2) { // Choose opt1
                turningMotors[i].turn(opt1);
                drivingMotors[i].forwards(netVectors[i][1]);
            }
            else { // Choose opt2
                turningMotors[i].turn(opt2);
                drivingMotors[i].forwards(-netVectors[i][1]);
            }
        }
    }

    // Returns the linear distance between 0,0 and a given point
    private double linearDistance(double X, double Y) {
        return Math.sqrt(Math.pow(X, 2) + Math.pow(Y, 2));
    }
    // Returns the linear distance between 2 given points
    private double linearDistance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
    
    private double angularDistance(double currentDirection, double targetDirection) {
        // Calculates the distance between current and target in degrees
        double distToTarget = (targetDirection % 360) - (currentDirection % 360);

        // If dist is >180, its more efficient to go the other way (CW -> CCW and vice versa)
        if(Math.abs(distToTarget) > 180)
           distToTarget = -(Math.signum(distToTarget) * 360) + distToTarget; 

        return distToTarget;
    }

}

