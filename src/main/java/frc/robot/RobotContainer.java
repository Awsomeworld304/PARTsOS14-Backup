// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.commands.IntakeShoot.IntakeShootCmd;
import frc.robot.commands.IntakeShoot.RunIntakeCmd;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer{
  // The robot's subsystems and commands are defined here...
  private final DriveTrain driveTrain = DriveTrain.getInstance();
  private final Arm arm = Arm.getInstance();
  private final Intake intake = Intake.getInstance();
  private final Shooter shooter = Shooter.getInstance();

  private final SlewRateLimiter speedLimiter = new SlewRateLimiter(1, -1, 0);

  private final CommandXboxController driveController = new CommandXboxController(0);
  private final CommandXboxController operatorController = new CommandXboxController(1);

  //Make SIM Input Device here. -R
  /*
   * Explanation:
   * I need to make a simulated input device \so that I can test the motor PID controllers.
   * I'm going to make an input device using a keyboard for motor testing.
   *  Better to tune the PID controllers now than later.
   * It's late and I'm tired. -R
   */
   CommandJoystick simInput = new CommandJoystick(2);

  // Example: Replace with real later.
  private ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
     
    driveTrain.setDefaultCommand(
      new RunCommand(() -> driveTrain.driveArcade(
        -speedLimiter.calculate(driveController.getLeftY()),
        driveController.getRightX()),
        driveTrain)
    );
    
    //Operator Triggers and Axis

    arm.setDefaultCommand(
      new RunCommand(() -> arm.setPivotSpeed(
        operatorController.getRightY()),
        arm)
    );

    //TODO: may need to invert the values
      operatorController.leftTrigger(.4).whileTrue(new RunIntakeCmd(-1));
      operatorController.leftBumper().whileTrue(new RunIntakeCmd(1));
      operatorController.b().whileTrue(new IntakeShootCmd());
    

    shooter.setDefaultCommand(
      new RunCommand(() -> shooter.runShooter(
        operatorController.getRightTriggerAxis()),
        shooter)
    );

    //Operator Buttons
    //operatorController.b().whileTrue(new ArmToPositionCmd(10));


    /* 
      driveTrain.setDefaultCommand(
        new RunCommand(() -> driveTrain.driveArcade(
          driveController.getLeftY(), 
          driveController.getRightX()),
          driveTrain)
      );

    
    driveTrain.setDefaultCommand(
      new RunCommand(() -> driveTrain.drive(
        driveController.getLeftY(), 
        driveController.getRightX()),
        driveTrain)
    );
    
    intake.setDefaultCommand(
      new RunCommand(() -> intake.runIntake(
        -operatorController.getLeftTriggerAxis()),
        intake)
    );

    */
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(m_exampleSubsystem);
  }
}