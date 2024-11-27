package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonomousCodeYuhh")
public class AutonomousCodeYuhh extends LinearOpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    //private DcMotor Extmotor; Extmotor is not working for some reason. needs to be fixed.
    private CRServo crServo;
    private Servo clawServo;
    private ElapsedTime runtime = new ElapsedTime();

    private static final double ENCODER_TICKS_PER_INCH = 100.0; // Adjust this value based on your robot's configuration

    @Override
    public void runOpMode() {
        // Initialize the drive system variables
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
        
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        
        waitForStart();

        // Autonomous actions
        driveForward(5, 0.1); 
        turnRight(-9, 0.1);
        driveForward(-5, 0.1); 
        turnRight(-8, 0.1); // Example of strafing right
        //Extmotor.setPower(0.1); this is not working for some reason, needs to be fixed. may not get fixed as new arm is being built.
        sleep(5000); // dropped the block into basket, going to get another one.
        driveForward(-3, 0.1);
        turnRight(10, 0.1); 
        driveForward(3, 0.1); 
    }

    public void driveForward(double distance, double power) {
        int targetPosition = frontLeft.getCurrentPosition() + (int) (distance * ENCODER_TICKS_PER_INCH);
        
        frontLeft.setTargetPosition(targetPosition);
        frontRight.setTargetPosition(targetPosition);
        backLeft.setTargetPosition(targetPosition);
        backRight.setTargetPosition(targetPosition);
        
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
        
        while (opModeIsActive() && (frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy())) {
            telemetry.addData("Path", "Driving to %7d", targetPosition);
            telemetry.addData("Left Front", frontLeft.getCurrentPosition());
            telemetry.addData("Right Front", frontRight.getCurrentPosition());
            telemetry.addData("Left Back", backLeft.getCurrentPosition());
            telemetry.addData("Right Back", backRight.getCurrentPosition());
            telemetry.update();
        }
        
        stopAndResetMotors();
    }

    public void turnRight(double degrees, double power) {
        int ticksPerDegree = 20; // Adjust this value based on your robot's turning characteristics
        int targetTicks = (int)(degrees * ticksPerDegree);

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setTargetPosition(-targetTicks);
        backLeft.setTargetPosition(-targetTicks);
        frontRight.setTargetPosition(targetTicks);
        backRight.setTargetPosition(targetTicks);

        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);

         while (opModeIsActive() && 
               (frontLeft.isBusy() && frontRight.isBusy() && 
                backLeft.isBusy() && backRight.isBusy())) {
            telemetry.addData("Turning", "Degrees: %f", degrees);
            telemetry.addData("Front Left", frontLeft.getCurrentPosition());
            telemetry.addData("Front Right", frontRight.getCurrentPosition());
            telemetry.addData("Back Left", backLeft.getCurrentPosition());
            telemetry.addData("Back Right", backRight.getCurrentPosition());
            telemetry.update();
         }

         stopAndResetMotors();
     }

     public void strafe(double inches, double power) {
         int targetPosition = (int) (inches * ENCODER_TICKS_PER_INCH);

         frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPosition); 
         backLeft.setTargetPosition(backLeft.getCurrentPosition() - targetPosition); 
         frontRight.setTargetPosition(frontRight.getCurrentPosition() - targetPosition); 
         backRight.setTargetPosition(backRight.getCurrentPosition() + targetPosition);

         frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
         frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
         backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION); 
         backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

         frontLeft.setPower(power); 
         frontRight.setPower(power); 
         backLeft.setPower(power); 
         backRight.setPower(power);

          while (opModeIsActive() && 
                (frontLeft.isBusy() && frontRight.isBusy() && 
                 backLeft.isBusy() && backRight.isBusy())) { 
             telemetry.addData("Strafing", "Inches: %f", inches); 
             telemetry.addData("Front Left", frontLeft.getCurrentPosition()); 
             telemetry.addData("Front Right", frontRight.getCurrentPosition()); 
             telemetry.addData("Back Left", backLeft.getCurrentPosition()); 
             telemetry.addData("Back Right", backRight.getCurrentPosition()); 
             telemetry.update(); 
          }

          stopAndResetMotors();
      }

      private void stopAndResetMotors() {
          // Stop motors
          frontLeft.setPower(0); 
          frontRight.setPower(0); 
          backLeft.setPower(0); 
          backRight.setPower(0);

          // Reset to RUN_WITHOUT_ENCODER mode
          frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); 
          frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); 
          backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); 
          backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);  
      }
}
