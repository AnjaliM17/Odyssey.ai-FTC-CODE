package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "BucketCode")
public class BucketCode extends LinearOpMode {
    private Servo bucketservo;
        @Override
    public void runOpMode() {
        bucketservo = hardwareMap.get(Servo.class, "bucketservo");
        
        waitForStart();

        while (opModeIsActive()) {
            if(gamepad2.dpad_down){
            //moves arm down
             bucketservo.setPosition(0);   
            }
            else if(gamepad2.dpad_up){
            //moves arm up 
             bucketservo.setPosition(1);
            }
            //Display telemetry data
            telemetry.addData("bucketservo",bucketservo.getPosition());
            telemetry.update();

            // Optional: Add a small delay to prevent over-polling
            sleep(10);  
        }
        
    }
}