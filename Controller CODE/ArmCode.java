package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "ArmCode")
public class ArmCode extends LinearOpMode {

  private DcMotor Extmotor;
  public float maxLimit = 4000;
  public float minLimit = -4000;
  public float oflag = 1;
  @Override
  public void runOpMode() {
    Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
    Extmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    Extmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    // Put initialization blocks here.
    waitForStart();
    
    if (opModeIsActive()) ;{
      // Put run blocks here.
      while (opModeIsActive()) {
      // Put loop blocks here.
        // if (gamepad1.dpad_left) {
        //   Extmotor.setPower(0.5);
        //   telemetry.addData("dpad_left", Extmotor.getCurrentPosition());
        //   telemetry.update(); 
        // } else if (gamepad1.dpad_right) {
        //   telemetry.addData("dpad_right", Extmotor.getCurrentPosition());
        //   Extmotor.setPower(-0.5);
        //   } else {
          // Extmotor.setPower(0);
          
        // }
        double extmotorPower = gamepad1.right_stick_y;
        if (Extmotor.getCurrentPosition()< maxLimit&& Extmotor.getCurrentPosition()>minLimit){
        Extmotor.setPower(extmotorPower);
        telemetry.addData("inside else",Extmotor.getCurrentPosition());
        telemetry.update();
        
        }
        else
      //else if( (Extmotor.getCurrentPosition()> maxLimit|| Extmotor.getCurrentPosition()<minLimit)||
      //  oflag== null){
                { telemetry.addData("button_y:",gamepad1.right_stick_y);
                telemetry.addData("button_x:",gamepad1.right_stick_x);
                 oflag=-1;
                 
                 telemetry.addData("limit reached",Extmotor.getCurrentPosition()); 
                 Extmotor.setPower(0);
                // Extmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                // Extmotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                 //Extmotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                 
        }
      
      
        telemetry.addData("Extmotor Pos:", Extmotor.getCurrentPosition());
        telemetry.update(); 
      }
      telemetry.addData("Exit the while loop", Extmotor.getCurrentPosition());
        telemetry.update(); 
    }
  }
}

// package org.firstinspires.ftc.teamcode;

// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import org.firstinspires.ftc.robotcore.external.JavaUtil;

// @TeleOp(name = "ArmCode")
// public class ArmCode extends LinearOpMode {

//   private DcMotor Extmotor;

//   @Override
//   public void runOpMode() {
//     Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");

//     // Put initialization blocks here.
//     waitForStart();
    
//     if (opModeIsActive()) {
//       // Put run blocks here.
//       while (opModeIsActive()) {
//         // Put loop blocks here.
//         if (gamepad1.dpad_right) {
//           Extmotor.setPower(0.5);  // Clockwise rotation
//           telemetry.addData("dpad_right", "Clockwise");
//         } else if (gamepad1.dpad_left) {
//           Extmotor.setPower(-0.5);  // Counterclockwise rotation
//           telemetry.addData("dpad_left", "Counterclockwise");
//         } else {
//           Extmotor.setPower(0);  // Stop the motor when no D-pad is pressed
//           Extmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
//           telemetry.addData("Motor", "Stopped");
//         }

//         telemetry.addData("Extmotor Pos:", Extmotor.getCurrentPosition());
//         telemetry.update(); 
//       }
//     }
//   }
// }
// // }





// package org.firstinspires.ftc.teamcode;

// import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
// import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
// import com.qualcomm.robotcore.hardware.DcMotor;
// import com.qualcomm.robotcore.hardware.DcMotorSimple;

// @TeleOp(name = "ArmCode")
// public class ArmCode extends LinearOpMode {

//     private DcMotor Extmotor;
//   public float maxLimit = 0;

//     @Override
//     public void runOpMode() {
//         // Initialize the motor
//         Extmotor = hardwareMap.get(DcMotor.class, "Extmotor");
//         // Set the motor direction (adjust if needed)
//         Extmotor.setDirection(DcMotorSimple.Direction.FORWARD);
        
//         // Set zero power behavior
//         Extmotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

//         // Wait for the start button to be pressed
//         waitForStart();

//         // Main loop
//         while (opModeIsActive()) {
//             float currentPosition = Extmotor.getCurrentPosition();
//             double extmotorPower = -gamepad1.right_stick_y;

//             if (gamepad1.dpad_right && currentPosition < maxLimit) {
//                 // Move clockwise if not at max limit
//                 Extmotor.setPower(0.5);
//                 telemetry.addData("Motor", "Moving Clockwise");
//             } else if (gamepad1.dpad_left) {
//                 // Move counter-clockwise
//                 Extmotor.setPower(-0.5);
//                 telemetry.addData("Motor", "Moving Counter-Clockwise");
//             } else if (Math.abs(extmotorPower) > 0.1) {
//                 // Use right stick for fine control
//                 Extmotor.setPower(extmotorPower);
//                 telemetry.addData("Motor", "Stick Control");
//             } else {
//                 // Stop the motor
//                 Extmotor.setPower(0);
//                 telemetry.addData("Motor", "Stopped");
//             }
      
//             telemetry.addData("Motor Position", currentPosition);
//             telemetry.addData("Max Limit", maxLimit);
//             telemetry.update();

//         }
//     }
// }