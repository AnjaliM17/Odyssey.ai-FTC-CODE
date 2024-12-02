package org.firstinspires.ftc.teamcode.clean;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.lang.Thread;
import java.lang.InterruptedException;

public class BaseAutonomous extends LinearOpMode  {

    protected DcMotor frontLeft;
    protected DcMotor frontRight;
    protected DcMotor backLeft;
    protected DcMotor backRight;
    protected DcMotor Extmotor;
    protected CRServo crServo;
    protected Servo clawServo;
    protected ElapsedTime runtime = new ElapsedTime();
    protected DcMotor[] motors = null;

    protected static final double ENCODER_TICKS_PER_INCH = 100.0; // Adjust this value based on your robot's configuration

    
    @Override
    public void runOpMode() throws InterruptedException {
        this.runOpMode();
    }
    
    protected void initMotors(DcMotor[] motors){
        for(int i=0;i<motors.length;i++){
         initMotor(motors[i]);
        }
    }
    
    private void initMotor(DcMotor motor){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

public void driveForward(double distance, double power) {
         initMotors(motors);
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
         int targetPositionFL = frontLeft.getCurrentPosition() + (int) (inches * ENCODER_TICKS_PER_INCH);
        int targetPositionFR = frontRight.getCurrentPosition() + (int) (inches * ENCODER_TICKS_PER_INCH);
        int targetPositionBL = backLeft.getCurrentPosition() + (int) (inches * ENCODER_TICKS_PER_INCH);
        int targetPositionBR = backRight.getCurrentPosition() + (int) (inches * ENCODER_TICKS_PER_INCH);
        
        frontLeft.setTargetPosition(targetPositionFL);
        frontRight.setTargetPosition(targetPositionFR);
        backLeft.setTargetPosition(targetPositionBL);
        backRight.setTargetPosition(targetPositionBR);
        

         frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + targetPositionFL); 
         backLeft.setTargetPosition(backLeft.getCurrentPosition() - targetPositionBL); 
         frontRight.setTargetPosition(frontRight.getCurrentPosition() - targetPositionFR); 
         backRight.setTargetPosition(backRight.getCurrentPosition() + targetPositionBR);

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
