/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
//ok
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Gobeur extends SubsystemBase {
  private DoubleSolenoid tiroir = new DoubleSolenoid(4, 5);
  private CANSparkMax rouleau = new CANSparkMax(26, MotorType.kBrushless);

  public Gobeur() {
    rouleau.setInverted(false);
    tiroirIn();
    moteurStop();
    
  }

  @Override
  public void periodic() {
  }
  public void moteurStop(){
    rouleau.set(0.0);
  }
  public void moteurGobe(){
    rouleau.set(1.0);
  }
  public void moteurPanic(){
    rouleau.set(-1.0);
  }
  public void tiroirIn(){
    tiroir.set(Value.kReverse);
  }
  public void tiroirOut(){
    tiroir.set(Value.kForward);
  }
}
