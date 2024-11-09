//// pre reset code 5th nov 3:54 pm

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TestServo1 (Blocks to Java)")
public class TestServo1 extends LinearOpMode {

  private Servo servoDown;
  private Servo servoUp;

  /**
   * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
   * Comment Blocks show where to place Initialization code (runs once, after touching the
   * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
   * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
   * Stopped).
   */
  @Override
  public void runOpMode() {
    servoDown = hardwareMap.get(Servo.class, "servoDown");
    servoUp = hardwareMap.get(Servo.class, "servoUp");
    
    // Put initialization blocks here.
    servoDown.setDirection(Servo.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      //servoDown.setPosition(0);
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.circle) {
         servoDown.setPosition(servoDown.getPosition() +0.01);
        } else if (gamepad1.square) {
         servoDown.setPosition(servoDown.getPosition()-0.01);
        }
        if (gamepad1.triangle) {
          servoUp.setPosition(-0.1);
        } else if (gamepad1.cross) {
          servoUp.setPosition(-1);
        }
        //telemetry.addData("Servo position:", servoDown.getPosition());
        telemetry.update();
      }
    }
  }
}
