/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Lanceur extends SubsystemBase {

  private final CANSparkMax moteurLanceurDroit = new CANSparkMax(20,MotorType.kBrushless);
  private final CANSparkMax moteurLanceurGauche = new CANSparkMax(29,MotorType.kBrushless);
  private final SpeedControllerGroup moteurLanceur  = new SpeedControllerGroup(moteurLanceurDroit,moteurLanceurGauche);
  private final SimpleMotorFeedforward lanceurFF = new SimpleMotorFeedforward(0.188,0.001412);
 private PIDController pid = new PIDController(0.003, 0, 0);//.914/60/10
 
  public Lanceur() {
    moteurLanceurDroit.setInverted(true);
    pid.setTolerance(10);
    setConversionFactors(1.5);
    moteurLanceurGauche.setIdleMode(IdleMode.kCoast);
    moteurLanceurDroit.setIdleMode(IdleMode.kCoast);
    moteurLanceurDroit.burnFlash();
    moteurLanceurGauche.burnFlash();
  }

  @Override
  public void periodic() {
   SmartDashboard.putNumber("vitesse lanceur", getVitesse());
   SmartDashboard.putNumber("Position lanceur", getPosition());
  }
  public void setConversionFactors(double facteur){
    moteurLanceurDroit.getEncoder().setPositionConversionFactor(facteur);
    moteurLanceurGauche.getEncoder().setPositionConversionFactor(facteur);
    moteurLanceurDroit.getEncoder().setVelocityConversionFactor(facteur);
    moteurLanceurGauche.getEncoder().setVelocityConversionFactor(facteur);
    moteurLanceurDroit.burnFlash();
    moteurLanceurGauche.burnFlash();
  }
  public boolean estBonneVitesse() {
    return pid.atSetpoint();
  }

  public void setVitesse(double voltage) {
    moteurLanceur.setVoltage(voltage);
  
  }
  public double getVitesse(){
    return (moteurLanceurDroit.getEncoder().getVelocity() + moteurLanceurGauche.getEncoder().getVelocity())/2.0 ;
  }
  public double getPosition(){
    return (moteurLanceurDroit.getEncoder().getPosition() + moteurLanceurGauche.getEncoder().getPosition())/2.0;
  }
  public void stop(){
    setVitesse(0);
  }
  public void pidfController(double vcible){
    setVitesse(pid.calculate(getVitesse(),vcible)+lanceurFF.calculate(vcible));
  }
}
