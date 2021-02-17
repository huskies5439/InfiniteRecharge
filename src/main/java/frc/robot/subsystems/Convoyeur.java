/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.nio.IntBuffer;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Convoyeur extends SubsystemBase {
  private CANSparkMax moteurConvoyeur1 = new CANSparkMax(35, MotorType.kBrushless);
  private CANSparkMax moteurConvoyeur2 = new CANSparkMax(36, MotorType.kBrushless);
  private SpeedControllerGroup convoyeur = new SpeedControllerGroup(moteurConvoyeur1, moteurConvoyeur2);
  private DigitalInput entree = new DigitalInput(8);
  private DigitalInput sortie = new DigitalInput(9);
  private I2C.Port i2cport = I2C.Port.kOnboard;
  private ColorSensorV3 colorsensor = new ColorSensorV3(i2cport);
  /**
   * Creates a new Convoyeur.
   */
  public Convoyeur() {
    moteurConvoyeur1.setInverted(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putBoolean("Capteur entrée", entree.get());
    SmartDashboard.putBoolean("Capteur sortie", sortie.get());
    SmartDashboard.putNumber("vert", couleur());
  }

  public void fournirBalle() {
    convoyeur.set(0.75);
  }
//les capteurs sont true lorsque détecte pas
  public void indexer() {
    if (!entree.get() && sortie.get()) {
      convoyeur.set(0.5);
    } else {
      convoyeur.set(0);
    }
  }

  public void stop() {
    convoyeur.set(0);
  }

  public int couleur(){
    return colorsensor.getGreen();
  }
}
