package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "PracticeIntakeServoCode")
public class PracticeIntakeArmServo extends LinearOpMode {
    private Servo intakeservo;
    private static final double Min_POSITION = 0.2; // Min position
    private static final double Max_POSITION = 0.6; // Max position
    

    @Override
    public void runOpMode() {
        // Initialization code here
        intakeservo = hardwareMap.get(Servo.class, "intakeservo");
        intakeservo.setPosition(Min_POSITION);
        waitForStart();
    
        while (opModeIsActive()) {
            if (gamepad2.dpad_up) {
                intakeservo.setPosition(0.6);
            } else if (gamepad2.dpad_down) {
                intakeservo.setPosition(NEUTRAL_POSITION);
            }

            // Optional: Add telemetry for debugging
            telemetry.addData("Servo Position", intakeservo.getPosition());
            telemetry.update();

            // Optional: Add a small delay to prevent over-polling
            sleep(10);
        }
    }