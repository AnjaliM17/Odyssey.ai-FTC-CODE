package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CombinedControllerCode")
public class CombinedControllerCode extends LinearOpMode {

    // Servos
    private CRServo crServo;
    private Servo clawServo;
    private ElapsedTime runtime = new ElapsedTime();

    // Drive Motors
    private DcMotor frontRight;
    private DcMotor backRight;
    private DcMotor frontLeft;
    private DcMotor backLeft;

    // Arm Extension Motor
    private DcMotor Extmotor;
    private int minPosition = 0;
    private int maxPosition = 0;
    private boolean isCalibrated = false;
    private boolean inCalibrationMode = false;
    
    private enum ArmState {IDLE, MOVING_TO_MIN, MOVING_TO_MAX}
    private ArmState currentArmState = ArmState.IDLE;

    @Override
    public void runOpMode() {
        // Initialize Servos
        crServo = hardwareMap.get(CRServo.class, "crServo");
        clawServo = hardwareMap.get(Servo.class, "clawservo");

        // Initialize Drive Motors
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // Initialize Arm Extension Motor
        Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
        Extmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Extmotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

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
                calibrateArm();
            } else {
                normalOperation();
            }

            // Drive Control (Gamepad 1)
            driveMecanum();

            // Telemetry
            updateTelemetry();
        }
    }

    private void calibrateArm() {
        float rightStickY = -gamepad2.right_stick_y;

        if (Math.abs(rightStickY) > 0.1) {
            Extmotor.setPower(rightStickY * 0.5);
        } else {
            Extmotor.setPower(0);
        }

        if (gamepad2.a) {
            minPosition = Extmotor.getCurrentPosition();
        }

        if (gamepad2.b) {
            maxPosition = Extmotor.getCurrentPosition();
        }

        if (minPosition > maxPosition) {
            int temp = minPosition;
            minPosition = maxPosition;
            maxPosition = temp;
        }

        isCalibrated = (minPosition != maxPosition);
    }

    private void normalOperation() {
        if (!isCalibrated) {
            telemetry.addData("Warning", "Arm not calibrated. Press Y to calibrate.");
            return;
        }

        int currentPosition = Extmotor.getCurrentPosition();
        float rightStickY = -gamepad2.right_stick_y;

        if (gamepad2.dpad_left) {
            currentArmState = ArmState.MOVING_TO_MIN;
        } else if (gamepad2.dpad_right) {
            currentArmState = ArmState.MOVING_TO_MAX;
        }

        switch (currentArmState) {
            case MOVING_TO_MIN:
                if (currentPosition > minPosition) {
                    Extmotor.setPower(-0.5);
                } else {
                    Extmotor.setPower(0);
                    currentArmState = ArmState.IDLE;
                }
                break;
            case MOVING_TO_MAX:
                if (currentPosition < maxPosition) {
                    Extmotor.setPower(0.5);
                } else {
                    Extmotor.setPower(0);
                    currentArmState = ArmState.IDLE;
                }
                break;
            case IDLE:
                if (Math.abs(rightStickY) > 0.1) {
                    if ((rightStickY < 0 && currentPosition > minPosition) || 
                        (rightStickY > 0 && currentPosition < maxPosition)) {
                        Extmotor.setPower(rightStickY);
                    } else {
                        Extmotor.setPower(0);
                    }
                } else {
                    Extmotor.setPower(0);
                }
                break;
        }

        // CRServo Control (Gamepad 2 left stick)
        double crServoPower = gamepad2.left_stick_y;
        crServo.setPower(crServoPower);

        // Claw Servo Control (Gamepad 2 X and B buttons)
        if (gamepad2.x) {
            clawServo.setPosition(0.2);
        } 
        else if (gamepad2.b) {
            clawServo.setPosition(0.3);
        }
    }

    private void driveMecanum() {
        float forward = -gamepad1.right_stick_y;
        float strafe = -gamepad1.right_stick_x;
        float turn = -gamepad1.left_stick_x;

        if (gamepad1.left_bumper) {
            forward /= 2;
            strafe /= 2;
            turn /= 2;
        } else if (gamepad1.right_bumper) {
            forward *= 2;
            strafe *= 2;
            turn *= 2;
        }

        double denominator = Math.max(1, Math.abs(forward) + Math.abs(strafe) + Math.abs(turn));

        frontLeft.setPower((forward + strafe + turn) / denominator);
        backLeft.setPower((forward - strafe + turn) / denominator);
        frontRight.setPower((forward - strafe - turn) / denominator);
        backRight.setPower((forward + strafe - turn) / denominator);
    }

    private void updateTelemetry() {
        telemetry.addData("Mode", inCalibrationMode ? "Calibration" : "Normal");
        telemetry.addData("Extmotor Position", Extmotor.getCurrentPosition());
        telemetry.addData("Min Position", minPosition);
        telemetry.addData("Max Position", maxPosition);
        telemetry.addData("Arm State", currentArmState);
        telemetry.addData("CRServo Power", "%.2f", crServo.getPower());
        telemetry.addData("Claw Servo Position", "%.2f", clawServo.getPosition());
        telemetry.update();
    }
}