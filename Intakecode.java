package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Intakecode")
public class Intakecode extends LinearOpMode {
    private Servo intakeservo;
    private double currentPosition = 0.5; // Start at middle position
    private static final double JOYSTICK_THRESHOLD = 0.1; // Sensitivity threshold for joystick movement
    private static final double NEUTRAL_POSITION = 0.5; // Neutral position

    @Override
    public void runOpMode() {
        intakeservo = hardwareMap.get(Servo.class, "intakeservo");
        intakeservo.setPosition(currentPosition);

        waitForStart();

        while (opModeIsActive()) {
            double rightStickX = gamepad2.right_stick_x;

            // Check if the joystick is moved beyond the threshold
            if (Math.abs(rightStickX) > JOYSTICK_THRESHOLD) {
                // Map joystick input (-1 to 1) to servo position (0 to 1)
                currentPosition = (-rightStickX + 1) / 2;
                intakeservo.setPosition(currentPosition);
            } else if (gamepad2.a) {
                // Set to neutral position when button A is pressed
                currentPosition = NEUTRAL_POSITION;
                intakeservo.setPosition(NEUTRAL_POSITION);
            }

            // Display telemetry data
            telemetry.addData("Intake Servo Position", currentPosition);
            telemetry.addData("Joystick X", rightStickX);
            telemetry.update();

            // Optional: Add a small delay to prevent over-polling
            sleep(10);
        }
    }
}