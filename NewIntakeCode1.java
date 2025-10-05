package org.firstinspires.ftc.decode.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp

public class TeleOpCode1 extends LinearOpMode {
    // introduce motor to program
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft; 
    private DcMotor backRight;

    @Override
    public void runOpMode() {
        //define variables 
        double drive, turn, strafe;
        double frontLeftPower, frontRightPower, backLeftPower, backRightPower;

        // initialize motors
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight"); 
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        
        //reverse necessary motors
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
        //reset encoders for each motor
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        
        // use encoders for each motor
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        //initialize on driver hub
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        // wait until program is started on driver hub
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //forward is controlled by the right joystick, y
            //turn is controlled by the left joysick, x 
            //strafe is controlled by the right joystick, x  
            telemetry.addData("Status", "Running");
            telemetry.update();
            drive=gamepad1.right_stick_y*-1;
            turn=gamepad1.left_stick_x;
            strafe = gamepad1.right_stick_x;
            
            frontLeftPower = drive + turn + strafe;
            frontRightPower = drive - turn - strafe; 
            backLeftPower = drive + turn - strafe; 
            backRightPower = drive - turn + strafe; 
            
            frontLeft.setPower(frontLeftPower); 
            frontRight.setPower(frontRightPower); 
            backLeft.setPower(backLeftPower); 
            backRight.setPower(backRightPower); 
            
        }
    }
}
