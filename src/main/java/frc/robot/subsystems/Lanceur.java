
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;



public class Lanceur extends PIDSubsystem {
  private final CANSparkMax shooterMotor1 = new CANSparkMax(22,MotorType.kBrushless);
  private final CANSparkMax shooterMotor2 = new CANSparkMax(23,MotorType.kBrushless);
  private final SpeedControllerGroup moteurslanceur  = new SpeedControllerGroup(shooterMotor1,shooterMotor2);

  private final Encoder encodeurlanceur = new Encoder(1,2);
  private final SimpleMotorFeedforward motorfeedforward = new SimpleMotorFeedforward(0,0);

  /**
   * The shooter subsystem for the robot.
   */
  public Lanceur() {
    super(new PIDController(0,0,0));
    getController().setTolerance(0);
    encodeurlanceur.setDistancePerPulse(0);
    setSetpoint(0);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    moteurslanceur.setVoltage(output + motorfeedforward.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
    return encodeurlanceur.getRate();
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  
}