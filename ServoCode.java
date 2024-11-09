package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "ServoCode")
public class ServoCode extends LinearOpMode {
    private Servo armrotateServo;
 
    @Override
    public void runOpMode() {
        // Initialization code here
        armrotateServo = hardwareMap.get(Servo.class, "armrotateServo");
    
        waitForStart();
    
        while (opModeIsActive()) {
            if (gamepad1.y) {
                armrotateServo.setPosition(Math.min(1, armrotateServo.getPosition() + 0.01));
            } else if (gamepad1.a) {
                armrotateServo.setPosition(Math.max(0, armrotateServo.getPosition() - 0.01));
            }
       
            telemetry.addData("Servo Position", armrotateServo.getPosition());
            telemetry.update();
        }
    }
}