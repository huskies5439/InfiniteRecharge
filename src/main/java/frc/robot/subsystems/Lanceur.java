
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;



public class Lanceur extends PIDSubsystem {
  private final CANSparkMax moteurLanceurDroit = new CANSparkMax(20,MotorType.kBrushless);
  private final CANSparkMax moteurLanceurGauche = new CANSparkMax(29,MotorType.kBrushless);
  private final SpeedControllerGroup moteurLanceur  = new SpeedControllerGroup(moteurLanceurDroit,moteurLanceurGauche);
  private final SimpleMotorFeedforward lanceurFF = new SimpleMotorFeedforward(0.188,0.0855);
  
  /**
   * The shooter subsystem for the robot.
   */
  public Lanceur() {
    super(new PIDController(0.914,0,0));
    moteurLanceurDroit.setInverted(true);
    getController().setTolerance(50);
    setSetpoint(3000);
    moteurLanceurDroit.getEncoder().setVelocityConversionFactor(0.6667);
    moteurLanceurGauche.getEncoder().setVelocityConversionFactor(0.6667);
  } 
  @Override
  public void useOutput(double output, double setpoint) {
    moteurLanceur.setVoltage(output + lanceurFF.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
        return getVitesse();
  }


 
  public boolean estBonneVitesse() {
    return m_controller.atSetpoint();
  }
  public void vitesselancer(double v) {
    moteurLanceur.set(v);
  
    

  }
  public double getVitesse(){
    return (moteurLanceurDroit.getEncoder().getVelocity() + moteurLanceurGauche.getEncoder().getVelocity())/2.0 ;
  }
  
}