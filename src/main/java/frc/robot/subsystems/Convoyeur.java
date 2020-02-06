/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Convoyeur extends SubsystemBase {
  private CANSparkMax moteurConvoyeur1 = new CANSparkMax(1, MotorType.kBrushless);
  private CANSparkMax moteurConvoyeur2 = new CANSparkMax(2, MotorType.kBrushless);
  private SpeedControllerGroup convoyeur = new SpeedControllerGroup(moteurConvoyeur1, moteurConvoyeur2);
  private DigitalInput entree = new DigitalInput(1);
  private DigitalInput sortie = new DigitalInput(2);

  /**
   * Creates a new Convoyeur.
   */
  public Convoyeur() {

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void convoyeurLancer() {
    convoyeur.set(1);
  }

  public void convoyeurGober() {
    if (!sortie.get() & entree.get()) {
      convoyeur.set(0.5);
    } else {
      convoyeur.set(0);
    }
  }

  public void convoyeurStop() {
    convoyeur.set(0);
  }
}