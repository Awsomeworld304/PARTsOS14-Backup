// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.Arm;

public class RunArmToZeroCmd extends Command {
  /** Creates a new RunArmToZeroCmd. */
  Arm arm;

  public RunArmToZeroCmd() {
    // Use addRequirements() here to declare subsystem dependencies.
    this.arm = Arm.getInstance();
    addRequirements(arm);

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    arm.setPivotSpeed(.2);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    arm.setPivotSpeed(0);
    //arm.zeroPivotEncoders();
    //new WaitCommand(.2).andThen(new ZeroPivotEncodersCmd()).schedule();

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return arm.getSwitch();
  }
}
