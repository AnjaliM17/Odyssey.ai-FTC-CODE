package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "AutonomousObservationZone")
public class AutonomousObservationZone extends LinearOpMode {

    private double power = 0.35;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor Extmotor;
    private DcMotor[] motors = null;
    private ElapsedTime runtime = new ElapsedTime();

    private static final double ENCODER_TICKS_PER_INCH = 100.0; // Adjust this value based on your robot's configuration
    private static final double INCHES_PER_FOOT = 12.0;

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
    
    private void initMotors(DcMotor[] motors){
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
    int targetPosition = (int) (distance * ENCODER_TICKS_PER_INCH);
        
    setTargetPosition(targetPosition);
    setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
    setPower(power);
        
    while (opModeIsActive() && motorsAreBusy()) {
        telemetry.addData("Path", "Driving %f inches", distance);
        telemetry.addData("Target", targetPosition);
        updateTelemetryMotorPositions();
        telemetry.update();
    }
    
    stopAndResetMotors();
}

    public void turnRight(double degrees, double power) {
        int ticksPerDegree = 20; // Adjust this value based on your robot's turning characteristics
        int targetTicks = (int)(degrees * ticksPerDegree);

        setRunMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeft.setTargetPosition(targetTicks);
        backLeft.setTargetPosition(targetTicks);
        frontRight.setTargetPosition(-targetTicks);
        backRight.setTargetPosition(-targetTicks);

        setRunMode(DcMotor.RunMode.RUN_TO_POSITION);
        setPower(power);

        while (opModeIsActive() && motorsAreBusy()) {
            telemetry.addData("Turning", "Degrees: %f", degrees);
            updateTelemetryMotorPositions();
            telemetry.update();
        }

        stopAndResetMotors();
    }
    
    public void strafe(double inches, double power) {
        initMotors(motors);

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

    private void setTargetPosition(int position) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + position);
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + position);
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + position);
        backRight.setTargetPosition(backRight.getCurrentPosition() + position);
    }

    private void setRunMode(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    private void setPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    private boolean motorsAreBusy() {
        return frontLeft.isBusy() && frontRight.isBusy() && backLeft.isBusy() && backRight.isBusy();
    }

    private void updateTelemetryMotorPositions() {
        telemetry.addData("Left Front", frontLeft.getCurrentPosition());
        telemetry.addData("Right Front", frontRight.getCurrentPosition());
        telemetry.addData("Left Back", backLeft.getCurrentPosition());
        telemetry.addData("Right Back", backRight.getCurrentPosition());
    }

    private void stopAndResetMotors() {
        setPower(0);
        setRunMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}
