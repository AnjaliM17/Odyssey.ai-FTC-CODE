package org.firstinspires.ftc.teamcode.clean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.Thread;
import java.lang.InterruptedException;

@Autonomous(name = "AutonomousNetZone2")
public class AutonomousNetZone2 extends BaseAutonomous  {

    private static final double ENCODER_TICKS_PER_INCH = 100.0; // Adjust this value based on your robot's configuration
    private double x = 0.3;

    @Override
    public void runOpMode() throws InterruptedException{
        // Initialize the drive system variables
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
        DcMotor[] allMotors = {frontLeft, frontRight, backLeft, backRight, Extmotor};
        motors = allMotors;
        initMotors(motors);
        
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        
        
        
        waitForStart();

        // Autonomous actions
        strafe(-4, x);
        driveForward(-5, x);
    //   // Thread.sleep(1000);
    //     driveForward(-5, 0.1); 
         turnRight(-25, x); 
        // Extmotor.setPower(0.1);
        // sleep(5000); // dropped the block into basket, going to get another one.
        
        driveForward(-2.99, x);
        //turnRight(-5, 0.1); 
        // driveForward(7, 0.1); 
        // turnRight(-7,0.1);
        // driveForward(8, 0.1);
        // turnRight(-7, 0.1); 
        
    }
}
