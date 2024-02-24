// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.commands.IntakeShoot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class IntakeShootCmd extends Command {
  /** Creates a new RunIntakeCmd. */
  Intake intake;
  Shooter shooter;
  Encoder shooterEncoder;
  double speedOfIntake;
  double speedOfShooter;

  public IntakeShootCmd(double speedOfIntake, double speedOfShooter) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = Intake.getInstance();
    this.shooter = Shooter.getInstance();
    this.speedOfIntake = speedOfIntake;
    this.speedOfShooter = speedOfShooter;
    // shooterEncoder = new Encoder(null, null);
    addRequirements(intake);
    addRequirements(shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  //
  @Override
  public void execute() {
    shooter.runShooter(speedOfShooter);
    /*
     * if () {
     * new RunIntakeCmd(speedOfIntake).schedule();
     * }
     */
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.runIntake(0);
    shooter.runShooter(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
