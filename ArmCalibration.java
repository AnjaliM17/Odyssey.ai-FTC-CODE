package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmCalibration {
    private DcMotor motor;
    private Gamepad gamepad;
    private Telemetry telemetry;
    private int minPosition = 0;
    private int maxPosition = 0;
    private boolean isCalibrated = false;

    public ArmCalibration(DcMotor motor, Gamepad gamepad, Telemetry telemetry) {
        this.motor = motor;
        this.gamepad = gamepad;
        this.telemetry = telemetry;
    }

    public void calibrate() {
        telemetry.addData("Calibration", "Use right stick to move arm");
        telemetry.addData("", "Press A to set min (extended) position");
        telemetry.addData("", "Press B to set max (retracted) position");
        telemetry.update();

        float rightStickY = -gamepad.right_stick_y;

        // Manual control for calibration
        if (Math.abs(rightStickY) > 0.1) {
            motor.setPower(rightStickY * 0.5); // Half speed for precise control
        } else {
            motor.setPower(0);
        }

        if (gamepad.a) {
            minPosition = motor.getCurrentPosition();
            telemetry.addData("Min Position Set", minPosition);
        }

        if (gamepad.b) {
            maxPosition = motor.getCurrentPosition();
            telemetry.addData("Max Position Set", maxPosition);
        }

        // Ensure min is always less than max
        if (minPosition > maxPosition) {
            int temp = minPosition;
            minPosition = maxPosition;
            maxPosition = temp;
        }

        isCalibrated = (minPosition != maxPosition);
    }

    public int getMinPosition() {
        return minPosition;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    public boolean isCalibrated() {
        return isCalibrated;
    }
}