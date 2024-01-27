// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {

  private static Intake intakeInstance;

  static TalonSRX intakeMotor;

  private boolean hasGamePiece = true;
  DigitalInput photoEye = new DigitalInput(0);


  /** Creates a new Intake. */
  public Intake() {
    intakeMotor = new TalonSRX(Constants.Intake.INTAKE_MOTOR);

    SmartDashboard.putBoolean("HAS NOTE", hasNoteSupplier().getAsBoolean());

  }

  public static Intake getInstance() {
    // If instance is null, then make a new instance.
    if (intakeInstance == null) { intakeInstance = new Intake(); }
    
    return intakeInstance;
  }

  public void runIntake(double speed) {
    intakeMotor.set(ControlMode.PercentOutput, speed);
  }

  //returns current
  public DoubleSupplier intakeCurrentSupplier() {
    DoubleSupplier s = () ->
      intakeMotor.getSupplyCurrent();
      return s;
  }

  public void runCurrent(double current) {
    intakeMotor.set(ControlMode.Current, current);
  }


  // methods for note detection using photoEyes
  public boolean hasGamePiece() {
    return hasGamePiece;
  }

  public void setGamePiece(boolean hasGamePiece) {
    this.hasGamePiece = hasGamePiece;
  }

  public Boolean hasNote() {
    return photoEye.get();
  }

  public BooleanSupplier hasNoteSupplier() {
    BooleanSupplier s = () ->
      hasNote();
    return s;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("HAS NOTE", hasNoteSupplier().getAsBoolean());
    SmartDashboard.putBoolean("Sensor 0", photoEye.get());
  }
}
