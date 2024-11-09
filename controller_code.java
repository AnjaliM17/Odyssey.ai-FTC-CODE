// pre reset code 5th nov 3:52 pm

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "controller_code (Blocks to Java)")
public class controller_code extends LinearOpMode {

  private DcMotor leftbackmotor;
  private DcMotor rightbackmotor;
  private DcMotor armmotor;
  private Servo leftservo;
  private Servo rightservo;

  /**
   * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
   * Comment Blocks show where to place Initialization code (runs once, after touching the
   * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
   * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
   * Stopped).
   */
  @Override
  public void runOpMode() {
    leftbackmotor = hardwareMap.get(DcMotor.class, "leftbackmotor");
    rightbackmotor = hardwareMap.get(DcMotor.class, "rightbackmotor");
    armmotor = hardwareMap.get(DcMotor.class, "armmotor");
    leftservo = hardwareMap.get(Servo.class, "leftservo");
    rightservo = hardwareMap.get(Servo.class, "rightservo");

    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        leftbackmotor.setPower(gamepad1.left_stick_y);
        telemetry.addData("Motor Power", leftbackmotor.getPower());
        telemetry.update();
        rightbackmotor.setPower(-gamepad1.right_stick_y);
        telemetry.addData("Motor Power", rightbackmotor.getPower());
        telemetry.update();
        if (gamepad1.dpad_up) {
          armmotor.setPower(0.5);
        } else if (gamepad1.dpad_down) {
          armmotor.setPower(-0.5);
        } else {
          armmotor.setPower(0.05);
          armmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad1.square) {
          // RESTING POSITION
          leftservo.setPosition(0);
          rightservo.setPosition(1);
        } else if (gamepad1.circle) {
          // FULL ROTATION
          leftservo.setPosition(1);
          rightservo.setPosition(0);
        } else if (gamepad1.triangle) {
          // MIDWAY POSITION
          leftservo.setPosition(0.45);
          rightservo.setPosition(0.55);
        }
      }
    }
  }
}
