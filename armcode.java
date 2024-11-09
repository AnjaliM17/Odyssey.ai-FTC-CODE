// pre reset code 5th nov 3:51 pm
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

public class ArmCode extends OpMode {
    private DcMotor Extmotor;
    private int pulsesPerRotation = 1120;
    private int maxRotations = 20;
    private int currentRotations = 0;

    @Override
    public void init() {
        Extmotor = hardwareMap.dcMotor.get("Extmotor");
        Extmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {
        int currentEncoderPosition = Extmotor.getCurrentPosition();
        currentRotations = Math.abs(currentEncoderPosition) / pulsesPerRotation;

        // Use right stick Y for motor control
        float rightStickY = -gamepad1.right_stick_y; // Negate to make up positive
        
        if (Math.abs(rightStickY) > 0.1) { // Add a small deadzone
            if (rightStickY > 0 && currentRotations < maxRotations) {
                // Move forward if not at max rotations
                Extmotor.setPower(rightStickY);
            } else if (rightStickY < 0 && currentRotations > 0) {
                // Move backward if not at 0 rotations
                Extmotor.setPower(rightStickY);
            } else {
                Extmotor.setPower(0);
            }
        } else if (gamepad1.dpad_left) {
            Extmotor.setPower(0.5);
            telemetry.addData("dpad_left", Extmotor.getCurrentPosition());
            telemetry.update(); 
        } else if (gamepad1.dpad_right) {
            telemetry.addData("dpad_right", Extmotor.getCurrentPosition());
            Extmotor.setPower(-0.5);
        } else {
            Extmotor.setPower(0);
        }

        telemetry.addData("Current Encoder Position", currentEncoderPosition);
        telemetry.addData("Current Rotations", currentRotations);
        telemetry.addData("Max Rotations", maxRotations);
        telemetry.addData("Right Stick Y", rightStickY);
        telemetry.update();
    }

    private void resetMotorToZero() {
        int reverseTargetPosition = Extmotor.getCurrentPosition() - currentRotations * pulsesPerRotation;
        Extmotor.setTargetPosition(reverseTargetPosition);
        Extmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Extmotor.setPower(-1.0);
        telemetry.addData("Motor Reset", "Motor is reversing to 0 rotations.");
    }

    private void moveMotorToMaxRotation() {
        int targetPosition = (maxRotations - currentRotations) * pulsesPerRotation + Extmotor.getCurrentPosition();
        Extmotor.setTargetPosition(targetPosition);
        Extmotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        Extmotor.setPower(1.0);
        telemetry.addData("Motor Status", "Motor is moving to 20 rotations.");
        telemetry.addData("Target Position", targetPosition);
        telemetry.update();
    }
}