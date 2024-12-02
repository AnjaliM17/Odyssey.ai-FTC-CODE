package org.firstinspires.ftc.teamcode.clean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonomousObservationZone2")
public class AutonomousObservationZone2 extends BaseAutonomous {


    private static final double ENCODER_TICKS_PER_INCH = 100.0; // Adjust this value based on your robot's configuration
    private static final double INCHES_PER_FOOT = 12.0;
    protected double power = 0.3;

    @Override
    public void runOpMode() {
        // Initialize the drive system variables
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
        
        DcMotor[] allMotors = {frontLeft, frontRight, backLeft, backRight, Extmotor};
        motors = allMotors;
        
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        
        initMotors(motors);
        
        waitForStart();

        // Autonomous actions
        sleep(1000);
        driveForward(0.5 * INCHES_PER_FOOT, power);
        strafe(9, power); 
        driveForward(1.5 * INCHES_PER_FOOT, power); // Drive forward 6 feet
        turnRight(51, power); // Turn right 90 degrees
        driveForward(0.25 * INCHES_PER_FOOT, power); // Drive forward 0.5 feet
        turnRight(55, power); // Turn right 90 degrees again
        driveForward(1.3 * INCHES_PER_FOOT, power); // Drive forward 6 feet
        sleep(250);
        initMotors(motors);
        strafe(50, power); 
        driveForward(0.2 * INCHES_PER_FOOT, power);
        turnRight(35,power);
        strafe(1, power);
        driveForward(0.4 * INCHES_PER_FOOT, power);
        sleep(100);
        driveForward(-0.5 * INCHES_PER_FOOT, power);
        turnRight(-35,power);
        // turnRight(-55, power); // Turn right 90 degrees again
        // driveForward(-0.25 * INCHES_PER_FOOT, power); // Drive forward 0.5 feet
        //  turnRight(-51, power); // Turn right 90 degrees
        //  driveForward(-1.5 * INCHES_PER_FOOT, power); // Drive forward 6 feet
        // strafe(-9, power); 
        // driveForward(-0.5 * INCHES_PER_FOOT, power);
    }

}
