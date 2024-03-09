// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  /** Creates a new DriveTrain. */

  public static DriveTrain driveTrain;

  /* Setup the motors. */
  public static CANSparkMax leftMotorLeader = new CANSparkMax(Constants.Drive.FRONT_LEFT_MOTOR, MotorType.kBrushless);
  public static CANSparkMax leftMotorFollower = new CANSparkMax(Constants.Drive.BACK_LEFT_MOTOR, MotorType.kBrushless);

  static CANSparkMax rightMotorLeader = new CANSparkMax(Constants.Drive.FRONT_RIGHT_MOTOR, MotorType.kBrushless);
  static CANSparkMax rightMotorFollower = new CANSparkMax(Constants.Drive.BACK_RIGHT_MOTOR, MotorType.kBrushless);

  DifferentialDrive differentialDrive = new DifferentialDrive(leftMotorLeader, rightMotorLeader);

  AHRS gyro;

  public DriveTrain() {

    gyro = new AHRS();

    /* Setup the followers of the motors. */
    leftMotorFollower.follow(leftMotorLeader);
    rightMotorFollower.follow(rightMotorLeader);

    /* Sets the proper directions for the motor groups. */
    rightMotorLeader.setInverted(false);
    leftMotorLeader.setInverted(true);

    leftMotorLeader.setIdleMode(IdleMode.kBrake);
    rightMotorLeader.setIdleMode(IdleMode.kBrake);
    leftMotorFollower.setIdleMode(IdleMode.kBrake);
    rightMotorFollower.setIdleMode(IdleMode.kBrake);

    /* Set open-loop rate for motors. */
    leftMotorFollower.setOpenLoopRampRate(Constants.Drive.OPEN_LOOP_RATE);
    rightMotorFollower.setOpenLoopRampRate(Constants.Drive.OPEN_LOOP_RATE);
    leftMotorLeader.setOpenLoopRampRate(Constants.Drive.OPEN_LOOP_RATE);
    rightMotorLeader.setOpenLoopRampRate(Constants.Drive.OPEN_LOOP_RATE);
  }

  /* Easy way to get instances. Returns the drivetrain obj if it's not null. */
  public static DriveTrain getInstance() {
    if (driveTrain == null) {
      driveTrain = new DriveTrain();
    }
    return driveTrain;
  }

  // The arcade drive mode, don't ask why it's swapped.
  public void driveArcade(double forwardBackSpeed, double rotationSpeed) {
    differentialDrive.arcadeDrive(forwardBackSpeed, rotationSpeed);
  }

  public void driveTank(double leftMotorSpeed, double rightMotorSpeed) {
    differentialDrive.tankDrive(leftMotorSpeed, rightMotorSpeed);
  }

  // (position * wheel diameter * pi) / gear ratio
  // gear ratio = driven gears / driving gears
  public double rightDistance() {
    return -Units.inchesToMeters((rightMotorLeader.getEncoder().getPosition() * 3.2 * Math.PI) / 5.87);
  }
  // 5.87

  public double leftDistance() {
    return -Units.inchesToMeters((leftMotorLeader.getEncoder().getPosition() * 3.2 * Math.PI) / 5.87);
  }

  public void moveVolts(double leftVoltage, double rightVoltage) {
    leftMotorLeader.setVoltage(leftVoltage);
    rightMotorLeader.setVoltage(rightVoltage);
  }

  public void zeroDriveEncoders() {
    leftMotorLeader.getEncoder().setPosition(0);
    rightMotorLeader.getEncoder().setPosition(0);
  }

  public double leftEncoderPosition() {
    return leftMotorLeader.getEncoder().getPosition();
  }

  public double rightEncoderPosition() {
    return rightMotorLeader.getEncoder().getPosition();
  }

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  public void zeroGyro() {
    gyro.reset();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
