package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "ExtmotorControl", group = "TeleOp")
public class ExtmotorControl extends LinearOpMode {

    private DcMotor Extmotor;
    private ArmCalibration armCalibration;
    private boolean inCalibrationMode = false;
    
    // New variables for movement state
    private enum ArmState {IDLE, MOVING_TO_MIN, MOVING_TO_MAX}
    private ArmState currentArmState = ArmState.IDLE;

    @Override
    public void runOpMode() {
        // Initialize the Extmotor
        Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
        Extmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Initialize ArmCalibration
        armCalibration = new ArmCalibration(Extmotor, gamepad2, telemetry);

        telemetry.addData("Status", "Initialized");
        telemetry.addData("Controls", "Press Y to enter/exit calibration mode");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            // Check if Y button is pressed to toggle calibration mode
            if (gamepad2.y && !gamepad2.start) {
                inCalibrationMode = !inCalibrationMode;
                sleep(250); // Debounce
            }

            if (inCalibrationMode) {
                armCalibration.calibrate();
            } else {
                normalOperation();
            }

            telemetry.addData("Mode", inCalibrationMode ? "Calibration" : "Normal");
            telemetry.addData("Extmotor Position", Extmotor.getCurrentPosition());
            telemetry.addData("Min Position", armCalibration.getMinPosition());
            telemetry.addData("Max Position", armCalibration.getMaxPosition());
            telemetry.addData("Arm State", currentArmState);
            telemetry.update();
        }
    }

    private void normalOperation() {
        if (!armCalibration.isCalibrated()) {
            telemetry.addData("Warning", "Arm not calibrated. Press Y to calibrate.");
            return;
        }

        int currentPosition = Extmotor.getCurrentPosition();
        float rightStickY = -gamepad2.right_stick_y;

        // Check for new D-pad inputs
        if (gamepad2.dpad_left) {
            currentArmState = ArmState.MOVING_TO_MIN;
        } else if (gamepad2.dpad_right) {
            currentArmState = ArmState.MOVING_TO_MAX;
        }

        // Act based on current arm state
        switch (currentArmState) {
            case MOVING_TO_MIN:
                if (currentPosition > armCalibration.getMinPosition()) {
                    Extmotor.setPower(-2); // Adjust power as needed
                } else {
                    Extmotor.setPower(0);
                    currentArmState = ArmState.IDLE;
                }
                break;
            case MOVING_TO_MAX:
                if (currentPosition < armCalibration.getMaxPosition()) {
                    Extmotor.setPower(2); // Adjust power as needed
                } else {
                    Extmotor.setPower(0);
                    currentArmState = ArmState.IDLE;
                }
                break;
            case IDLE:
                // Manual control with right stick
                if (Math.abs(rightStickY) > 0.1) {
                    if ((rightStickY < 0 && currentPosition > armCalibration.getMinPosition()) || 
                        (rightStickY > 0 && currentPosition < armCalibration.getMaxPosition())) {
                        Extmotor.setPower(rightStickY);
                    } else {
                        Extmotor.setPower(0);
                    }
                } else {
                    Extmotor.setPower(0);
                }
                break;
        }

        telemetry.addData("Current Position", currentPosition);
        telemetry.addData("Arm State", currentArmState);
    }