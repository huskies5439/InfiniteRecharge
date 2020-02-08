
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

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;



public class Lanceur extends PIDSubsystem {
  private final CANSparkMax m_shooterMotor = new CANSparkMax(22,MotorType.kBrushless);
 
  private final Encoder m_shooterEncoder =
      new Encoder(1,2);
  private final SimpleMotorFeedforward m_shooterFeedforward =
      new SimpleMotorFeedforward(0,0);

  /**
   * The shooter subsystem for the robot.
   */
  public Lanceur() {
    super(new PIDController(0,0,0));
    getController().setTolerance(0);
    m_shooterEncoder.setDistancePerPulse(0);
    setSetpoint(0);
  }

  @Override
  public void useOutput(double output, double setpoint) {
    m_shooterMotor.setVoltage(output + m_shooterFeedforward.calculate(setpoint));
  }

  @Override
  public double getMeasurement() {
    return m_shooterEncoder.getRate();
  }

  public boolean atSetpoint() {
    return m_controller.atSetpoint();
  }

  
}