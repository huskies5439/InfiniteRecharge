/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//ok
public class Transmission extends SubsystemBase {
  private DoubleSolenoid vitesse = new DoubleSolenoid(0, 1);
 
  
  public Transmission() {
    basseVitesse();
  }

  @Override
  public void periodic() {    
     
  }

  public void hauteVitesse() {
    vitesse.set(Value.kForward);
  }
 
  public void basseVitesse() {
    vitesse.set(Value.kReverse);
  }
 
  
}
