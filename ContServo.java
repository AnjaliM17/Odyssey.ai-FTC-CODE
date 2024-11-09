//// pre reset code 5th nov 3:53 pm

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "CustomServoCode (Blocks to Java)")
public class ContServo extends LinearOpMode {

  private Servo servoDown;
  private Servo servoUp;
  private double servoDownPos = 0.5; // Start at midpoint
  private double servoUpPos = 0.5; // Start at midpoint
  private static final double SERVO_STEP = 0.01; // Amount to change per button press
  private CRServo contservo;
  

  @Override
  public void runOpMode() {
    servoDown = hardwareMap.get(Servo.class, "servoDown");
    servoUp = hardwareMap.get(Servo.class, "servoUp");
    
    // Initialize servo positions
    servoDown.setPosition(servoDownPos);
    servoUp.setPosition(servoUpPos);

    waitForStart();
    if (opModeIsActive()) {
      while (opModeIsActive()) {
        // Handle servoDown
        contservo.setPower(0.45);

          if (gamepad1.circle) {
          servoDownPos = 0.35;
          servoDown.setPosition(servoDownPos);
        } else if (gamepad1.square) {
          servoDownPos = 0.2;
          servoDown.setPosition(servoDownPos);
        }
        
        if (gamepad1.circle) {
          servoDownPos = Math.min(1.0, servoDownPos + SERVO_STEP);
          servoDown.setPosition(servoDownPos);
        } else if (gamepad1.square) {
          servoDownPos = Math.max(0.0, servoDownPos - SERVO_STEP);
          servoDown.setPosition(servoDownPos);
        }
         
        // Handle servoUp
        if (gamepad1.triangle) {
          servoUpPos = Math.min(1.0, servoUpPos + SERVO_STEP);
          servoUp.setPosition(servoUpPos);
        } else if (gamepad1.cross) {
          servoUpPos = Math.max(0.0, servoUpPos - SERVO_STEP);
          servoUp.setPosition(servoUpPos);
        }

        // Update telemetry
        telemetry.addData("servoDown position", servoDownPos);
        telemetry.addData("servoUp position", servoUpPos);
        telemetry.update();

        // Small delay to prevent excessive CPU usage
        sleep(50);
      }
    }
  }
}