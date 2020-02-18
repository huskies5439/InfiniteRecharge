/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Transmission extends SubsystemBase {
  private DoubleSolenoid vitesse = new DoubleSolenoid(0, 7);
 
  
  /**
   * Creates a new Transmission.
   */
  public Transmission() {
   // basseVitesse();
   

  }

  @Override
  public void periodic() {
    
 
    

    // This method will be called once per scheduler run
    
     
  }

  public void hauteVitesse() {
    vitesse.set(Value.kForward);
  }

  public void basseVitesse() {
    vitesse.set(Value.kReverse);
  }
 
  
}
