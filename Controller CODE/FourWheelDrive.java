package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "Fourwheelmecanumwheeldrivecontroller1 (Blocks to Java)")
public class Fourwheelmecanumwheeldrivecontroller1 extends LinearOpMode {

  private DcMotor rightfrontmotorAsDcMotor;
  private DcMotor rightbackmotorAsDcMotor;
  private DcMotor leftfrontmotorAsDcMotor;
  private DcMotor LeftbackmotorAsDcMotor;
  private DcMotor Armmotor;
  private DcMotor Extmotor;

  /**
   * This sample contains the bare minimum Blocks for any regular OpMode. The 3 blue
   * Comment Blocks show where to place Initialization code (runs once, after touching the
   * DS INIT button, and before touching the DS Start arrow), Run code (runs once, after
   * touching Start), and Loop code (runs repeatedly while the OpMode is active, namely not
   * Stopped).
   */
  @Override
  public void runOpMode() {
    float forward;
    float strafe;
    float turn;
    double denominator;

    rightfrontmotorAsDcMotor = hardwareMap.get(DcMotor.class, "rightfrontmotorAsDcMotor");
    rightbackmotorAsDcMotor = hardwareMap.get(DcMotor.class, "rightbackmotorAsDcMotor");
    leftfrontmotorAsDcMotor = hardwareMap.get(DcMotor.class, "leftfrontmotorAsDcMotor");
    LeftbackmotorAsDcMotor = hardwareMap.get(DcMotor.class, "LeftbackmotorAsDcMotor");
    Armmotor = hardwareMap.get(DcMotor.class, "Arm motor");
    Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");

    // Put initialization blocks here.
    rightfrontmotorAsDcMotor.setDirection(DcMotor.Direction.REVERSE);
    rightbackmotorAsDcMotor.setDirection(DcMotor.Direction.REVERSE);
    waitForStart();
    if (opModeIsActive()) {
      // Put run blocks here.
      while (opModeIsActive()) {
        // Put loop blocks here.
        forward = -gamepad1.right_stick_y;
        strafe = gamepad1.right_stick_x;
        turn = gamepad1.left_stick_x;
        if (gamepad1.left_bumper) {
          forward = forward / 2;
          strafe = forward / 2;
          turn = forward / 2;
        }
        denominator = JavaUtil.maxOfList(JavaUtil.createListWith(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn)));
        leftfrontmotorAsDcMotor.setPower((forward + strafe + turn) / denominator);
        LeftbackmotorAsDcMotor.setPower((forward - (strafe + turn)) / denominator);
        rightfrontmotorAsDcMotor.setPower((forward - (strafe - turn)) / denominator);
        rightbackmotorAsDcMotor.setPower((forward + (strafe - turn)) / denominator);
        if (gamepad1.dpad_up) {
          Armmotor.setPower(0.5);
        } else if (gamepad1.dpad_down) {
          Armmotor.setPower(-0.5);
        } else {
          Armmotor.setPower(0.05);
          Armmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        if (gamepad1.dpad_left) {
          Extmotor.setPower(0.5);
        } else if (gamepad1.dpad_right) {
          Extmotor.setPower(-0.5);
        } else {
          Extmotor.setPower(0);
          Extmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        }
        telemetry.update();
      }
    }
  }
}
