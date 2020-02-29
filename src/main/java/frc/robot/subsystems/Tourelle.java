/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Tourelle extends SubsystemBase {
  private CANSparkMax neotourelle = new CANSparkMax(28,MotorType.kBrushless);
  private ProfiledPIDController pid = new ProfiledPIDController(0.25, 0, 0,
      new TrapezoidProfile.Constraints(/* max speed  m/sec */5, /* max acceleration m/sec² */5));

  /**
   * Creates a new Tourelle.
   */
  public Tourelle() {
    neotourelle.getEncoder().setPositionConversionFactor(3.6);//1 tour tourelle/5 tour planetary,20 tour neo pour 1 tour planetary,360 par tpour tourelle
    neotourelle.getEncoder().setVelocityConversionFactor(0.06);//ratio position/60 secs
    pid.setTolerance(1);
    resetEncoder();
    neotourelle.setInverted(true);
  }
  public void ramp(double ramp){
    neotourelle.setOpenLoopRampRate(ramp);
  }

  public double getPosition() {
    return neotourelle.getEncoder().getPosition();
  }

  public double getVitesse() {
    return neotourelle.getEncoder().getVelocity();
  }

  public double pidController(double mesure) { 
    return pid.calculate(mesure, 0);
  }

  public void setVoltage(double v) {
    neotourelle.setVoltage(v);
  }

  public void stop() {
    neotourelle.setVoltage(0.0);
  }

  public boolean getSoftLimit(double vinput) {
    return Math.abs(getPosition()) < 50 || Math.signum(getPosition()) != Math.signum(vinput);
  }

  public boolean estCentre() {
    return pid.atSetpoint();
  }
  public void resetEncoder(){
    neotourelle.getEncoder().setPosition(0);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("AngleTourelle", getPosition());
    SmartDashboard.putNumber("VitesseTourelle", getVitesse());
  }
}
