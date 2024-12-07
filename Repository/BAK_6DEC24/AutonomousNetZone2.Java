package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "armforwardbackward (Blocks to Java)")
public class armforwardbackward extends LinearOpMode {

  /**
   * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
   * Comment Blocks show where to place Initialization code (runs once, after touching the
   * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
   * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
   * Stopped).
   */
  @Override
  public void runOpMode() {
    // Put initialization blocks here.
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        if (gamepad1.right_stick_y && gamepad1.left_stick_y) {
            Armmotor.setPower(0.5); //this code sets the armmotor to 0.5 when BOTH joysticks are interacted with
        } else {
          Armmotor.setPower(0.05);
          Armmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        telemetry.update();
      }
    }
  }
}
