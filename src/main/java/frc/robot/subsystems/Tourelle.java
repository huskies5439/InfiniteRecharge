/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tourelle extends SubsystemBase {
  private TalonSRX moteurTourelle = new TalonSRX(13);
  private Encoder encoderTourelle = new Encoder(1, 2);
  private ProfiledPIDController pid = new ProfiledPIDController(0, 0, 0,
      new TrapezoidProfile.Constraints(/* max speed/sec */0, /* max acceleration/sec */0));
  private boolean softLimit;

  /**
   * Creates a new Tourelle.
   */
  public Tourelle() {
    encoderTourelle.setDistancePerPulse(1);// idk la valeur
    pid.setTolerance(1);
  }

  public double getPosition() {
    return encoderTourelle.getDistance();
  }

  public double getVitesse() {
    return encoderTourelle.getRate();
  }

  public void pidController(double mesure) {
    vitesse(pid.calculate(mesure, 0));
  }

  public void vitesse(double v) {
    moteurTourelle.set(ControlMode.PercentOutput, v);
  }

  public void stop() {
    moteurTourelle.set(ControlMode.PercentOutput, 0.0);
  }

  public boolean getSoftLimit() {
    return softLimit;
  }

  public boolean estCentre() {
    return pid.atSetpoint();
  }

  @Override
  public void periodic() {
    softLimit = Math.abs(getPosition()) < 45 || Math.signum(getPosition()) != Math.signum(getVitesse());
    SmartDashboard.putBoolean("Tourelle ok ?", softLimit);
    // This method will be called once per scheduler run
  }
}
