/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Convoyeur extends SubsystemBase {
  private CANSparkMax moteurConvoyeur1 = new CANSparkMax(35, MotorType.kBrushless);
  private CANSparkMax moteurConvoyeur2 = new CANSparkMax(36, MotorType.kBrushless);
  private SpeedControllerGroup convoyeur = new SpeedControllerGroup(moteurConvoyeur1, moteurConvoyeur2);
  private I2C.Port i2cport = I2C.Port.kOnboard;
  private ColorSensorV3 colorsensor = new ColorSensorV3(i2cport);
  private Ultrasonic ultrasonic = new Ultrasonic(8,9);
  /**
   * Creates a new Convoyeur.
   */
  public Convoyeur() {
    moteurConvoyeur1.setInverted(true);
    Ultrasonic.setAutomaticMode(true);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("vert", couleur());
    SmartDashboard.putNumber("Ultrasonic", getUltrasonicRange());
  }

  public void fournirBalle() {
    convoyeur.set(0.75);
  }
  public void sortirBalle(){
    convoyeur.set(-0.75);
  }
//les capteurs sont true lorsque détecte pas
  public void indexer() {
    if (entree() && sortie() ) {
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

  boolean entree(){
    return couleur()>350;
  }

  boolean sortie(){
    return getUltrasonicRange()>75;
  }

  public double getUltrasonicRange()
  {
    return ultrasonic.getRangeMM();
  }

 
}
