// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.IntakeShoot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class ShootInSpeakerCmd extends Command {
  /** Creates a new ShootInAmpCmd. */
  Shooter shooter;
  Intake intake;
  Encoder shooterEncoder;

  public ShootInSpeakerCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    shooter = Shooter.getInstance();
    intake = Intake.getInstance();

    // TODO: add shooter encoder

    addRequirements(shooter);
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    shooter.runShooter(.9);

    // TODO: get correct values
    if (shooterEncoder.getRate() >= 100) {
      intake.runIntake(.80);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.runShooter(0);
    intake.runIntake(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
