package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Fourwheelmecanumdrive")
public class Fourwheelmecanumdrive extends LinearOpMode {

    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;

    @Override
    public void runOpMode() {
        float forward;
        float strafe;
        float turn;
        double denominator;

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        // Initialization
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            forward = -gamepad1.right_stick_y;  
            strafe = -gamepad1.right_stick_x;
            turn = -gamepad1.left_stick_x;

            if (gamepad1.left_bumper) {
                forward /= 2;
                strafe /= 2;
                turn /= 2;
            } else if (gamepad1.right_bumper) {
                forward *= 2;
                strafe *= 2;
                turn *= 2;
            }

            denominator = Math.max(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn));

            frontLeft.setPower((forward + strafe + turn) / denominator);
            backLeft.setPower((forward - strafe + turn) / denominator);
            frontRight.setPower((forward - strafe - turn) / denominator);
            backRight.setPower((forward + strafe - turn) / denominator);

            telemetry.addData("Forward", forward);
            telemetry.addData("Strafe", strafe);
            telemetry.addData("Turn", turn);
            telemetry.update();
        }
    }
}
