/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.DoubleSummaryStatistics;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Grimpeur extends SubsystemBase {
  private CANSparkMax grimpeur1 = new CANSparkMax(0, MotorType.kBrushless);
  private CANSparkMax grimpeur2 = new CANSparkMax(0, MotorType.kBrushless);
  private SpeedControllerGroup grimpeur = new SpeedControllerGroup(grimpeur1, grimpeur2);
  private Servo servo = new Servo(0);
  private DoubleSolenoid bras = new DoubleSolenoid(0, 1);
  WPI_TalonSRX bougeur = new WPI_TalonSRX(0);
    /**
   * Creates a new Grimpeur.
   */
  public Grimpeur() {
    grimpeur1.setInverted(false);
    grimpeur2.setInverted(false);
    grimpeur1.setIdleMode(IdleMode.kBrake);
    grimpeur2.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void barrer(){
    servo.setAngle(10);
  }

  public void debarrer(){
    servo.setAngle(170);  
  }

  public double getVelocity(){
    return grimpeur1.getEncoder().getVelocity();
  }

  public double getPosition(){
    return grimpeur1.getEncoder().getVelocity();
  }

  public void treuilOff(){
    grimpeur.set(0);
  }

  public void treuilOn(){
    grimpeur.set(1);
  }

  public void alignement(double v){
    bougeur.set(v);
  }
}
